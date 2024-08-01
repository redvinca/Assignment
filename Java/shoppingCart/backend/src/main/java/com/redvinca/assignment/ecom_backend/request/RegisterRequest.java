package com.redvinca.assignment.ecom_backend.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

	@NotBlank(message = "First name is required")
	private String firstName;

	@NotBlank(message = "Last name is required")
	private String lastName;

	@NotBlank(message = "Address is required")
	private String address;

	@NotBlank(message = "City is required")
	private String city;

	@NotNull(message = "Pincode is required")
	@Pattern(regexp = "^[0-9]{6}$", message = "Pincode must be a 6-digit number")
	private String pincode;

	@NotBlank(message = "Mobile number is required")
	@Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be a 10-digit number")
	private String mobileNumber;

	@NotBlank(message = "Email is required")
	@Email(message = "Email should be valid")
	private String email;

	@NotBlank(message = "Password is required")
	@Size(min = 8, message = "Password should have at least 8 characters")
	private String password;

	@NotBlank(message = "Confirm password is required")
	private String confirmPassword;
}
