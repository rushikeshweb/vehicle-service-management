package com.rushikesh.vehicleservice.service.impl;

import com.rushikesh.vehicleservice.dto.BookingRequest;
import com.rushikesh.vehicleservice.dto.BookingResponse;
import com.rushikesh.vehicleservice.entity.Booking;
import com.rushikesh.vehicleservice.entity.User;
import com.rushikesh.vehicleservice.entity.Vehicle;
import com.rushikesh.vehicleservice.enums.BookingStatus;
import com.rushikesh.vehicleservice.repository.BookingRepository;
import com.rushikesh.vehicleservice.repository.UserRepository;
import com.rushikesh.vehicleservice.repository.VehicleRepository;
import com.rushikesh.vehicleservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    // 1. Create Booking
    @Override
    public void createBooking(BookingRequest bookingRequestDto, String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found: " + username);
        }
        User user = userOpt.get();

        Vehicle vehicle = vehicleRepository.findByVehicleNumberAndUser(bookingRequestDto.getVehicleNumber(), user);
        if (vehicle == null) {
            throw new RuntimeException("Vehicle not found for user");
        }

        Booking booking = new Booking();
        booking.setBookingDate(LocalDate.now());
        booking.setStatus(BookingStatus.PENDING);
        booking.setVehicle(vehicle);

        bookingRepository.save(booking);
    }

    //  2. Get Bookings For Logged-In User
    @Override
    public List<BookingResponse> getBookingsForUser(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found: " + username);
        }
        User user = userOpt.get();

        List<Booking> bookings = bookingRepository.findByVehicleUser(user);
        List<BookingResponse> responseList = new ArrayList<>();

        for (Booking booking : bookings) {
            BookingResponse dto = new BookingResponse();
            dto.setId(booking.getId());
            dto.setBookingDate(booking.getBookingDate());
            dto.setStatus(booking.getStatus());
            dto.setVehicleNumber(booking.getVehicle().getVehicleNumber());
            dto.setModel(booking.getVehicle().getModel());
            dto.setUsername(user.getUsername()); // for consistency

            responseList.add(dto);
        }
        return responseList;
    }

    //  3. Admin: Get All Bookings
    @Override
    public List<BookingResponse> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingResponse> responseList = new ArrayList<>();

        for (Booking booking : bookings) {
            BookingResponse dto = new BookingResponse();
            dto.setId(booking.getId());
            dto.setBookingDate(booking.getBookingDate());
            dto.setStatus(booking.getStatus());
            dto.setVehicleNumber(booking.getVehicle().getVehicleNumber());
            dto.setModel(booking.getVehicle().getModel());
            dto.setUsername(booking.getVehicle().getUser().getUsername()); // ✅ add username

            responseList.add(dto);
        }

        return responseList;
    }

    //  4. Update Booking Status
    @Override
    public void updateBookingStatus(Long bookingId, String status) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (!bookingOpt.isPresent()) {
            throw new RuntimeException("Booking not found with id: " + bookingId);
        }
        Booking booking = bookingOpt.get();

        BookingStatus bookingStatus;
        try {
            bookingStatus = BookingStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid booking status: " + status);
        }

        booking.setStatus(bookingStatus);
        bookingRepository.save(booking);
    }
    @Override
    public List<BookingResponse> getBookingsByUsername(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found with username: " + username);
        }
        User user = userOpt.get();

        List<Booking> bookings = bookingRepository.findByVehicleUser(user);
        List<BookingResponse> responseList = new ArrayList<>();

        for (Booking booking : bookings) {
            BookingResponse dto = new BookingResponse();
            dto.setId(booking.getId());
            dto.setBookingDate(booking.getBookingDate());
            dto.setStatus(booking.getStatus());
            dto.setVehicleNumber(booking.getVehicle().getVehicleNumber());
            dto.setModel(booking.getVehicle().getModel());
            dto.setUsername(user.getUsername());

            responseList.add(dto);
        }

        return responseList;
    }

}
