package org.user.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.user.dto.ResponseDTO;
import org.user.dto.RolesDTO;
import org.user.service.RoleService;

@RestController
@RequestMapping("/api/v1/role")
public class RolesController {
    private final RoleService service;

    public RolesController(RoleService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseDTO createRole(@RequestBody final RolesDTO rolesDTO) {
        return this.service.createRole(rolesDTO);
    }

    @GetMapping("/retrieve")
    public ResponseDTO retrieve() {
        return this.service.retrieve();
    }

    @PutMapping("/update/{id}")
    public ResponseDTO updateUser(@RequestBody final RolesDTO rolesDTO, @PathVariable final String id) {
        return this.service.updateUser(rolesDTO, id);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable final String id) {
        return this.service.removeUser(id);
    }

}
