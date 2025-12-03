package com.restapiproject.hotelMgmt.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.restapiproject.hotelMgmt.model.Hotel;

public class HotelRowMapper implements RowMapper<Hotel>{
	
	// tell spring how to convert one row of SQL result into a Java object

	@Override
	public Hotel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Hotel h = new Hotel();
		h.setId(rs.getLong("id"));
		h.setName(rs.getString("name"));
		h.setAddress(rs.getString("address"));
		h.setTotal_rooms(rs.getInt("total_rooms"));
		h.setAvailable_rooms(rs.getInt("available_rooms"));
		h.setPrice_per_night(rs.getBigDecimal("price_per_night"));
		java.sql.Timestamp created = rs.getTimestamp("created_at");
		if(created != null) h.setCreated_at(created.toLocalDateTime());
		java.sql.Timestamp updated = rs.getTimestamp("updated_at");
		if(updated != null) h.setUpdated_at(updated.toLocalDateTime());
		
		return h;
	}
	
	
 
}
