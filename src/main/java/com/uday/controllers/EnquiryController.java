package com.uday.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uday.binding.DashboardResponse;
import com.uday.service.EnquiryService;

@Controller
public class EnquiryController {

	@Autowired
	public EnquiryService enqService;

	@Autowired
	private HttpSession session;

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {

		Integer userId = (Integer) session.getAttribute("userId");
		
		DashboardResponse dashboardData = enqService.getDashboardData(userId);

		model.addAttribute("dashboardData",dashboardData);
		return "dashboard";
	}

	@GetMapping("/enquiry")
	public String addEnquiryPage() {
		return "add-enquiry";
	}

	@GetMapping("/enquires")
	public String viewEnquiesPage() {
		return "view-enquiries";
	}

}
