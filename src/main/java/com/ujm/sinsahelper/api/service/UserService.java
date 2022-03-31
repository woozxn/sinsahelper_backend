package com.ujm.sinsahelper.api.service;

import com.ujm.sinsahelper.api.entity.user.User;
import com.ujm.sinsahelper.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(String userId) {
        return userRepository.findByUserId(userId);
    }
}
