package com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.models.User;
import com.repositories.UserRepository;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    public User checkUserExists(User user) {
        // see if the user that tries to login, exists....
        User validationUser =
                user.getUsername() == null ? this.userRepository.findUserByEmail(user.getEmail()) :
                                             this.userRepository.findUserByUsername(user.getUsername());

        if (validationUser != null) {
            if (validationUser.getPassword().equals(user.getPassword()))
                return validationUser;
            else
                return null;
        } else {
            return null;
        }
    }
}