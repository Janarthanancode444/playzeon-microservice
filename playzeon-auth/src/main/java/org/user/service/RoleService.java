package org.user.service;

import org.app.entity.Roles;
import org.app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.user.dto.ResponseDTO;
import org.user.dto.RolesDTO;
import org.user.repository.RolesRepository;
import org.user.repository.UserRepository;
import org.user.util.Constants;

@Service
public class RoleService {
    @Autowired
    private UserRepository userRepository;
    private final RolesRepository rolesRepository;

    public RoleService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public ResponseDTO createRole(final RolesDTO rolesDTO) {
        final User user = this.userRepository.findById(rolesDTO.getUserId()).orElseThrow(() -> new UsernameNotFoundException(Constants.NOT_FOUND));
        final Roles roles = new Roles();
        roles.setRole(rolesDTO.getRole());
        roles.setUser(user);
        roles.setCreatedBy(rolesDTO.getCreatedBy());
        roles.setUpdatedBy(rolesDTO.getUpdatedBy());
        return new ResponseDTO(Constants.CREATED, this.rolesRepository.save(roles), HttpStatus.OK.getReasonPhrase());
    }
}
