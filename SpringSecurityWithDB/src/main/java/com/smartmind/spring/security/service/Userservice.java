package com.smartmind.spring.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smartmind.spring.security.entity.UserEntity;
import com.smartmind.spring.security.model.UserPrincipal;
import com.smartmind.spring.security.repo.UserRepo;
@Service
public class Userservice implements UserDetailsService{

	@Autowired
	UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserEntity entity=userRepo.findByUserName(username);
		if(entity==null) {
			System.out.println("User not found");
			throw new UsernameNotFoundException("User not Found");
		}
		return new UserPrincipal(entity);
	}

}
