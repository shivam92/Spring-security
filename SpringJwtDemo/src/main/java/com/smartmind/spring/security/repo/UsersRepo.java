package com.smartmind.spring.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartmind.spring.security.entity.UserEntity;

@Repository
public interface UsersRepo extends JpaRepository<UserEntity, String> {
	public UserEntity findByUserName(String userName);

}
