package com.example.EventManager.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EventManager.Entity.UserEntity;
import com.example.EventManager.Entity.EventManager;
import com.example.EventManager.Entity.RegisteredEvent;
import com.example.EventManager.Repository.EventRepo;

@Service
public class EventService {
	
	@Autowired
	private EventRepo repo;

	public void submitEventForm(EventManager event) {
		repo.save(event);
		
	}

	public List<EventManager> getEventByCategory(String category) {
		
		return repo.findByCategoryIgnoreCase(category);
	}

	public List<EventManager> getEventUserByCategory(String category) {
		// TODO Auto-generated method stub
		return repo.findByCategoryIgnoreCase(category);
	}

	public EventManager getEventByCategory(int id) {
		
		return repo.findById(id).orElse(null);
	}

	public void UpdateEvent(EventManager event) {
		repo.save(event);
		
	}

	public void DeleteEvent(int id) {
		repo.deleteById(id);
		
	}

//	public EventManager registerEventbyCategory(int id) {
//		// TODO Auto-generated method stub
//		return repo.findById(id).orElse(null);
//	}

	public EventManager ViewEvent(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id).orElse(null);
	}

	
}
