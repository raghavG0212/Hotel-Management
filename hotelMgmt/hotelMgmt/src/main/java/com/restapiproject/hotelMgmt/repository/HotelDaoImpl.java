package com.restapiproject.hotelMgmt.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import com.restapiproject.hotelMgmt.util.HotelRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.restapiproject.hotelMgmt.model.Hotel;

@Repository  // mark this class as DAO component - spring sutomatically detect for DI, direclty interacts wth db
public class HotelDaoImpl implements HotelDao{
	
	private final JdbcTemplate jdbcTemplate;
	
	public HotelDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
    @Override
    public Hotel save(Hotel hotel) {    	
    	// sql string -> insert 
    	// keyholder -> new GeneratedKeyHolder()
    	// jdbcTemplate.update method(conn ->
    	// name, address, rooms - total , avail, price per night
    	
    	 String sql = "INSERT INTO hotels (name, address, total_rooms, available_rooms, price_per_night) " +
                "VALUES (?, ?, ?, ?, ?)";
         KeyHolder keyHolder = new GeneratedKeyHolder(); // capture auto generated key
         jdbcTemplate.update(connection -> {                       // jdbc return auto generated key
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, hotel.getName());
            ps.setString(2, hotel.getAddress());
            ps.setInt(3, hotel.getTotal_rooms());
            ps.setInt(4, hotel.getAvailable_rooms());
            ps.setBigDecimal(5, hotel.getPrice_per_night());
            return ps;
            }, keyHolder);   
            Number key = keyHolder.getKey();
            if (key != null) {
        	   hotel.setId(key.longValue());
            }
            return hotel; 	// hotel object along with its generated key    	
    }
	
	@Override
	public Optional<Hotel> findById(Long id) {		
		String sql = "select * from hotels where id=?";
		List<Hotel> list = jdbcTemplate.query(sql, new HotelRowMapper(),id);
		if (list.isEmpty()) return Optional.empty();
		return Optional.of(list.get(0));		
	}

	@Override
	public List<Hotel> findAll(){
	   String sql = "SELECT * FROM hotels";
	   return jdbcTemplate.query(sql, new HotelRowMapper());
	}
	 
	@Override
	public int update(Hotel hotel) {
		String sql="update hotels set name =?,address=?, total_rooms=?, available_rooms  =?,"
				+ " price_per_night =? where id=?";
		return jdbcTemplate.update(sql,
				hotel.getName(),
				hotel.getAddress(),
				hotel.getTotal_rooms(),
				hotel.getAvailable_rooms(),
				hotel.getPrice_per_night(),
				hotel.getId()
				);
	}
	@Override
	public List<Hotel> searchByName(String name) {	
		if(name==null ) {
			return findAll();
		}
		String sql = "select * from hotels where LOWER(name) LIKE ?";
		String input = "%" + name.toLowerCase()+ "%";
		return jdbcTemplate.query(sql, new HotelRowMapper(),input);
		
	}

	@Override
	public int deleteById(Long id) {
		String sql = "delete from hotels where id=?";
		return jdbcTemplate.update(sql,id);
	}
	
}
