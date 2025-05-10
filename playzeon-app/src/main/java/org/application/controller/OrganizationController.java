package org.application.controller;

import org.application.dto.OrganizationDTO;
import org.user.dto.ResponseDTO;
import org.application.service.OrganizationService;
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
@RequestMapping("/api/v1/organization")
public class OrganizationController {
    private final OrganizationService organizationService;

    public OrganizationController(final OrganizationService organizationService) {
        this.organizationService = organizationService;
    }


    @PostMapping("/create")
    //@PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    public ResponseDTO createOrganization(@RequestBody final OrganizationDTO organizationDTO) {
        return this.organizationService.createOrganization(organizationDTO);
    }


    @GetMapping("/retrieve")
    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    public ResponseDTO retrieve() {
        return this.organizationService.retrieveOrganization();
    }


    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    public ResponseDTO updateOrganization(@RequestBody final OrganizationDTO organizationDTO, @PathVariable final String id) {
        return this.organizationService.updateOrganization(organizationDTO, id);
    }


    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    public ResponseDTO removeOrganization(@PathVariable final String id) {
        return this.organizationService.removeOrganization(id);
    }
}
