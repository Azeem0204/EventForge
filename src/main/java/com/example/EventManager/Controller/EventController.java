package com.example.EventManager.Controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import com.example.EventManager.Service.EmailService;
import com.example.EventManager.Service.EventService;
import com.example.EventManager.Service.RegisteredEventService;

import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/event")
public class EventController {
	
	@Autowired
	private EmailService emailService;

	
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
	    
	    // Add these maps
	    Map<Integer, Integer> remainingSlotsMap = new HashMap<>();
	    Map<Integer, Integer> registeredCountMap = new HashMap<>();

	    // Fill them with event data
	    for (EventManager event : eventList) {
	        int total = event.getMaxQuantity();
	        int registered = rs.countByEventId(event.getId());  // ✅ Make sure this method exists
	        int remaining = total - registered;

	        registeredCountMap.put(event.getId(), registered);
	        remainingSlotsMap.put(event.getId(), Math.max(remaining, 0));
	    }

	    // Add to model
	    model.addAttribute("admincategory", category);
	    model.addAttribute("eventList", eventList);
	    model.addAttribute("remainingSlotsMap", remainingSlotsMap);
	    model.addAttribute("registeredCountMap", registeredCountMap); // ✅ add this

	    return "admincategory";
	}

	
	@GetMapping("/category")
	public String getEventByUserCategory(
	        @RequestParam("name") String category,
	        Model model,
	        HttpSession session) {

	    List<EventManager> eventList = es.getEventUserByCategory(category);

	    Set<Integer> registeredEventIds = new HashSet<>();
	    Map<Integer, Integer> remainingSlotsMap = new HashMap<>();

	    UserEntity user = (UserEntity) session.getAttribute("loggedUser");

	    for (EventManager event : eventList) {
	        int total = event.getMaxQuantity();
	        int registeredCount = rs.countByEventId(event.getId());  // 👈 you'll need this method in service
	        int remaining = total - registeredCount;
	        remainingSlotsMap.put(event.getId(), Math.max(remaining, 0));
	    }

	    if (user != null) {
	        List<RegisteredEvent> registeredEvents = rs.getRegisteredEventsByUser(user);
	        for (RegisteredEvent reg : registeredEvents) {
	            registeredEventIds.add(reg.getEventDetails().getId());
	        }
	    }

	    model.addAttribute("category", category);
	    model.addAttribute("eventList", eventList);
	    model.addAttribute("registeredEventIds", registeredEventIds);
	    model.addAttribute("remainingSlotsMap", remainingSlotsMap); // ✅ Add this

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
	    UserEntity user = (UserEntity) session.getAttribute("loggedUser");
	    if (user == null) {
	        return "redirect:/landing/login";
	    }

	    EventManager event = es.ViewEvent(id);
	    int registered = rs.countByEventId(id);
	    int remaining = event.getMaxQuantity() - registered;

	    if (remaining <= 0) {
	        model.addAttribute("error", "Registration is full for this event.");
	        model.addAttribute("category", event.getCategory());
	        return "full"; // create a simple "registration full" page or reuse an error template
	    }

	    model.addAttribute("event", event);
	    model.addAttribute("registeredEvent", new RegisteredEvent());
	    model.addAttribute("user", user);
	    return "eventregister";
	}


	// Add this new method to link the actual event to the registration
	@PostMapping("/submit")
	public String submitRegistration(
	        @ModelAttribute RegisteredEvent events, 
	        @RequestParam("eventId") int eventId, 
	        HttpSession session,
	        Model model) {

	    // Get logged-in user
	    UserEntity user = (UserEntity) session.getAttribute("loggedUser");
	    if (user == null) {
	        return "redirect:/landing/login";
	    }

	    // Get the actual event details
	    EventManager eventDetails = es.ViewEvent(eventId);
	    
	    // Check if event is full
	    int registeredCount = rs.countByEventId(eventId);
	    int remainingSlots = eventDetails.getMaxQuantity() - registeredCount;
	    if (remainingSlots <= 0) {
	        model.addAttribute("error", "Sorry, this event is already full.");
	        model.addAttribute("category", eventDetails.getCategory()); // ✅ This is good
	        return "full";
	    }


	    // Proceed with registration
	    events.setUser(user);
	    events.setEventDetails(eventDetails);
	    rs.saveRegistration(events);

	    // Send confirmation email
	    try {
	        String toEmail = user.getEmail();
	        String subject = "Event Registration Confirmation";
	        String message = "Dear " + user.getFullname() + ",\n\n"
	                + "You have successfully registered for the event:\n\n"
	                + "Event: " + eventDetails.getName() + "\n"
	                + "Category: " + eventDetails.getCategory() + "\n"
	                + "Date: " + eventDetails.getDate() + "\n"
	                + "Location: " + eventDetails.getLocation() + "\n\n"
	                + "Thank you for registering!\n\n"
	                + "Best regards,\nEvent Management Team";
	        
	        emailService.sendEmail(toEmail, subject, message);
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("error", "Registration saved but failed to send confirmation email.");
	    }

	    // Redirect to category view
	    return "redirect:/event/category?name=" + eventDetails.getCategory();
	}


	





}
