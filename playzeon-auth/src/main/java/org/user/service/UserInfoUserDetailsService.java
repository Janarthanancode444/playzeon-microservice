package org.user.service;

import org.app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.user.dto.UserInfoUserDetailsDTO;
import org.user.repository.UserRepository;
import org.user.util.Constants;

import java.util.Optional;


@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Optional<User> userInfo = userRepository.findByName(username);
        return userInfo.map(UserInfoUserDetailsDTO::new).orElseThrow(() -> new UsernameNotFoundException(Constants.NOT_FOUND + username));
    }
}