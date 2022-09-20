package com.myservice.project.service;

import com.myservice.project.entiry.PasswordResetToken;
import com.myservice.project.entiry.VerificationToken;
import com.myservice.project.entiry.User;
import com.myservice.project.model.UserModel;
import com.myservice.project.repository.PasswordResetRepository;
import com.myservice.project.repository.UserRepository;
import com.myservice.project.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetRepository passwordResetRepository;

    @Override
    public User registerUser(UserModel userModel) {
        User user = new User();
        user.setEmail(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));

        userRepository.save(user);
        return user;
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken =
                new VerificationToken(user, token);

        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {

        VerificationToken verificationToken
                = verificationTokenRepository.findByToken(token);
        if(verificationToken == null){
            return "Invalid token";
        }
        User user = verificationToken.getUser();
        Calendar calendar= Calendar.getInstance();
        if(verificationToken.getExpirationTime().getTime()
        - calendar.getTime().getTime() <= 0){
            verificationTokenRepository.delete(verificationToken);
            return "Token is Expired";
        }

        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {

        VerificationToken verificationToken =
                verificationTokenRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public User findUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken =
                new PasswordResetToken(user, token);
        passwordResetRepository.save(passwordResetToken);
    }

    @Override
    public String validatePasswordReserToken(String token) {

        PasswordResetToken passwordResetToken
                = passwordResetRepository.findByToken(token);
        if(passwordResetToken == null){
            return "Invalid token";
        }
        User user = passwordResetToken.getUser();
        Calendar calendar= Calendar.getInstance();
        if(passwordResetToken.getExpirationTime().getTime()
                - calendar.getTime().getTime() <= 0){
            passwordResetRepository.delete(passwordResetToken);
            return "Token is Expired";
        }
        return "valid";

    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {

        return Optional.ofNullable(passwordResetRepository.findByToken(token)
                .getUser());
    }

    @Override
    public void changePassword(User user, String newPassword) {

            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
    }

    @Override
    public String checkOldPassword(User user, String oldPassword) {
        if(passwordEncoder.matches(oldPassword, user.getPassword())){
            return "1";
        }
        return "0";
    }

    @Override
    public String checkPassword(User user, String password) {
        if(user.getPassword().equals(passwordEncoder.encode(password))){
            return "1";
        }
        return null;
    }
}
