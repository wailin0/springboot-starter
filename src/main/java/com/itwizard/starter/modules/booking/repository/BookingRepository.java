package com.itwizard.starter.modules.booking.repository;

import com.itwizard.starter.modules.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Add custom query methods here
}

