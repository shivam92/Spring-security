package com.smartmind.spring.security.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smartmind.spring.security.entity.UserEntity;

public class UserPrincipal implements UserDetails {
  private  UserEntity entity;
	public UserPrincipal(UserEntity entity) {
		this.entity=entity;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Collections.singleton(new SimpleGrantedAuthority("USER"));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return entity.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return entity.getUserName();
	}

}
