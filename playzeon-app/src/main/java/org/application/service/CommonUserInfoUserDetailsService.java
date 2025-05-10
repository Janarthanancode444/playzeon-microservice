package org.application.service;

import org.app.entity.User;
import org.application.dto.UserInfoUserDetailsDTO;
import org.application.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.user.repository.UserRepository;

import java.util.Optional;


@Component
public class CommonUserInfoUserDetailsService implements UserDetailsService {

    @Autowired(required = true)
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Optional<User> userInfo = userRepository.findByName(username);
        return userInfo.map(UserInfoUserDetailsDTO::new).orElseThrow(() -> new UsernameNotFoundException(Constants.NOT_FOUND + username));
    }
}