package com.example.EventManager.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EventManager.Entity.UserEntity;
import com.example.EventManager.Repository.UserRepo;

@Service
public class UserService {
    
    @Autowired
    private UserRepo userRepo;
    
    public void updateUser(UserEntity user) {
        userRepo.save(user);
    }
    
    public UserEntity findById(int id) {
        return userRepo.findById(id).orElse(null);
    }
    
    public UserEntity findByUsernameAndPassword(String username, String password) {
        return userRepo.findByUsernameAndPass(username, password);
    }
    
    public byte[] getUserProfileImageById(int id) {
        UserEntity user = userRepo.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        return user.getProfileImage();
    }
    
    public List<UserEntity> getAllUsers() {
        return userRepo.findAll();
    }
    
    public UserEntity findById1(int id) {
        return userRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }



}