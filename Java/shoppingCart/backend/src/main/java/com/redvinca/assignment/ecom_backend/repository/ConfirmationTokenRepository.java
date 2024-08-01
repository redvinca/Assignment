package com.redvinca.assignment.ecom_backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redvinca.assignment.ecom_backend.model.ConfirmationToken;

@Repository("confirmationTokenRepository")
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Integer> {
	
	ConfirmationToken findByConfirmationToken(String confirmationToken);
}