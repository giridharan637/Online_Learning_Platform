
package com.learning.service;

import com.learning.models.User;
import com.learning.repository.UserRepository;
import java.util.ArrayList;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean createUser(User user) {
        if (user == null) {
            System.out.println("User cannot be empty.");
            return false;
        }

        if (userRepository.findById(user.getId()) != null) {
            System.out.println("User ID already exists.");
            return false;
        }

        userRepository.addUser(user);
        System.out.println("User created successfully.");
        return true;
    }

    public User findUserById(int userId) {
        return userRepository.findById(userId);
    }

    public ArrayList<User> getAllUsers() {
        return userRepository.findAll();
    }
}
