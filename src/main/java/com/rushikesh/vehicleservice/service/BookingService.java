package com.rushikesh.vehicleservice.service;

import com.rushikesh.vehicleservice.dto.BookingRequest;
import com.rushikesh.vehicleservice.dto.BookingResponse;

import java.util.List;

public interface BookingService {
    void createBooking(BookingRequest bookingRequestDto, String username);
    List<BookingResponse> getBookingsForUser(String username);
    void updateBookingStatus(Long bookingId, String status);
    List<BookingResponse> getAllBookings();
    List<BookingResponse> getBookingsByUsername(String username);


}
