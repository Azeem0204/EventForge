package com.example.EventManager.Controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.EventManager.Entity.EventManager;
import com.example.EventManager.Entity.RegisteredEvent;
import com.example.EventManager.Entity.UserEntity;
import com.example.EventManager.Service.EventService;
import com.example.EventManager.Service.RegisteredEventService;

import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/event")
public class EventController {
	
	@Autowired
	private EventService es;
	
	@Autowired
	private RegisteredEventService rs;

	
	@GetMapping("/")
	public String Test()
	{
		return "index";
	}
	
	@GetMapping("/about") 
	public String about()
	{
		return "about";
	}
	
	@GetMapping("/service")
	public String service()
	{
		return "service";
	}
	
	@GetMapping("/contact")
	public String contact()
	{
		return "contact";
	}
	
	@GetMapping("/add")
	public String viewEventForm(Model model)
	{
		model.addAttribute("Food", new EventManager());
		return "add";
	}
	
	@PostMapping("/submit-item")
	public String submitEventForm(@ModelAttribute EventManager event)
	{
		es.submitEventForm(event);
		return "redirect:/event/";
	}
	
	@GetMapping("/admincategory")
	public String getEventByCategory(@RequestParam("name") String category, Model model)
	{
		List<EventManager> eventList = es.getEventByCategory(category);
		
		System.out.println("List: "+eventList);
		model.addAttribute("admincategory", category);
		model.addAttribute("eventList", eventList);
		return "admincategory";
	}
	
	@GetMapping("/category")
	public String getEventByUserCategory(
	        @RequestParam("name") String category,
	        Model model,
	        HttpSession session) {

	    List<EventManager> eventList = es.getEventUserByCategory(category);

	    // Default empty set
	    Set<Integer> registeredEventIds = new HashSet<>();

	    // Check if user is logged in
	    UserEntity user = (UserEntity) session.getAttribute("loggedUser");
	    if (user != null) {
	        List<RegisteredEvent> registeredEvents = rs.getRegisteredEventsByUser(user);
	        for (RegisteredEvent reg : registeredEvents) {
	            // Use correct getter:
	            registeredEventIds.add(reg.getEventDetails().getId());
	        }
	    }

	    model.addAttribute("category", category);
	    model.addAttribute("eventList", eventList);
	    model.addAttribute("registeredEventIds", registeredEventIds);

	    return "usercategory";
	}

	
	
	@GetMapping("/edit/{id}")
	public String editEvent(@PathVariable("id")int id, Model model)
	{
		EventManager event = es.getEventByCategory(id);
		model.addAttribute("event", event);
		return "update";
	} 
	
	@PostMapping("/update")
	public String updateEvent(@ModelAttribute EventManager event) {
		es.UpdateEvent(event);
		return "redirect:/event/admincategory?name="+event.getCategory();
	}
	
	@GetMapping("delete/{id}")
	public String deleteEvent(@PathVariable("id") int id) {
	    EventManager event = es.getEventByCategory(id); // fetch event by id
	    if (event != null) {
	        String category = event.getCategory();  // get category before deletion
	        es.DeleteEvent(id);                     // delete event
	        return "redirect:/event/admincategory?name=" + category; // redirect with category param
	    }
	    // fallback
	    return "redirect:/event/admincategory?name="+ event.getCategory();
	}
	
//	@GetMapping("/eventregister/{id}")
//	public String Eventregister(@PathVariable("id") int id, Model model)
//	{
//		EventManager event = es.registerEventbyCategory(id);
//		model.addAttribute("event", event);
//		return "eventregister";
//	}

	@GetMapping("/view/{id}")
	public String ViewEvent(@PathVariable("id") int id, Model model)
	{
		EventManager event = es.ViewEvent(id);
		model.addAttribute("event", event);
		return "viewmore";
	}
	
	
//	for register
	@GetMapping("/register/{id}")
	public String showRegistrationForm(@PathVariable("id") int id, Model model, HttpSession session) {
	    // Check if user is logged in
	    UserEntity user = (UserEntity) session.getAttribute("loggedUser");
	    if (user == null) {
	        return "redirect:/landing/login";
	    }
	    
	    EventManager event = es.ViewEvent(id);
	    model.addAttribute("event", event);
	    model.addAttribute("registeredEvent", new RegisteredEvent());
	    return "eventregister";
	}

	// Add this new method to link the actual event to the registration
	@PostMapping("/submit")
	public String submitRegistration(@ModelAttribute RegisteredEvent events, 
	                                @RequestParam("eventId") int eventId, 
	                                HttpSession session) {
	    // Get logged-in user
	    UserEntity user = (UserEntity) session.getAttribute("loggedUser");
	    if (user == null) {
	        return "redirect:/landing/login";
	    }
	    
	    // Set the user for this registration
	    events.setUser(user);
	    
	    // Get the actual event details
	    EventManager eventDetails = es.ViewEvent(eventId);
	    events.setEventDetails(eventDetails);
	    
	    rs.saveRegistration(events);
	    // Redirect to usercategory page with the event category param
	    return "redirect:/event/category?name=" + events.getEvent();
	}
	





}
