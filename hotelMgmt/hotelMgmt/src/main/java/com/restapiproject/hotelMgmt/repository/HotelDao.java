package com.restapiproject.hotelMgmt.repository;

import java.util.List;
import java.util.Optional;

import com.restapiproject.hotelMgmt.model.Hotel;

public interface HotelDao {
	
    Hotel save(Hotel hotel);
    Optional<Hotel> findById(Long id);
    List<Hotel> findAll();
    int update(Hotel hotel);
    int deleteById(Long id);
    List<Hotel> searchByName(String name);
}
