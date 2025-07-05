package com.rushikesh.vehicleservice.controller;

import com.rushikesh.vehicleservice.dto.BookingRequest;
import com.rushikesh.vehicleservice.dto.BookingResponse;
import com.rushikesh.vehicleservice.dto.UpdateBookingStatusRequest;
import com.rushikesh.vehicleservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody BookingRequest bookingRequestDto, Authentication authentication) {
        bookingService.createBooking(bookingRequestDto, authentication.getName());
        return ResponseEntity.ok("Booking created successfully.");
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getBookings(Authentication authentication) {
        return ResponseEntity.ok(bookingService.getBookingsForUser(authentication.getName()));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestBody UpdateBookingStatusRequest requestDto) {
        bookingService.updateBookingStatus(id, requestDto.getStatus());
        return ResponseEntity.ok("Booking status updated successfully.");
    }
    
}

