package com.baubekTns.url_shortener.service;

import com.baubekTns.url_shortener.dto.auth.LoginRequest;
import com.baubekTns.url_shortener.dto.auth.LoginResponse;
import com.baubekTns.url_shortener.dto.auth.RegisterRequest;
import com.baubekTns.url_shortener.dto.auth.UserResponse;

public interface AuthService {

    UserResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}
