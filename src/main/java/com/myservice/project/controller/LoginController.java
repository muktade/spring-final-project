package com.myservice.project.controller;


import com.myservice.project.entiry.User;
import com.myservice.project.model.UserModel;
import com.myservice.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
//@RequestMapping("/user/")
public class LoginController {

    @Autowired
    private AuthenticationManagerBuilder authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApplicationEventPublisher publisher;

//    @GetMapping("/login")
//    public String loginForm(){
//        return "user_login";
//    }


    @PostMapping("/login")
    public String loginPage(@RequestBody UserModel userModel){
        System.out.println("i am login");
        System.out.println(userModel);

        Authentication authentication = authenticationManager.getOrBuild()
                .authenticate(new UsernamePasswordAuthenticationToken(userModel.getEmail(), userModel.getPassword()));

        System.out.println(authentication);


        User user = userService.findUserByEmail(userModel.getEmail());
        System.out.println(userModel.getEmail());

        String status = userService.checkPassword(user, userModel.getPassword());
        if (status.equals("1")){
            return "login success";
        }else {
            return "unsuccessful";
        }
//        return "user_login";
//        return "user_login";
    }

}
