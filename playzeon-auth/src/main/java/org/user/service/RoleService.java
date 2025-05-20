package org.user.service;


import jakarta.servlet.http.HttpServletRequest;
import org.app.entity.Roles;
import org.app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.user.dto.ResponseDTO;
import org.user.dto.RolesDTO;
import org.user.exception.UserRequestServiceException;
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

    @Transactional
    public ResponseDTO createRole(final RolesDTO rolesDTO) {
        final User user = this.userRepository.findById(rolesDTO.getUserId()).orElseThrow(() -> new UsernameNotFoundException(Constants.NOT_FOUND));
        final Roles roles = new Roles();
        roles.setRole(rolesDTO.getRole());
        roles.setUser(user);
        roles.setCreatedBy(rolesDTO.getCreatedBy());
        roles.setUpdatedBy(rolesDTO.getUpdatedBy());
        return new ResponseDTO(Constants.CREATED, this.rolesRepository.save(roles), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve() {
        return new ResponseDTO(Constants.RETRIEVED, this.rolesRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO updateUser(final RolesDTO rolesDTO, final String id) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        final Roles roles = this.rolesRepository.findById(id).orElseThrow(() -> new UserRequestServiceException(Constants.User, Constants.EMAIL_PATTERN, request.getRequestURI(), getClass().getName(), Constants.PUT, id, Constants.USERREQUESTEXCEPTION));
        if (rolesDTO.getRole() != null) {
            roles.setRole(roles.getRole());
        }
        return new ResponseDTO(Constants.SUCCESS, this.rolesRepository.save(roles), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO removeUser(final String id) {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (!this.rolesRepository.existsById(id)) {
            throw new UserRequestServiceException(Constants.REOMVE, Constants.EMAIL_PATTERN, request.getRequestURI(), getClass().getName(), Constants.DELETE, id, Constants.USERREQUESTEXCEPTION);
        }
        this.rolesRepository.deleteById(id);
        return new ResponseDTO(Constants.DELETED, id, HttpStatus.OK.getReasonPhrase());
    }
}
