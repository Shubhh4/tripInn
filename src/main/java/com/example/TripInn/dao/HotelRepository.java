package com.example.TripInn.dao;

import com.example.TripInn.entity.Hotel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> getHotelsByCity(String city);
}
