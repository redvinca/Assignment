package com.redvinca.assignment.ecom_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redvinca.assignment.ecom_backend.request.ChangePasswordRequest;
import com.redvinca.assignment.ecom_backend.request.LoginRequest;
import com.redvinca.assignment.ecom_backend.request.RegisterRequest;
import com.redvinca.assignment.ecom_backend.response.LoginRegisterResponse;
import com.redvinca.assignment.ecom_backend.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${user.api.url}")
public class LoginRegistrationController {

	@Autowired
	private UserService userService;

	@PostMapping("${user.api.signUp}")
	@Operation(summary = "Register a new user", description = "Provide user details to register")
	public LoginRegisterResponse registerUser(@RequestBody @Valid RegisterRequest registerRequest) {
		return userService.registerUser(registerRequest);
	}

//	@GetMapping("/confirm-account")
//	@Operation(summary = "Confirm user account", description = "Confirm user account with the provided token")
//	public LoginRegisterResponse confirmUserAccount(@RequestParam("token") String confirmationToken) {
//		return userService.confirmEmail(confirmationToken);
//	}

	@PostMapping("${user.api.signIn}")
	@Operation(summary = "User login", description = "Provide user credentials to login")
	public LoginRegisterResponse loginUser(@RequestBody @Valid LoginRequest loginRequest) {
		return userService.loginUser(loginRequest);
	}

	@Operation(summary = "This API is used to log out the user.")
	@PostMapping("${user.api.logout}")
	public LoginRegisterResponse logout(HttpSession session) {
		return userService.logoutUser(session);
	}

	@Operation(summary = "This API is used to change the Password of user.")
	@PostMapping("${user.api.changePassword}")
	public LoginRegisterResponse changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
		return userService.changePassword(changePasswordRequest);
	}
}
