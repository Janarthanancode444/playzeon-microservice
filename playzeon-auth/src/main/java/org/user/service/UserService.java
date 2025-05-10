package org.user.service;


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
        final User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRoles(userDTO.getRoles());
        user.setPhone(userDTO.getPhone());
        user.setCreatedBy(userDTO.getCreatedBy());
        user.setUpdatedBy(userDTO.getUpdatedBy());
        user.setPassword((passwordEncoder.encode(userDTO.getPassword())));
        return new ResponseDTO(Constants.SUCCESS, this.userRepository.save(user), HttpStatus.CREATED.getReasonPhrase());
    }

    private void validateEmail(final UserDTO userDTO) {
        if (UtilService.emailValidation(userDTO.getEmail())) {
            throw new UserRequestServiceException(Constants.EMAIL_PATTERN, Constants.EMAIL_PATTERN, Constants.ENDCREATED, userDTO.getCreatedBy());
        }
        final Optional<User> emailFound = this.userRepository.findByName(userDTO.getEmail());
        if (emailFound.isPresent()) {
            throw new BadRequestServiceException(Constants.EMAIL);
        }
    }

    private void validatePhone(final UserDTO userDTO) {
        if (UtilService.phoneNumberValidation(userDTO.getPhone())) {
            throw new BadRequestServiceException(Constants.PHONE_PATTERN);
        }
        final List<User> phoneFound = this.userRepository.findByPhone(userDTO.getPhone());
        if (!phoneFound.isEmpty()) {
            throw new BadRequestServiceException(Constants.PHONE);
        }
    }

    public ResponseDTO retrieve() {
        return new ResponseDTO(Constants.RETRIEVED, this.userRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO updateUser(final UserDTO userDTO, final String id) {
        {
            final User existingUser = this.userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(Constants.User));
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
            if (userDTO.getRoles() != null) {
                existingUser.setRoles(userDTO.getRoles());
            }
            return new ResponseDTO(Constants.SUCCESS, this.userRepository.save(existingUser), HttpStatus.OK.getReasonPhrase());
        }

    }

    @Transactional
    public ResponseDTO removeUser(final String id) {
        if (!this.userRepository.existsById(id)) {
            throw new UsernameNotFoundException(Constants.NOT_FOUND);
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
