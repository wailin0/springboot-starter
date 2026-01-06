package com.itwizard.starter.modules.booking.controller;

import com.itwizard.starter.modules.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // Add booking endpoints here
    // Example:
    // @PostMapping
    // public ResponseEntity<ApiResponse> createBooking(@RequestBody BookingRequest request) {
    //     return ResponseUtil.created("Booking created successfully", bookingService.createBooking(request));
    // }
}

