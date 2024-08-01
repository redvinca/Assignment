package com.redvinca.assignment.ecom_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "user_address", nullable = false)
	private String address;

	@Column(name = "city", nullable = false)
	private String cityName;

	@Column(name = "pincode", nullable = false)
	private String pincode;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "mobile_number", nullable = false)
	private String mobileNumber;

	@Column(name = "user_password", nullable = false)
	private String password;

	@Column(name = "confirm_password", nullable = false)
	private String confirmPassword;

}
