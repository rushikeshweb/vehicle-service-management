package com.rushikesh.vehicleservice.service.impl;

import com.rushikesh.vehicleservice.dto.VehicleDto;
import com.rushikesh.vehicleservice.dto.VehicleRequest;
import com.rushikesh.vehicleservice.dto.VehicleResponse;
import com.rushikesh.vehicleservice.entity.User;
import com.rushikesh.vehicleservice.entity.Vehicle;
import com.rushikesh.vehicleservice.repository.UserRepository;
import com.rushikesh.vehicleservice.repository.VehicleRepository;
import com.rushikesh.vehicleservice.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    // Add a vehicle for a user
    @Override
    public VehicleResponse addVehicle(VehicleRequest request, String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found with username: " + username);
        }

        User user = userOpt.get();

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber(request.getVehicleNumber());
        vehicle.setModel(request.getModel());
        vehicle.setUser(user);

        Vehicle saved = vehicleRepository.save(vehicle);

        VehicleResponse response = new VehicleResponse();
        response.setId(saved.getId());
        response.setVehicleNumber(saved.getVehicleNumber());
        response.setModel(saved.getModel());

        return response;
    }

    //  Get all vehicles for a user
    @Override
    public List<VehicleResponse> getVehiclesByUser(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found with username: " + username);
        }

        List<Vehicle> vehicles = vehicleRepository.findByUser(userOpt.get());
        List<VehicleResponse> responses = new ArrayList<>();

        for (Vehicle v : vehicles) {
            VehicleResponse res = new VehicleResponse();
            res.setId(v.getId());
            res.setVehicleNumber(v.getVehicleNumber());
            res.setModel(v.getModel());
            responses.add(res);
        }

        return responses;
    }

    //  Get vehicle by ID for a user
    @Override
    public VehicleResponse getVehicleById(Long id, String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }

        Optional<Vehicle> vehicleOpt = vehicleRepository.findById(id);
        if (!vehicleOpt.isPresent()) {
            throw new RuntimeException("Vehicle not found with ID: " + id);
        }

        Vehicle vehicle = vehicleOpt.get();
        if (!vehicle.getUser().getId().equals(userOpt.get().getId())) {
            throw new RuntimeException("Unauthorized access to vehicle");
        }

        VehicleResponse response = new VehicleResponse();
        response.setId(vehicle.getId());
        response.setVehicleNumber(vehicle.getVehicleNumber());
        response.setModel(vehicle.getModel());

        return response;
    }

    //  Delete vehicle for a user
    @Override
    public void deleteVehicle(Long id, String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }

        Optional<Vehicle> vehicleOpt = vehicleRepository.findById(id);
        if (!vehicleOpt.isPresent()) {
            throw new RuntimeException("Vehicle not found with ID: " + id);
        }

        Vehicle vehicle = vehicleOpt.get();
        if (!vehicle.getUser().getId().equals(userOpt.get().getId())) {
            throw new RuntimeException("Unauthorized access to delete vehicle");
        }

        vehicleRepository.delete(vehicle);
    }

    //  Register a vehicle from admin (no user)
    @Override
    public void registerVehicle(VehicleDto vehicleDto) {
        Optional<User> userOpt = userRepository.findByUsername(vehicleDto.getUsername());
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found with username: " + vehicleDto.getUsername());
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber(vehicleDto.getVehicleNumber());
        vehicle.setModel(vehicleDto.getModel());
        vehicle.setUser(userOpt.get());

        vehicleRepository.save(vehicle);
    }

    //  Admin: Get all registered vehicles
    @Override
    public List<VehicleDto> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        List<VehicleDto> dtoList = new ArrayList<>();

        for (Vehicle v : vehicles) {
            VehicleDto dto = new VehicleDto();
            dto.setId(v.getId());
            dto.setVehicleNumber(v.getVehicleNumber());
            dto.setModel(v.getModel());
            dto.setUsername(v.getUser().getUsername());

            dtoList.add(dto);
        }

        return dtoList;
    }
}
