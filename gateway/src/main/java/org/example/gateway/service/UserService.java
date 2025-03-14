package org.example.gateway.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.gateway.dao.UserRepository;
import org.example.gateway.model.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    final Logger logger = LogManager.getLogger(UserService.class);

    public User getByUsername(String username) {
        try {
            return userRepository.findByUsername(username);
        }catch (Exception e){
             logger.error(e);
             return null;
        }
    }
}
