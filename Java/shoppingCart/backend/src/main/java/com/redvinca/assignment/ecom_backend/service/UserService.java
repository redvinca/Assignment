package com.redvinca.assignment.ecom_backend.service;


import com.redvinca.assignment.ecom_backend.request.ChangePasswordRequest;
import com.redvinca.assignment.ecom_backend.request.LoginRequest;
import com.redvinca.assignment.ecom_backend.request.RegisterRequest;
import com.redvinca.assignment.ecom_backend.response.LoginRegisterResponse;

import jakarta.servlet.http.HttpSession;

public interface UserService {
	
	LoginRegisterResponse registerUser(RegisterRequest registerRequest);
    
	LoginRegisterResponse loginUser(LoginRequest loginRequest);
	
	//public LoginRegisterResponse  confirmEmail(String confirmationToken);
	
	public LoginRegisterResponse logoutUser(HttpSession session);

	public LoginRegisterResponse changePassword(ChangePasswordRequest request);
    
}
