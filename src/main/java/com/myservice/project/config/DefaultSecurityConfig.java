package com.myservice.project.config;


import com.myservice.project.service.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
public class DefaultSecurityConfig {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

//    @Bean
//    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
//        throws Exception{
//        http.authorizeHttpRequests(authorizeRequests->
//                authorizeRequests.anyRequest().authenticated()
//                )
//                .formLogin(Customizer.withDefaults());
//        return http.build();
//    }

    @Autowired
    public void bindAuthenticationProvider(AuthenticationManagerBuilder authenticationManagerBuilder){
        authenticationManagerBuilder
                .authenticationProvider(customAuthenticationProvider);
    }

//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
}
