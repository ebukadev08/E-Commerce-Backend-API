package com.ebuka.ecommerce.service;


public interface AuthService {

    void signup(String firstName, String lastName, String email, String password);

    void resendOtp(String email);

    void verifyOtp(String email, String otp);

    String login(String email, String password);

}
