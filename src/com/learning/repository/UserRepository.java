
package com.learning.repository;

import com.learning.models.User;
import java.util.ArrayList;

public class UserRepository {

    private ArrayList<User> users;

    public UserRepository() {
        users = new ArrayList<>();
    }

    public void addUser(User user) {
        if (user != null) {
            users.add(user);
        }
    }

    public User findById(int userId) {
        for (User user : users) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }

    public ArrayList<User> findAll() {
        return new ArrayList<>(users);
    }
}


// Why do we use ArrayList<User> instead of separate lists?
// Remember our inheritance?
// - Student extends User
// - Instructor extends User
// - Admin extends User
// Because all three inherit from User, one ArrayList<User> can store objects of all three types. That's another example of polymorphism!

// For example:
// users.add(new Student(1, "Arun", "arun@example.com"));
// users.add(new Instructor(2, "Priya", "priya@example.com"));
// users.add(new Admin(3, "Kumar", "kumar@example.com"));