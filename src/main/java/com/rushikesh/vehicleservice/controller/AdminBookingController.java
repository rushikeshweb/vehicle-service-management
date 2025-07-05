package com.rushikesh.vehicleservice.controller;

import com.rushikesh.vehicleservice.dto.UpdateBookingStatusRequest;
import com.rushikesh.vehicleservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/bookings")
public class AdminBookingController {

    @Autowired
    private BookingService bookingService;

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateBookingStatus(@PathVariable Long id, @RequestBody UpdateBookingStatusRequest requestDto) {
        bookingService.updateBookingStatus(id, requestDto.getStatus());
        return ResponseEntity.ok("Booking status updated successfully.");
    }
}
