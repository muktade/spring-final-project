package com.myservice.project.controller;


import com.myservice.project.entiry.User;
import com.myservice.project.model.UserModel;
import com.myservice.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LoginController {

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
