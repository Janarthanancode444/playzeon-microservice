package org.user.controller;

import org.user.dto.AuthDTO;
import org.user.dto.ResponseDTO;
import org.user.dto.UserDTO;
import org.user.service.JwtService;
import org.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody final UserDTO userDTO) {
        return this.userService.create(userDTO);
    }

    @GetMapping("/retrieve")
    public ResponseDTO retrieve() {
        return this.userService.retrieve();
    }

    @PutMapping("/update/{id}")
    public ResponseDTO updateUser(@RequestBody final UserDTO userDTO, @PathVariable final String id) {
        return this.userService.updateUser(userDTO, id);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable final String id) {
        return this.userService.removeUser(id);
    }

    @PostMapping("/login")
    public ResponseDTO login(@RequestBody final AuthDTO authDTO) {
        return this.userService.login(authDTO);
    }
}