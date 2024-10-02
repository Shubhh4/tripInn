package com.example.TripInn.service;

import com.example.TripInn.dao.HotelRepository;
import com.example.TripInn.entity.Hotel;
import com.example.TripInn.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> getAllHotels(){
        return hotelRepository.findAll();
    }

    public Optional<Hotel> getHotelById(Long id){
        return hotelRepository.findById(id);
    }

    public List<Hotel> getHotelsByCity(String city){
        return hotelRepository.getHotelsByCity(city);
    }

    public void saveOrUpdateHotel(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }
}
