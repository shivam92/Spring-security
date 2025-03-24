package com.smartmind.spring.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartmind.spring.security.entity.UserEntity;
import com.smartmind.spring.security.repo.UsersRepo;
@Service
public class RegUserService {
	@Autowired
	UsersRepo repo;
	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
	public UserEntity saveUser(UserEntity usersEntity) {
		usersEntity.setPassword(encoder.encode(usersEntity.getPassword()));
		repo.save(usersEntity);
		return usersEntity;
	}

}
