package com.uday.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uday.binding.LoginForm;
import com.uday.binding.SignUpForm;
import com.uday.binding.UnlockForm;
import com.uday.constants.AppConstants;
import com.uday.entity.UserDtlsEntity;
import com.uday.repo.UserDtlsRepo;
import com.uday.util.EmailUtils;
import com.uday.util.PwdUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDtlsRepo userDtlsRepo;

	@Autowired
	private EmailUtils emailUtils;

	@Autowired
	private HttpSession session;

	@Override
	public boolean unloackAccount(UnlockForm form) {

		UserDtlsEntity entity = userDtlsRepo.findByEmail(form.getEmail());

		if (entity.getPwd().equals(form.getTempPwd())) {
			entity.setPwd(form.getNewPwd());
			entity.setAccStatus(AppConstants.STR_UNLOCKED);
			userDtlsRepo.save(entity);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean signUp(SignUpForm form) {

		// Check email existing or not
		UserDtlsEntity findByEmail = userDtlsRepo.findByEmail(form.getEmail());
		if (findByEmail != null) {
			return false;
		}

		// TODO : Copy data from binding object to entity object
		UserDtlsEntity entity = new UserDtlsEntity();
		BeanUtils.copyProperties(form, entity);

		// TODO : generate random pwd and set to object
		String generateRandomPwd = PwdUtils.generateRandomPwd();
		entity.setPwd(generateRandomPwd);

		// TODO : set account status as Locked
		entity.setAccStatus("LOCKED");

		// TODO :Insert record
		userDtlsRepo.save(entity);

		// TODO : Send email to user unlock the account
		String to = form.getEmail();
		String subject = AppConstants.UNLOCK_EMAIL_SUBJECT;

		StringBuffer body = new StringBuffer("");
		body.append("<h1> Use below temporary password to unlock your account</h1>");
		body.append("Temporary Pwd: " + generateRandomPwd);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8080/unlock?email=" + to + "\">Click Here To Unlock your account");

		emailUtils.sendEmail(to, subject, body.toString());

		return false;
	}

	@Override
	public String login(LoginForm form) {
		UserDtlsEntity entity = userDtlsRepo.findByEmailAndPwd(form.getEmail(), form.getPwd());

		if (entity == null) {
			return AppConstants.INVALID_CREDENTIALS_MSG;
		}

		if (entity.getAccStatus().equals(AppConstants.STR_LOCKED)) {
			return AppConstants.STR_ACC_LOCKED_MSG;
		}

		// create session and store user data in session
		session.setAttribute(AppConstants.STR_USER_ID, entity.getUserId());

		return AppConstants.STR_SUCCESS;
	}

	@Override
	public boolean forgotPwd(String email) {

		// check record presence in db with given email
		UserDtlsEntity entity = userDtlsRepo.findByEmail(email);

		// if record not available return false
		if (entity == null) {
			return false;
		}

		// if record availabe send pwd to email and return true
		String Subject = AppConstants.RECOVER_PWD_EMAIL_SUBJECT;
		String body = "Your Pwd :: " + entity.getPwd();

		emailUtils.sendEmail(email, Subject, body);

		return true;
	}

}
