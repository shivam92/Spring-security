package com.smartmind.spring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smartmind.spring.security.entity.UserEntity;
import com.smartmind.spring.security.service.CustomUserDetailsService;
import com.smartmind.spring.security.service.JwtService;
import com.smartmind.spring.security.service.RegUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class TestController {
@Autowired
RegUserService service;
@Autowired
CustomUserDetailsService customUserDetailsService;
@Autowired
JwtService jwtService;
@Autowired
AuthenticationManager  authenticationManager ;
	@PostMapping("/login")
	public String loginWithUserName(@RequestBody UserEntity user) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(user.getUserName());
		}
		return "login failed";
	}
	@PostMapping("/register")
	public UserEntity registerUser(@RequestBody UserEntity user) {
		//TODO: process POST request
		return service.saveUser(user);
		
	}
	@GetMapping("path")
	public String getMethodName() {
		return new String("hello how are you?");
	}
	
	
}
