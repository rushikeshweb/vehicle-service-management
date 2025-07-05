package com.rushikesh.vehicleservice.repository;

import com.rushikesh.vehicleservice.entity.User;
import com.rushikesh.vehicleservice.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
	Vehicle findByVehicleNumberAndUser(String vehicleNumber, User user);
    List<Vehicle> findByUser(User user);

}
