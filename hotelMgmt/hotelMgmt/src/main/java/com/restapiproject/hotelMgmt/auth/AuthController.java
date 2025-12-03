package com.restapiproject.hotelMgmt.auth;

import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

	
	private final UserRepository userRepository;
	private JwtUtil jwtUtil;
	private PasswordEncoder passwordEncoder;
	
	public AuthController(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.jwtUtil = jwtUtil;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	@PostMapping("/register")
	public String register(@RequestBody Map<String,String> userMap) {
		User user = new User();
		user.setUsername(userMap.get("username"));
		user.setPassword(passwordEncoder.encode(userMap.get("password")));
		user.setRole(userMap.getOrDefault("role", "ROLE_USER"));
		userRepository.save(user);
		return "User registered successfully";
	}
	@PostMapping("/login")
	public String login(@RequestBody Map<String,String> userMap) {
		User user = userRepository.findByUsername(userMap.get("username"));
		if(user == null) return "User not found";
		if(!passwordEncoder.matches(userMap.get("password"), user.getPassword())) return "Invalid credentials";
		return jwtUtil.generateToken(user.getUsername());
	}
	
}
