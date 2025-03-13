package org.example.securityservice.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserInfoService {
    public UserDetails getCurrentInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return (UserDetails) principal;
            }
        }
throw new RuntimeException("Пользователь не авторизован");
    }



    public String getCurrentRole(){
        return getCurrentInfo().getAuthorities().iterator().next().getAuthority();
    }
    public String getCurrentUserName(){
        return getCurrentInfo().getUsername();
    }



}
