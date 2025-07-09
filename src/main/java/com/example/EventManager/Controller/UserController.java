package com.example.EventManager.Controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.EventManager.Entity.RegisteredEvent;
import com.example.EventManager.Entity.UserEntity;
import com.example.EventManager.Repository.UserRepo;
import com.example.EventManager.Service.RegisteredEventService;
import com.example.EventManager.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

   
	
	@Autowired
	private RegisteredEventService registeredEventService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepo userRepo;
	
	@GetMapping("/home")
	public String userlogin(HttpSession session,Model model)
	{
		UserEntity user = (UserEntity) session.getAttribute("loggedUser");
		model.addAttribute("user", user);
		if (user != null && "User".equalsIgnoreCase(user.getRole()))
		{
			return "userhome";
		}
		return "redirect:/landing/login";
	}
	
	@GetMapping("/profile")
	public String userProfile(HttpSession session, Model model)
	{
		UserEntity user = (UserEntity) session.getAttribute("loggedUser");
		if (user != null && "User".equalsIgnoreCase(user.getRole()))
		{
			model.addAttribute("user", user);
			
			// this ones used for fetching registed event detail
			List<RegisteredEvent> registeredEvents = registeredEventService.getRegisteredEventsByUser(user);
			model.addAttribute("registeredEvents", registeredEvents);
			
			return "profile";
		}
		return "redirect:/landing/login";
	}
	
	@PostMapping("/profile/update")
	public String updateProfile(
	    @RequestParam("id") int id,
	    @RequestParam("username") String username,
	    @RequestParam("fullname") String fullname,
	    @RequestParam("email") String email,
	    @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
	    RedirectAttributes redirectAttributes
	) {
	    try {
	        UserEntity user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

	        user.setUsername(username);
	        user.setFullname(fullname);
	        user.setEmail(email);

	        if (profileImage != null && !profileImage.isEmpty()) {
	            user.setProfileImage(profileImage.getBytes());
	        }

	        userRepo.save(user);
	        redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");

	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Error updating profile: " + e.getMessage());
	    }

	    return "redirect:/user/profile";
	}

	@GetMapping("/profile/photo/{id}")
	@ResponseBody
	public ResponseEntity<byte[]> getUserProfilePhoto(@PathVariable("id") int id) {
	    UserEntity user = userRepo.findById(id).orElse(null);
	    if (user == null || user.getProfileImage() == null) {
	        return ResponseEntity.notFound().build();
	    }

	    return ResponseEntity
	            .ok()
	            .header("Content-Type", "image/jpeg")  // or detect MIME type dynamically
	            .body(user.getProfileImage());
	}


	
	@PostMapping("/cancel-registration/{id}")
	@ResponseBody
	public ResponseEntity<?> cancelRegistration(@PathVariable("id") int registrationId, HttpSession session) {
	    try {
	        // Get the logged-in user from session
	        UserEntity currentUser = (UserEntity) session.getAttribute("loggedUser");
	        
	        if (currentUser == null || !"User".equalsIgnoreCase(currentUser.getRole())) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login to cancel registration.");
	        }
	        
	        // Use the username from the session user
	        String username = currentUser.getUsername();
	        boolean success = registeredEventService.cancelRegistration(registrationId, username);

	        if (success) {
	            return ResponseEntity.ok().body("Registration cancelled successfully!");
	        } else {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to cancel this registration.");
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to cancel registration: " + e.getMessage());
	    }
	}
}