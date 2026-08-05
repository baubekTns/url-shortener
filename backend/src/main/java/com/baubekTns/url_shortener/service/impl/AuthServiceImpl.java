package com.baubekTns.url_shortener.service.impl;

import com.baubekTns.url_shortener.dto.auth.LoginRequest;
import com.baubekTns.url_shortener.dto.auth.LoginResponse;
import com.baubekTns.url_shortener.dto.auth.RegisterRequest;
import com.baubekTns.url_shortener.dto.auth.UserResponse;
import com.baubekTns.url_shortener.entity.User;
import com.baubekTns.url_shortener.exception.EmailAlreadyExistsException;
import com.baubekTns.url_shortener.exception.InvalidCredentialsException;
import com.baubekTns.url_shortener.mapper.UserMapper;
import com.baubekTns.url_shortener.repository.UserRepository;
import com.baubekTns.url_shortener.security.JwtService;
import com.baubekTns.url_shortener.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    @Override
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }

        User user = User.builder()
                .fullName(request.fullName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        return userMapper.toResponse(
                userRepository.save(user)
        );
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(
                request.password(),
                user.getPassword())) {

            throw new InvalidCredentialsException();
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(
                token,
                "Bearer"
        );
    }

}
