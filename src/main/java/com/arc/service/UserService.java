package com.arc.service;

import com.arc.entity.Users;
import com.arc.repository.UserDetailsRepository;
import com.arc.to.RegisterUserRequest;
import com.arc.to.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDetailsRepository userDetailsRepository;

    private final BCryptPasswordEncoder encoder;

    public UserService(UserDetailsRepository userDetailsRepository, BCryptPasswordEncoder encoder) {
        this.userDetailsRepository = userDetailsRepository;
        this.encoder = encoder;
    }

    public UserResponse registerUser(RegisterUserRequest registerUserRequest) {
        // Check if the username already exists
        if (userDetailsRepository.findByUsername(registerUserRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Users users = new Users();
        users.setUsername(registerUserRequest.getUsername());
        users.setRole(registerUserRequest.getRole());
        users.setPassword(encoder.encode(registerUserRequest.getPassword()));

        Users savedUser = userDetailsRepository.save(users);

        return new UserResponse(savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getRole().name());

    }
}
