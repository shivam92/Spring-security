package com.smartmind.spring.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain secuirtyFilterChain(HttpSecurity http) throws Exception {
		http.csrf(customizer->customizer.disable())
		.authorizeHttpRequests(customizer->customizer.anyRequest().authenticated())
		.httpBasic(Customizer.withDefaults())
		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	
	}
	@Bean
	public UserDetailsService userDetails() {
		UserDetails user=User
				.withDefaultPasswordEncoder()
				.username("samrtmind")
				.password("45678")
				.roles("USER")
				.build();
		
		
		UserDetails  admin=User.withDefaultPasswordEncoder()
				.username("samrtmindadmin")
				.password("12345")
				.roles("ADMIN")
				.build();
		
		
		return new InMemoryUserDetailsManager(user,admin);
		        
	}
}
