package com.rushikesh.vehicleservice.dto;

public class UpdateBookingStatusRequest {
    private String status;

    public UpdateBookingStatusRequest() {}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
