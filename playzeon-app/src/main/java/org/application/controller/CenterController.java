package org.application.controller;

import org.application.dto.CenterDTO;
import org.application.dto.ResponseDTO;
import org.application.service.CenterService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/center")
public class CenterController {
    private final CenterService centerService;

    public CenterController(final CenterService centerService) {
        this.centerService = centerService;
    }

    @PostMapping("/create")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO createCenter(@RequestBody final CenterDTO centerDTO) {
        return this.centerService.createCenter(centerDTO);
    }

    @GetMapping("/retrieve")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO retrieveCenter() {
        return this.centerService.retrieveCenter();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO updateCenter(@RequestBody final CenterDTO centerDTO, @PathVariable final String id) {
        return this.centerService.updateCenter(centerDTO, id);
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO removeCenter(@PathVariable final String id) {
        return this.centerService.removeCenter(id);
    }
}
