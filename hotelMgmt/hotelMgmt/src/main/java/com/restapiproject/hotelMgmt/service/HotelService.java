package com.restapiproject.hotelMgmt.service;

import java.util.List;
import com.restapiproject.hotelMgmt.model.Hotel;

public interface HotelService {
	
	Hotel createHotel(Hotel hotel);
	Hotel getHotelById(Long id);
	List<Hotel> getAllHotels();
	Hotel updateHotel(Long id, Hotel hotel);
	void deleteHotel(Long id);
	List<Hotel> searchByName(String name);


}
