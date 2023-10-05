package com.uday.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uday.binding.DashboardResponse;
import com.uday.binding.EnquiryForm;
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

		model.addAttribute("dashboardData", dashboardData);
		return "dashboard";
	}

	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("formObj") EnquiryForm formObj, Model model) {

		System.out.println(formObj);
		boolean status = enqService.saveEnquiry(formObj);
		if (status) {
			model.addAttribute("successMsg", "Enquiry Added");
		} else {
			model.addAttribute("errorMsg", "Problem Occured");
		}

		return "add-enquiry";

	}

	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {

		// get courses for drop down
		List<String> courses = enqService.getCourse();

		// get enquiry status for drop down
		List<String> enqStatuses = enqService.getEnquStatuses();

		// create binding class obj
		EnquiryForm formObj = new EnquiryForm();

		// set data in model obj
		model.addAttribute("courseNames", courses);
		model.addAttribute("statusNames", enqStatuses);
		model.addAttribute("formObj", formObj);

		return "add-enquiry";
	}

	@GetMapping("/enquires")
	public String viewEnquiesPage() {
		return "view-enquiries";
	}

}
