package com.myservice.project.controller;

import com.myservice.project.entiry.User;
import com.myservice.project.entiry.VerificationToken;
import com.myservice.project.event.RegistrationCompleteEvent;
import com.myservice.project.model.PasswordModel;
import com.myservice.project.model.UserModel;
import com.myservice.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;


    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel,
                               final HttpServletRequest request) {
        User user = userService.registerUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(user,
                applicationUrl(request)));
        return "Success";
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() +
                ":" + request.getServerPort()
                + request.getContextPath();
    }

    @GetMapping("/verifyUser")
    private String VerifyRegistration(@RequestParam("token") String token) {
        String result = userService.validateVerificationToken(token);
        if (result.equalsIgnoreCase("valid")) {
            return "User Verifies Successfully";
        } else {
            return result;
        }
    }

    @GetMapping("/sendVerificationToken")
    private String sendVerificationToken(@RequestParam("token") String oldToken,
                                         HttpServletRequest request) {
        VerificationToken verificationToken =
                userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        resentVerificationTokenMail(user, applicationUrl(request), verificationToken);
        return "Verification token Send";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel,
                                HttpServletRequest request) {
        User user = userService.findUserByEmail(passwordModel.getEmail());

        String url = "";
        if (user != null) {
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            url = passwardRresetTokenMail(user, applicationUrl(request), token);
            return url;
        }
        return "User Not Found";
    }

    @PostMapping("/savePassword")
    private String savePassword(@RequestParam("token") String token,
                                @RequestBody PasswordModel passwordModel){

        String result = userService.validatePasswordReserToken(token);
        if(!result.equalsIgnoreCase("valid")){
            return result;
        }
        Optional<User> user = userService.getUserByPasswordResetToken(token);
        if (user.isPresent()){
            userService.changePassword(user.get(), passwordModel.getNewPassword());
            return "Password Reset Successful";
        }else {
            return "Invalid";
        }
    }

    @PostMapping("/changePassword")
    private String changePassword(@RequestBody PasswordModel passwordModel){
        User user =userService.findUserByEmail(passwordModel.getEmail());
        String status = userService.checkOldPassword(user, passwordModel.getOldPassword());
        String url ="";
        if(status.equals("1")){
            userService.changePassword(user, passwordModel.getNewPassword());
            return "Password Change Successful";
        }
        return "Old Password Not Match";
    }

    private String passwardRresetTokenMail(User user, String applicationUrl, String token) {

        ///sent link
        String url = applicationUrl + "/savePassword?token="
                + token;

        //sent verification link
        log.info("Click the link to Reset our Password: {}",
                url);
        return url;

    }


    private void resentVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {
        ///sent link
        String url = applicationUrl + "/verifyUser?token="
                + verificationToken.getToken();

        //sent verification link
        log.info("Click the link to verify our account: {}",
                url);
    }


}
