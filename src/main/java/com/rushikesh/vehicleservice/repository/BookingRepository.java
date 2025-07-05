package com.rushikesh.vehicleservice.repository;

import com.rushikesh.vehicleservice.entity.Booking;
import com.rushikesh.vehicleservice.entity.User;
import com.rushikesh.vehicleservice.entity.Vehicle;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByVehicleUser(User user);
    List<Booking> findByVehicle(Vehicle vehicle);


}
