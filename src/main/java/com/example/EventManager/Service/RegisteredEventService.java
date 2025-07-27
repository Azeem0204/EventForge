package com.example.EventManager.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EventManager.Entity.RegisteredEvent;
import com.example.EventManager.Entity.UserEntity;
import com.example.EventManager.Repository.RegisteredEventRepo;

@Service
public class RegisteredEventService {

    @Autowired
    private RegisteredEventRepo registeredEventRepo;

    public void saveRegistration(RegisteredEvent events) {
        registeredEventRepo.save(events);
    }
    
    public List<RegisteredEvent> getRegisteredEventsByUser(UserEntity user) {
        return registeredEventRepo.findByUser(user);
    }
    
    public boolean cancelRegistration(int registrationId, String username) {
        Optional<RegisteredEvent> regOpt = registeredEventRepo.findById(registrationId);

        if (regOpt.isPresent()) {
        	RegisteredEvent registration = regOpt.get();

            // Optional: ensure the user owns the registration
            if (!registration.getUser().getUsername().equals(username)) {
                return false;
            }

            registeredEventRepo.delete(registration);
            return true;
        }

        return false;
    }
    
    public List<RegisteredEvent> getRegisteredEventsByUser1(UserEntity user) {
        return registeredEventRepo.findByUser(user);
    }

    public int countByEventId(int eventId) {
        return registeredEventRepo.countByEventDetailsId(eventId);
    }




}