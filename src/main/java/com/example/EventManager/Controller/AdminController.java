package com.example.EventManager.Controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.EventManager.Entity.RegisteredEvent;
import com.example.EventManager.Entity.UserEntity;
import com.example.EventManager.Service.RegisteredEventService;
import com.example.EventManager.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userSerivce;
	
	@Autowired
	private RegisteredEventService registeredEventService;

	
	@GetMapping("/home")
	public String adminHome(HttpSession session, Model model) {
		UserEntity user = (UserEntity) session.getAttribute("loggedUser");
		if(user != null && "Admin".equalsIgnoreCase(user.getRole()))
		{
			model.addAttribute("EventList", new ArrayList<>());
			model.addAttribute("user", user);

			model.addAttribute("category", "All");
			return "index";
		}
		return "redirect:/login";
	}
	
	@GetMapping("/users")
	public String viewAllUsers(Model model) {
	    List<UserEntity> allUsers = userSerivce.getAllUsers();
	    List<UserEntity> nonAdminUsers = new ArrayList<>();
	    
	    for (UserEntity user : allUsers) {
	        if (!"Admin".equalsIgnoreCase(user.getRole())) {
	            nonAdminUsers.add(user);
	        }
	    }

	    model.addAttribute("users", nonAdminUsers);
	    return "users";
	}
	@GetMapping("/users/{userId}/registrations")
	public String viewUserRegistrations(@PathVariable int userId, Model model) {
	    UserEntity user = userSerivce.findById1(userId);
	    List<RegisteredEvent> registrations = registeredEventService.getRegisteredEventsByUser(user);
	    
	    model.addAttribute("user", user);
	    model.addAttribute("registrations", registrations);
	    return "user-registrations";
	}



}
