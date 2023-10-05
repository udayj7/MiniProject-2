package com.uday.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uday.binding.LoginForm;
import com.uday.binding.SignUpForm;
import com.uday.binding.UnlockForm;
import com.uday.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	// Load signup page
	@GetMapping("/signup")
	public String signUpPage(Model model) {
		model.addAttribute("user", new SignUpForm());
		System.out.println(model);
		return "signup";
	}

	@PostMapping("/signup")
	public String handleSignUp(@ModelAttribute("user") SignUpForm form, Model model) {
		boolean status = userService.signUp(form);
		System.err.println(status);
		if (!status) {
			model.addAttribute("successMsg", "Account Created, Check Your Mail");
		} else {
			model.addAttribute("errorMsg", "Already Email exist, Choose Different Mail ID");
		}
		return "signup";
	}

	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {
		System.out.println(loginForm);
		String status = userService.login(loginForm);
		if (status.contains("Success")) {
			// redirect req to dashboard method
//		// redirect "dashboard";
			return "redirect:/dashboard";
		}
		model.addAttribute("errorMsg", status);

		return "login";
	}

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email, Model model) {

		UnlockForm unlockFormObj = new UnlockForm();
		unlockFormObj.setEmail(email);

		model.addAttribute("unlock", unlockFormObj);
		return "unlock";

	}

	@PostMapping("/unlock")
	public String unlockUserAccount(@ModelAttribute("unlock") UnlockForm unlock, Model model) {
		System.out.println(unlock);

		if (unlock.getNewPwd().equals(unlock.getConfirmPwd())) {
			boolean status = userService.unloackAccount(unlock);

			if (status) {
				model.addAttribute("successMsg", "Your Account Unlocked Successfully");
			} else {
				model.addAttribute("errorMsg", "Given Temporary Pwd is Incorrect, check your email");
			}
		} else {
			model.addAttribute("errorMsg", "Your New Pwd and Confirm pwd should not same");
		}

		return "unlock";
	}

	@GetMapping("/forgot")
	public String forgotPwdPage() {
		return "forgotPwd";
	}

}
