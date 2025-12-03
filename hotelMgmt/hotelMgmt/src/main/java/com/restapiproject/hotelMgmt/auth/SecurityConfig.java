package com.restapiproject.hotelMgmt.auth;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
 
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	// how request are autheticated
	// which endpoints require roles
	// how JWT filter is plugged into security
	// what beans are exposed
	
	private final JwtAuthFilter jwtAuthFilter;
	public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
		super();
		this.jwtAuthFilter = jwtAuthFilter;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		// store pwd in MySQL table -> Bcrpyt ->
	}
	
	@Bean
	public AuthenticationManager autheticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	// required for login controller to authenticate credentials
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {			
		// csrf is needed ofr stateful applications - sessions + forms
		// JWT - stateless
		// register
		// login
		http.csrf().disable()
		    .authorizeHttpRequests(auth -> auth
		    		.requestMatchers("/auth/**").permitAll()
		    		.requestMatchers(HttpMethod.GET,"/api/hotels/**").hasAnyRole("USER","ADMIN")
		    		.requestMatchers(HttpMethod.POST, "/api/hotels/**").hasRole("ADMIN")
		    		.requestMatchers(HttpMethod.PUT, "/api/hotels/**").hasRole("ADMIN")
		    		.requestMatchers(HttpMethod.DELETE, "/api/hotels/**").hasRole("ADMIN")
		    		.anyRequest().authenticated()
		     )
		     .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();			
	}
}
 