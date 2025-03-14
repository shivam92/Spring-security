package com.smartmind.spring.security.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class Controller {

	@GetMapping("path")
	public String sayHello() {
		return "Hello";
	}
	
}
