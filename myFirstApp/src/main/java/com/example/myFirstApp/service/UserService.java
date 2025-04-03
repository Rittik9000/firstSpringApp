package com.example.myFirstApp.service;
import com.example.myFirstApp.model.User;
import com.example.myFirstApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email '" + user.getEmail() + "' is already in use!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash password
        user.setRole("USER"); // Default role
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public  Optional<User> getUserById(String id){
    	return userRepository.findById(id);
    }
    
}