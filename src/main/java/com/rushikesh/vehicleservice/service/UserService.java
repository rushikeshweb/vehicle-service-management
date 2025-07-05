package com.rushikesh.vehicleservice.service;

import com.rushikesh.vehicleservice.dto.UserDto;
import com.rushikesh.vehicleservice.entity.User;

import java.util.List;

public interface UserService {
    void registerUser(UserDto userDto);
    User findByUsername(String username);
    List<User> getAllUsers();
}
