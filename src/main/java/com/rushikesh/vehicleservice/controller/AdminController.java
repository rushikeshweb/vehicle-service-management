package com.rushikesh.vehicleservice.controller;

import com.rushikesh.vehicleservice.dto.BookingResponse;
import com.rushikesh.vehicleservice.dto.VehicleDto;
import com.rushikesh.vehicleservice.entity.User;
import com.rushikesh.vehicleservice.entity.Vehicle;
import com.rushikesh.vehicleservice.service.BookingService;
import com.rushikesh.vehicleservice.service.UserService;
import com.rushikesh.vehicleservice.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private VehicleService vehicleService; 

    // GET all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // GET all bookings
    @GetMapping("/bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // GET bookings by username
    @GetMapping("/bookings/{username}")
    public ResponseEntity<List<BookingResponse>> getBookingsByUsername(@PathVariable String username) {
        List<BookingResponse> bookings = bookingService.getBookingsByUsername(username);
        return ResponseEntity.ok(bookings);
    }

    //  GET all vehicles
    @GetMapping("/vehicles")
    public ResponseEntity<List<VehicleDto>> getAllVehicles() {
        List<VehicleDto> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }
    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity<String> deleteVehicleById(@PathVariable Long id, @RequestParam String username) {
        vehicleService.deleteVehicle(id, username);
        return ResponseEntity.ok("Vehicle deleted successfully");
    }

}
