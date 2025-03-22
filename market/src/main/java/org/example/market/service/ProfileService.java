package org.example.market.service;

import lombok.RequiredArgsConstructor;
import org.example.market.dto.ResponseUserInfo;
import org.example.market.entity.User;
import org.example.market.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;

    @Transactional
    public ResponseUserInfo getUserInfo(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            ResponseUserInfo responseUserInfo = ResponseUserInfo.builder()
                    .username(user.get().getUsername())
                    .sales()
                    .purchases()
                    .build();
        }
    }
}
