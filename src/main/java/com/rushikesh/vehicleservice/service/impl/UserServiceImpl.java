package com.rushikesh.vehicleservice.service.impl;

import com.rushikesh.vehicleservice.dto.UserDto;
import com.rushikesh.vehicleservice.entity.RoleEntity;
import com.rushikesh.vehicleservice.entity.User;
import com.rushikesh.vehicleservice.repository.RoleRepository;
import com.rushikesh.vehicleservice.repository.UserRepository;
import com.rushikesh.vehicleservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void registerUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        //  Assign only ROLE_USER
        RoleEntity userRole = roleRepository.findByName("ROLE_USER")
            .orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoles().add(userRole);

        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        return userOpt.orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
