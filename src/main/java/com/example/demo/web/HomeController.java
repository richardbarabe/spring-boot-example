package com.example.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the application home page.
 * 
 * @author richardbarabe
 *
 */
@Controller
public class HomeController {
	
	/**
	 * For now, the default page will redirect to the persons page.
	 */
	@GetMapping("/")
	public String home() {
		return "redirect:/persons";
	}
}
