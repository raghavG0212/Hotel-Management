package com.restapiproject.hotelMgmt.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
 
public class Hotel {
	
	public Long id;
	public String name;
	public String address;
	public Integer total_rooms;
	public Integer available_rooms;
	public BigDecimal price_per_night;
	public LocalDateTime created_at;
	public LocalDateTime updated_at;
	
	public Hotel() {}
 
	public Hotel(Long id, String name, String address, int total_rooms, int available_rooms, BigDecimal price_per_night
			) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.total_rooms = total_rooms;
		this.available_rooms = available_rooms;
		this.price_per_night = price_per_night;
		
	}
	
 
	public Long getId() {
		return id;
	}
 
	public void setId(Long id) {
		this.id = id;
	}
 
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
 
	public String getAddress() {
		return address;
	}
 
	public void setAddress(String address) {
		this.address = address;
	}
 
	public int getTotal_rooms() {
		return total_rooms;
	}
 
	public void setTotal_rooms(int total_rooms) {
		this.total_rooms = total_rooms;
	}
 
	public int getAvailable_rooms() {
		return available_rooms;
	}
 
	public void setAvailable_rooms(int available_rooms) {
		this.available_rooms = available_rooms;
	}
 
	public BigDecimal getPrice_per_night() {
		return price_per_night;
	}
 
	public void setPrice_per_night(BigDecimal price_per_night) {
		this.price_per_night = price_per_night;
	}
 
	public LocalDateTime getCreated_at() {
		return created_at;
	}
 
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
 
	public LocalDateTime getUpdated_at() {
		return updated_at;
	}
 
	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}
	
	
}
 
 