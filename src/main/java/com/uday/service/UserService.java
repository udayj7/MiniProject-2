package com.uday.service;

import org.springframework.stereotype.Component;

import com.uday.binding.LoginForm;
import com.uday.binding.SignUpForm;
import com.uday.binding.UnlockForm;

public interface UserService {

	public boolean signUp(SignUpForm form);

	public boolean unloackAccount(UnlockForm form);
//
//	public String forgotPwd(String email);
//
	public String login(LoginForm form);

}
