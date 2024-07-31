package com.redvinca.assignment.ecom_backend.util;

public class ValidationUtil {

    //Checks if a string is null or empty.
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    //Checks if a number is null or less than or equal to zero.
    public static boolean isNullOrNegative(Long num) {
        return num == null || num <= 0;
    }
    
    //Checks if a number is null or less than or equal to zero.
    public static boolean isNullOrZero(Double num) {
        return num == null || num <= 0;
    }
}
