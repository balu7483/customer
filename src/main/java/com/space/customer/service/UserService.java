package com.space.customer.service;

import com.space.customer.model.User;
import com.space.customer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User insertUser(User user) {
        return repository.save(user);
    }

    public String login(String email, String password) {
        Optional<User> userOptional = repository.findByEmailAndPassword(email, password);
        if (userOptional.isPresent()) {
            return "Login successful";
        } else {
            return "Check your credentials";
        }
    }


    public String updateUserById(long id, User newUser) {
        try {
            Optional<User> optionalShirt = repository.findById(id);
            if (optionalShirt.isPresent()) {
                User exsistingUser = optionalShirt.get();
                exsistingUser.setFirstName(newUser.getFirstName());
                exsistingUser.setDob(newUser.getDob());
                exsistingUser.setLastName(newUser.getLastName());
                exsistingUser.setEmail(newUser.getEmail());
                exsistingUser.setCountry(newUser.getCountry());
                repository.save(exsistingUser);
                return "user updated successfully";
            } else {
                return "user not found";
            }
        } catch (Exception e) {
            return "Error updating user" + e.getMessage();
        }
    }

    public boolean changePassword(long id, String newPassword) {
        Optional<User> optUser = repository.findById(id);
        if (optUser.isPresent()) {
            User user = optUser.get();
            user.setPassword(newPassword);
            repository.save(user);
        }
        return false;
    }

    public void deleteUser(long id) {
        repository.deleteById(id);
    }

    public User findByID(long id) {
        User user = repository.findById(id).get();
        return user;
    }

    public User getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    public String signUP(User user) {
        List<User> newUsers = repository.findAll();
        for (User newUser : newUsers) {
            if (newUser.getEmail().equals(user.getEmail())) {
                return "user Already Exsists";
            }
        }
        repository.save(user);
        return "User signed up Sucessfully";
    }

}
