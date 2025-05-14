package org.user.service;

import jakarta.servlet.http.HttpServletRequest;
import org.app.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.user.dto.AuthDTO;
import org.user.dto.ResponseDTO;
import org.user.dto.UserDTO;
import org.user.exception.BadRequestServiceException;
import org.user.exception.UserRequestServiceException;
import org.user.repository.UserRepository;
import org.user.util.Constants;
import org.user.util.UtilService;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JavaMailSender javaMailSender, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.javaMailSender = javaMailSender;
    }

    @Transactional
    public ResponseDTO create(final UserDTO userDTO) {
        this.validateEmail(userDTO);
        this.validatePhone(userDTO);
        final org.app.entity.User user = new org.app.entity.User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setCreatedBy(userDTO.getCreatedBy());
        user.setUpdatedBy(userDTO.getUpdatedBy());
        user.setPassword((passwordEncoder.encode(userDTO.getPassword())));
        return new ResponseDTO(Constants.SUCCESS, this.userRepository.save(user), HttpStatus.CREATED.getReasonPhrase());
    }

    private void validateEmail(final UserDTO userDTO) {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (UtilService.emailValidation(userDTO.getEmail())) {
            throw new UserRequestServiceException(Constants.EMAIL_PATTERN, Constants.EMAIL_PATTERN, request.getRequestURI(), getClass().getName(), Constants.POST, userDTO.getCreatedBy(), Constants.USERREQUESTEXCEPTION);
        }
        final Optional<org.app.entity.User> emailFound = this.userRepository.findByName(userDTO.getEmail());
        if (emailFound.isPresent()) {
            throw new BadRequestServiceException(Constants.EMAIL);
        }
    }

    private void validatePhone(final UserDTO userDTO) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (UtilService.phoneNumberValidation(userDTO.getPhone())) {
            throw new UserRequestServiceException(Constants.PHONE_PATTERN, Constants.EMAIL_PATTERN, request.getRequestURI(), getClass().getName(), Constants.POST, userDTO.getCreatedBy(), Constants.USERREQUESTEXCEPTION);
        }
        final List<org.app.entity.User> phoneFound = this.userRepository.findByPhone(userDTO.getPhone());
        if (!phoneFound.isEmpty()) {
            throw new BadRequestServiceException(Constants.PHONE);
        }
    }

    public ResponseDTO retrieve() {
        return new ResponseDTO(Constants.RETRIEVED, this.userRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO updateUser(final UserDTO userDTO, final String id) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        final User existingUser = this.userRepository.findById(id).orElseThrow(() -> new UserRequestServiceException(Constants.User, Constants.EMAIL_PATTERN, request.getRequestURI(), getClass().getName(), Constants.PUT, id, Constants.USERREQUESTEXCEPTION));
        if (userDTO.getName() != null) {
            existingUser.setName(userDTO.getName());
        }
        if (userDTO.getEmail() != null) {
            existingUser.setEmail(userDTO.getEmail());
        }
        if (userDTO.getCreatedBy() != null) {
            existingUser.setCreatedBy(userDTO.getCreatedBy());
        }
        if (userDTO.getUpdatedBy() != null) {
            existingUser.setUpdatedBy(userDTO.getUpdatedBy());
        }
        if (userDTO.getPassword() != null) {
            existingUser.setPassword((passwordEncoder.encode(userDTO.getPassword())));
        }
        return new ResponseDTO(Constants.SUCCESS, this.userRepository.save(existingUser), HttpStatus.OK.getReasonPhrase());
    }


    @Transactional
    public ResponseDTO removeUser(final String id) {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (!this.userRepository.existsById(id)) {
            throw new UserRequestServiceException(Constants.User, Constants.EMAIL_PATTERN, request.getRequestURI(), getClass().getName(), Constants.DELETE, id, Constants.USERREQUESTEXCEPTION);
        }
        this.userRepository.deleteById(id);
        return new ResponseDTO(Constants.DELETED, id, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO login(final AuthDTO authDTO) {
        final Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            final SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("janarthanan0607@gmail.com");
            message.setTo(this.userRepository.findByEmail(authDTO.getUsername()));
            message.setSubject(Constants.SUBJECT);
            message.setText(Constants.BODY);
            javaMailSender.send(message);
            return jwtService.generateToken(authDTO.getUsername());

        } else {
            throw new UsernameNotFoundException(Constants.NOT_FOUND);
        }
    }
}
