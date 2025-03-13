package org.example.authservice.services;

import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.authservice.dao.UserRepository;
import org.example.authservice.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public static Logger logger = LogManager.getLogger(UserService.class);
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public User getByUsername(@NonNull String username)  {
      try {
          return userRepository.findByUsername(username);
      } catch (Exception e) {
          logger.error(e + "Пользователь не найден");

      }
        return null;
    }

public UserDetailsService getUserDetailsService() {
        return this::getByUsername;
}

public User getCurrentUser() {
    var username = SecurityContextHolder.getContext().getAuthentication().getName();
    return getByUsername(username);

}





}