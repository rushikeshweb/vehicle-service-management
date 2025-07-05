package com.rushikesh.vehicleservice.controller;

import com.rushikesh.vehicleservice.dto.VehicleRequest;
import com.rushikesh.vehicleservice.dto.VehicleResponse;
import com.rushikesh.vehicleservice.entity.Vehicle;
import com.rushikesh.vehicleservice.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<String> addVehicle(@RequestBody VehicleRequest vehicleRequest, Authentication authentication) {
        vehicleService.addVehicle(vehicleRequest, authentication.getName());  // ✅ Correct types
        return ResponseEntity.ok("Vehicle added successfully.");
    }


    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getUserVehicles(Authentication authentication) {
        List<VehicleResponse> vehicles = vehicleService.getVehiclesByUser(authentication.getName());
        return ResponseEntity.ok(vehicles);
    }

}
