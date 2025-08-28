package com.example.EventManager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.EventManager.Repository.UserRepo;
import com.example.EventManager.Service.EmailService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import com.example.EventManager.Entity.UserEntity;


@Controller
@RequestMapping("/landing")
public class AuthController {
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private EmailService emailService;
	
	@GetMapping("/")
	public String landing()
	{
		return "landing";
	}
	
	@GetMapping("/login")
	public String Login(Model model)
	{
		model.addAttribute("user",new UserEntity());
		return "login";
	}
	
	@GetMapping("/register")
	public String Register(Model model)
	{
		model.addAttribute("user",new UserEntity());
		return "register";
	}
	
	@PostMapping("/register")
	public String AddUser(@Valid @ModelAttribute("user") UserEntity user, BindingResult bindingResult, Model model)
	{
		if(bindingResult.hasErrors())
		{
			// Return to the register page with validation errors
			return "register";
		}
		
		try {
			user.setRole("User");
			repo.save(user);
			// ✅ Send welcome email here
			try {
				emailService.sendEmail(
					    user.getEmail(),
					    "Welcome to EventForge!",
					    "Hello " + user.getFullname() + ",\n\n"
					    + "Your account has been created successfully.\n\n"
					    + "You can now explore and register for the latest technology events, manage your preferences, and stay informed about upcoming opportunities.\n\n"
					    + "Thank you for joining EventForge. We’re excited to have you onboard!\n\n"
					    + "Best regards,\n"
					    + "The EventForge Team"
					);

			} catch (Exception emailEx) {
				emailEx.printStackTrace();
				model.addAttribute("error", "Account created, but failed to send confirmation email.");
				return "register"; // Optional: redirect anyway or show success
			}
			
			return "redirect:/landing/login";
		} catch (Exception e) {
			// Handle any other exceptions that might occur during save
			model.addAttribute("error", "Registration failed. Please try again.");
			return "register";
		}
	}
	
	@PostMapping("/login")
	public String Login(@RequestParam String username,@RequestParam String password, Model model, HttpSession session)
	{
		System.out.println("Email: "+username+"Pass: "+password);
		UserEntity user = repo.findByUsernameAndPass(username, password);
		if(user!=null)
		{
			session.setAttribute("loggedUser", user);
			if("Admin".equalsIgnoreCase(user.getRole()))
			{
				return "redirect:/admin/home"; // need to change this when i work with the main controller
			}
			else
			{
				return "redirect:/user/home"; // need to change this when i work with the main controller
			}
		}
		else
		{
			model.addAttribute("Error","Invalid Credentials");
			return "login";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/landing/login";
	}
}