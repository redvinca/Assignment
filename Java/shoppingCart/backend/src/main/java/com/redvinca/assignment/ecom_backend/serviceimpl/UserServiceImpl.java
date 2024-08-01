package com.redvinca.assignment.ecom_backend.serviceimpl;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redvinca.assignment.ecom_backend.model.User;
import com.redvinca.assignment.ecom_backend.repository.UserRepository;
import com.redvinca.assignment.ecom_backend.request.ChangePasswordRequest;
import com.redvinca.assignment.ecom_backend.request.LoginRequest;
import com.redvinca.assignment.ecom_backend.request.RegisterRequest;
import com.redvinca.assignment.ecom_backend.response.LoginRegisterResponse;
import com.redvinca.assignment.ecom_backend.service.UserService;
import com.redvinca.assignment.ecom_backend.util.AESUtils;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public LoginRegisterResponse registerUser(RegisterRequest registerRequest) {
		LoginRegisterResponse response = new LoginRegisterResponse();

		try {
			// Check if user with the same email already exists
			if (userRepository.findByEmail(registerRequest.getEmail()) != null) {
				response.setMessage("This email is already registered...!.");
				return response;
			}
			User user = new User();
			user.setFirstName(registerRequest.getFirstName());
			user.setLastName(registerRequest.getLastName());

			user.setAddress(registerRequest.getAddress());

			user.setCityName(registerRequest.getCity());

			user.setPincode(registerRequest.getPincode());
			user.setEmail(registerRequest.getEmail());
			user.setMobileNumber(registerRequest.getMobileNumber());

			// Encrypt password
			SecretKey secretKey = AESUtils.getStoredKey();
			log.info(" SecretKey--at registation :-" + secretKey);
			String encryptedPassword = AESUtils.encrypt(registerRequest.getPassword(), secretKey);
			user.setPassword(encryptedPassword);
			user.setConfirmPassword(registerRequest.getConfirmPassword()); // Consider whether you need to store this

			userRepository.save(user);
			response.setMessage("User registered successfully..!");
		} catch (Exception e) {
			response.setMessage("Error registering user");
		}
		return response;
	}

	@Override
	public LoginRegisterResponse loginUser(LoginRequest loginRequest) {
		// Fetch the user using the email from the loginRequest
		User user = userRepository.findByEmail(loginRequest.getEmail());
		System.out.println("--user--" + user);

		// Create a response object
		LoginRegisterResponse response = new LoginRegisterResponse();

		// Check if the user exists
		if (user == null) {
			response.setMessage("Invalid username/password");
			return response;
		}

		try {
			// Retrieve the stored secret key
			SecretKey secretKey = AESUtils.getStoredKey();
			log.info("Check SecretKey--at registation :-" + secretKey);

			// Decrypt the stored password
			String decryptedPassword = AESUtils.decrypt(user.getPassword(), secretKey);
			log.info("Check DecryptedPassword--at Login :-" + decryptedPassword);

			// Validate the password
			if (decryptedPassword.equals(loginRequest.getPassword())) {
				response.setMessage("Login successful!");
			} else {
				response.setMessage("Invalid username/password");
			}
		} catch (Exception e) {
			response.setMessage("Error logging in");
		}

		return response;
	}

	@Override
	public LoginRegisterResponse logoutUser(HttpSession session) {
		LoginRegisterResponse response = new LoginRegisterResponse();
		try {
			session.invalidate();
			response.setMessage("User logged out successfully!");
		} catch (Exception e) {
			response.setMessage("Error logging out user");
		}
		return response;
	}

	@Override
	public LoginRegisterResponse changePassword(ChangePasswordRequest request) {
		// Retrieve the user from the repository using the email provided in the request
		User user = userRepository.findByEmail(request.getEmail());
		LoginRegisterResponse response = new LoginRegisterResponse();

		// Check if the user is found
		if (user == null) {
			response.setMessage("User not found.");
			return response;
		}

		try {
			// Retrieve the stored secret key for encryption/decryption
			SecretKey secretKey = AESUtils.getStoredKey();

			// Decrypt the user's current password
			String decryptedPassword = AESUtils.decrypt(user.getPassword(), secretKey);

			// Check if the current password provided in the request matches the decrypted
			// password
			if (!decryptedPassword.equals(request.getCurrentPassword())) {
				response.setMessage("Current password is incorrect.");
				return response;
			}

			// Check if the new password is different from the current password
			if (decryptedPassword.equals(request.getNewPassword())) {
				response.setMessage("New password cannot be the same as the current password.");
				return response;
			}

			// Encrypt the new password
			String newEncryptedPassword = AESUtils.encrypt(request.getNewPassword(), secretKey);

			// Update the user's password with the new encrypted password
			user.setPassword(newEncryptedPassword);

			// Save the updated user entity back to the repository
			userRepository.save(user);

			// Set the success message in the response
			response.setMessage("Password changed successfully.");
		} catch (Exception e) {
			// In case of an exception, set the error message in the response
			response.setMessage("Error changing password.");
		}

		// Return the response
		return response;
	}
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	EmailService emailService;
//
//	@Autowired
//	ConfirmationTokenRepository confirmationTokenRepository;
//
//	@Override
//	@Transactional
//	public LoginRegisterResponse registerUser(RegisterRequest registerRequest) {
//		LoginRegisterResponse response = new LoginRegisterResponse();
//		String userEmail = registerRequest.getEmail();
//
//		try {
//			// Check if user with the same email already exists
//			if (userRepository.findByEmail(registerRequest.getEmail()) != null) {
//				response.setMessage("Error: Email is already in use!");
//				return response;
//			}
//
//			// Create a new user object and set its properties from the request
//			User user = new User();
//			user.setFirstName(registerRequest.getFirstName());
//			user.setLastName(registerRequest.getLastName());
//			user.setAddressLine1(registerRequest.getAddressLine1());
//			user.setAddressLine2(registerRequest.getAddressLine2());
//			user.setCityName(registerRequest.getCity());
//			user.setEmail(registerRequest.getEmail());
//			user.setMobileNumber(registerRequest.getMobileNumber());
//			user.setIsEnabled(false);
//
//			// Encrypt the user's password
//			SecretKey secretKey = AESUtils.getStoredKey();
//			log.info("SecretKey--at registration: " + secretKey);
//			String encryptedPassword = AESUtils.encrypt(registerRequest.getPassword(), secretKey);
//			user.setPassword(encryptedPassword);
//
//			// Save the new user to the repository
//			userRepository.save(user);
//
//			// Create a confirmation token for email verification
//			ConfirmationToken confirmationToken = new ConfirmationToken();
//			confirmationToken.setUser(user); // Associate the user with the token
//			confirmationToken.setCreatedDate(new Date());
//			confirmationToken.setConfirmationToken(UUID.randomUUID().toString()); // Generate the token using UUID
//
//			// Save the confirmation token to the repository
//			confirmationTokenRepository.save(confirmationToken);
//
//			// Compose the email message for account confirmation
//			String message = "Hello " + user.getFirstName() + " " + user.getLastName() + ",<br><br>"
//					+ "Thank you for registering. To confirm your account, please click <a href=\"http://localhost:8080/user/confirm-account?token="
//					+ confirmationToken.getConfirmationToken() + "\">here</a>.<br><br>" + "Regards,<br>"
//					+ "Your Sender Name";
//
//			// Create and send the HTML email message
//			MimeMessage mimeMessage = emailService.createMimeMessage();
//			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
//			try {
//				helper.setText(message, true); // Enable HTML content
//				helper.setTo(userEmail);
//				helper.setSubject("Complete Registration!");
//
//				emailService.sendEmail(mimeMessage);
//			} catch (MessagingException e) {
//				e.printStackTrace();
//				return new LoginRegisterResponse("Error: sending email.");
//			}
//
//			// Log the confirmation token for debugging purposes
//			System.out.println("Confirmation Token: " + confirmationToken.getConfirmationToken());
//
//			return new LoginRegisterResponse("Verify email by the link sent to your email address");
//		} catch (Exception e) {
//			response.setMessage("Error: registering user. Please try again later.");
//		}
//
//		return response;
//	}

//	@Override
//	public LoginRegisterResponse confirmEmail(String confirmationToken) {
//		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
//
//		if (token != null) {
//			User user = token.getUser();
//			user.setIsEnabled(true);
//			userRepository.save(user);
//			return new LoginRegisterResponse("Email verified successfully!");
//		}
//		return new LoginRegisterResponse("Error: Couldn't verify email");
//	}

//	@Override
//	public LoginRegisterResponse loginUser(LoginRequest loginRequest) {
//		// Fetch the user using the email from the loginRequest
//		User user = userRepository.findByEmail(loginRequest.getEmail());
//		System.out.println("--user--" + user);
//
//		// Create a response object
//		LoginRegisterResponse response = new LoginRegisterResponse();
//
//		// Check if the user exists
//		if (user == null) {
//			response.setMessage("Error:Invalid username/password");
//			return response;
//		}
//
//		if (!user.getIsEnabled()) {
//			return new LoginRegisterResponse("Error: Email not verified.");
//		}
//
//		try {
//			// Retrieve the stored secret key
//			SecretKey secretKey = AESUtils.getStoredKey();
//			log.info("Check SecretKey--at registation :-" + secretKey);
//
//			// Decrypt the stored password
//			String decryptedPassword = AESUtils.decrypt(user.getPassword(), secretKey);
//			log.info("Check DecryptedPassword--at Login :-" + decryptedPassword);
//
//			// Validate the password
//			if (decryptedPassword.equals(loginRequest.getPassword())) {
//				response.setMessage("Login successful!");
//			} else {
//				response.setMessage("Error:Invalid username/password");
//			}
//		} catch (Exception e) {
//			response.setMessage("Error: logging in");
//		}
//
//		return response;
//	}

}
