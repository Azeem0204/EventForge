package com.example.EventManager.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EventManager.Entity.RegisteredEvent;
import com.example.EventManager.Entity.UserEntity;

@Repository
public interface RegisteredEventRepo extends JpaRepository<RegisteredEvent, Integer> {
    
    List<RegisteredEvent> findByUser(UserEntity user);
}