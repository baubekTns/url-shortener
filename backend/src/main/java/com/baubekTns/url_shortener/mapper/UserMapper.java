package com.baubekTns.url_shortener.mapper;

import com.baubekTns.url_shortener.dto.auth.UserResponse;
import com.baubekTns.url_shortener.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {

        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }

}
