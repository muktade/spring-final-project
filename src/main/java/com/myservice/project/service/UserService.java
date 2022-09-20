package com.myservice.project.service;

import com.myservice.project.entiry.User;
import com.myservice.project.entiry.VerificationToken;
import com.myservice.project.model.UserModel;

import java.util.Optional;

public interface UserService {
    User registerUser(UserModel userModel);

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

    User findUserByEmail(String email);

    void createPasswordResetTokenForUser(User user, String token);

    String validatePasswordReserToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    void changePassword(User user, String newPassword);

    String checkOldPassword(User user, String oldPassword);


    String checkPassword(User user, String password);

}
