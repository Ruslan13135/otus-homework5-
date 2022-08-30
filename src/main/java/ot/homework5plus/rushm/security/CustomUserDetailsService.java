package ot.homework5plus.rushm.security;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ot.homework5plus.rushm.models.entity.User;
import ot.homework5plus.rushm.service.impl.UserServiceImpl;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserServiceImpl userService;

    public CustomUserDetailsService(@Lazy UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) {
        User userEntity = userService.findByUsername(username);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(userEntity);
    }
}
