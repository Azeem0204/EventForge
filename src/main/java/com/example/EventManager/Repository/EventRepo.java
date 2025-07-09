package com.example.EventManager.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EventManager.Entity.EventManager;

@Repository
public interface EventRepo extends JpaRepository<EventManager, Integer>{

	List<EventManager> findByCategoryIgnoreCase(String category); 
}
