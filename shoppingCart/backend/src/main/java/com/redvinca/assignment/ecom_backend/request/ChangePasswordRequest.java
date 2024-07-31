package com.redvinca.assignment.ecom_backend.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    @NotBlank(message = "Email is required")
	@Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String currentPassword;

    @NotBlank(message = "Password is required")
    private String newPassword;
}