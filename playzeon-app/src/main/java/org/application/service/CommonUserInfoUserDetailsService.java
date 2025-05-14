package org.application.service;

import org.app.entity.Roles;
import org.app.entity.User;
import org.application.dto.UserInfoUserDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.user.repository.RolesRepository;
import org.user.repository.UserRepository;


@Component
public class CommonUserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private RolesRepository rolesRepository;
    @Autowired(required = true)
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException(org.user.util.Constants.NOT_FOUND + username));

        final Roles roles = rolesRepository.findRoleByUserId(user.getId());

        return new UserInfoUserDetailsDTO(user, roles);
    }
}