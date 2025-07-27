package com.example.EventManager.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public String handleError(Exception e , Model model)
	{
		model.addAttribute("errorMessage", e.getMessage());
		return "error-page";
	}

}
