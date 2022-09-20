package com.myservice.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private LoggingAccessDeniedHandler loggingAccessDeniedHandler;

    private static final String[] WHITE_LIST_USER={
            "/hello", "/register", "/sendVerificationToken","/verifyUser"
    };


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .antMatchers("/login","/register","/").permitAll()
                .antMatchers("/static/**", "/dist/**", "/css/**", "/fonts/**", "/js/**", "/images/**", "/ie8-panel/**").permitAll()
                .antMatchers(WHITE_LIST_USER).permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").permitAll()
                .successForwardUrl("/loginsuccess")
                .and().logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").permitAll()
                .and().exceptionHandling().accessDeniedHandler(loggingAccessDeniedHandler);

        return http.build();
    }

}
