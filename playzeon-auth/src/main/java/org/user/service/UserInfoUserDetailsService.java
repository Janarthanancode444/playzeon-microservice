package org.user.service;

import org.app.entity.Roles;
import org.app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.user.dto.UserInfoUserDetailsDTO;
import org.user.repository.RolesRepository;
import org.user.repository.UserRepository;
import org.user.util.Constants;


@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(Constants.NOT_FOUND + email));

        final Roles roles = rolesRepository.findRoleByUserId(user.getId());

        return new UserInfoUserDetailsDTO(user, roles);
    }

}