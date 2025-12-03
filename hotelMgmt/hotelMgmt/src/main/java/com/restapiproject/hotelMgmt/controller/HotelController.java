package com.restapiproject.hotelMgmt.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restapiproject.hotelMgmt.dto.HotelDto;
import com.restapiproject.hotelMgmt.model.Hotel;
import com.restapiproject.hotelMgmt.service.HotelService;

import jakarta.validation.Valid;

@RestController // marking this class as a REST controller, automatically convert responses into JSON format
@RequestMapping("/api/hotels") // Sets a base URL for all endpoints in this class
public class HotelController {
	
	// is the entry point for all HTTP API requests
	// GET , POST, PUT, DELETE
	// set a base URL for all the endpoints of this class /api/hotels
     
	private final HotelService hotelService;
	public HotelController(HotelService hotelService) {
		this.hotelService=hotelService;
	}
	
	// HotelDto - db structure (internal) not expose to API
	//          - preform validations, transformations 
	// controller -> recieve and return Dtos (Data Transfer Object)
	// and not entities
	// 2 helper functions
	// DTO <-> Entity 
	// incoming API request -> dtoToEntity 
	// send JSON in API response -> conver entity (from db) back to dto
	
	// helper functions
	
	// dtoToEntity - Enitity Hotel
	private Hotel dtoToEntity(HotelDto dto) {
		Hotel h = new Hotel();
		h.setId(dto.getId());
		h.setName(dto.getName());
		h.setAddress(dto.getAddress());
		h.setTotal_rooms(dto.getTotal_rooms());
		h.setAvailable_rooms(dto.getAvailable_rooms());
		h.setPrice_per_night(dto.getPrice_per_night());
		return h;
	}
	
	// EntitytoDto
	private HotelDto entityToDto(Hotel h) {
		HotelDto dto = new HotelDto();
		dto.setId(h.getId());
		dto.setName(h.getName());
		dto.setAddress(h.getAddress());
		dto.setTotal_rooms(h.getTotal_rooms());
		dto.setAvailable_rooms(h.getAvailable_rooms());
		dto.setPrice_per_night(h.getPrice_per_night());
		return dto;
	}
		
	// Get request
	@GetMapping
	public ResponseEntity<List<HotelDto>> getAll() {
		List<Hotel> list = hotelService.getAllHotels(); // fetch that from db 
		// converting list og Hotel entities into corresponding Dtos
		List<HotelDto> dtoList = list.stream().map(this::entityToDto)
				                              .collect(Collectors.toList());
		// return HTTP response
		// ResponseEntity - spring wrapper for HTTP Response
		// allows you to set status code, headers, body - dto objects
		return ResponseEntity.ok(dtoList);  // 200 OK along with JSON list
 	}
	
	// Get request for specific hotel - id
	// /api/hotels/1
	@GetMapping("/{id}")
	public ResponseEntity<HotelDto> getById(@PathVariable Long id) { 
		
		// @PathVariable - tell spring to extract {id}
		// - from URL and assign it to the id variable
		// /api/hotels/4		
		
		Hotel h = hotelService.getHotelById(id);
		HotelDto dto = entityToDto(h);
		return ResponseEntity.ok(dto);		
	}
	
	// Post Request
	@PostMapping
	public ResponseEntity<HotelDto> create(@Valid @RequestBody HotelDto dto) {
		// @RequestBody -> tell the spring to take data form request body 
		// give data in request body -> JSON 
		// take data - into Dto
		// deserialize it into Java object
		// @Valid -> chekc for non empty values , price >0, min >
		// validation fails -> 400 Bad Request
		Hotel h = dtoToEntity(dto);
		Hotel created = hotelService.createHotel(h);
		return ResponseEntity.created(URI.create("/api/hotels/" + created.getId()))
				             .body(entityToDto(created));
	}
	
	// Put Request
	@PutMapping("/{id}")
	public ResponseEntity<HotelDto> update(@PathVariable Long id, @Valid @RequestBody HotelDto dto) {		
		Hotel h = dtoToEntity(dto);
		Hotel updated = hotelService.updateHotel(id, h);
        return ResponseEntity.ok(entityToDto(updated));		
	}
	
	// Delete Request
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		hotelService.deleteHotel(id);
		
		return ResponseEntity.ok("Hotel deleted with id : "+id);
		
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<HotelDto>> searchByName(@RequestParam(value="name",required=false)String name) { 
		
		// @PathVariable - tell spring to extract {id}
		// - from URL and assign it to the id variable
		// /api/hotels/4		
		
		List<Hotel> hotels = hotelService.searchByName(name);
		List<HotelDto> dtoList = hotels.stream().map(this::entityToDto)
                .collect(Collectors.toList());
		
		
		return ResponseEntity.ok(dtoList);		
	}
		
	
	
}
