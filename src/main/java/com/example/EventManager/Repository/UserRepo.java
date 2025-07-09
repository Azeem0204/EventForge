package com.example.EventManager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EventManager.Entity.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer>{

	UserEntity findByUsernameAndPass(String username,String pass);
}
