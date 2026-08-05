package com.baubekTns.url_shortener.service.impl;

import com.baubekTns.url_shortener.dto.auth.RegisterRequest;
import com.baubekTns.url_shortener.dto.auth.UserResponse;
import com.baubekTns.url_shortener.entity.User;
import com.baubekTns.url_shortener.exception.EmailAlreadyExistsException;
import com.baubekTns.url_shortener.mapper.UserMapper;
import com.baubekTns.url_shortener.repository.UserRepository;
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

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

}
