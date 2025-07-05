package com.rushikesh.vehicleservice.service;

import com.rushikesh.vehicleservice.dto.VehicleDto;
import com.rushikesh.vehicleservice.dto.VehicleRequest;
import com.rushikesh.vehicleservice.dto.VehicleResponse;
import java.util.List;

public interface VehicleService {
    VehicleResponse addVehicle(VehicleRequest vehicleRequestDto, String username);
    List<VehicleResponse> getVehiclesByUser(String username);
    VehicleResponse getVehicleById(Long id, String username);
    void deleteVehicle(Long id, String username);
    void registerVehicle(VehicleDto vehicleDto);
    List<VehicleDto> getAllVehicles();
}
