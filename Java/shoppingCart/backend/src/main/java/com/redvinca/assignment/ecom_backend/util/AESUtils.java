package com.redvinca.assignment.ecom_backend.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class AESUtils {

	private static String ALGORITHM = "AES";
	private static SecretKey secretKey;

	@Value("${aes.secret.key}")
	private String key;

	// This method initializes the SecretKey using the key retrieved from
	// application.properties
	@PostConstruct
	public void init() {
		secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
	}

	// Returns the stored SecretKey instance
	// Note: This method is static to allow access without creating an instance of
	// AESUtils
	public static SecretKey getStoredKey() {
		return secretKey;
	}

	// Encrypts the given data using the provided SecretKey
	// Returns the encrypted data as a Base64-encoded string
	public static String encrypt(String data, SecretKey key) throws Exception {
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encryptedData = cipher.doFinal(data.getBytes());
		return Base64.getEncoder().encodeToString(encryptedData);
	}

	// Decrypts the given Base64-encoded encrypted data using the provided SecretKey
	// Returns the decrypted data as a string
	public static String decrypt(String encryptedData, SecretKey key) throws Exception {
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decodedData = Base64.getDecoder().decode(encryptedData);
		byte[] decryptedData = cipher.doFinal(decodedData);
		return new String(decryptedData);
	}
}
