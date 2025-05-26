package org.application.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.app.entity.Organization;
import org.application.dto.OrganizationDTO;
import org.application.dto.ResponseDTO;
import org.application.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
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
    @Autowired
    private KafkaTemplate<String, ResponseDTO> kafkaTemplate;
    private static final String TOPIC = "new-topic";
    private final OrganizationService organizationService;

    public OrganizationController(final OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Organization created successfully"), @ApiResponse(responseCode = "400", description = "Invalid request body")})
    @PostMapping("/create")
    @Tag(name = "Organization", description = "APIs for managing organizations")
    public ResponseDTO createOrganization(@RequestBody final OrganizationDTO organizationDTO) {
        return this.organizationService.createOrganization(organizationDTO);
    }


    @GetMapping("/retrieve")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Organization list retrieved successfully"), @ApiResponse(responseCode = "403", description = "Access denied"), @ApiResponse(responseCode = "500", description = "Server error")})
    //@PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    public ResponseDTO retrieve() {
        kafkaTemplate.send(TOPIC, this.organizationService.retrieveOrganization());
        return this.organizationService.retrieveOrganization();
    }

    @GetMapping("/retrieveuser")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Organization list retrieved successfully"), @ApiResponse(responseCode = "403", description = "Access denied"), @ApiResponse(responseCode = "500", description = "Server error")})
    public ResponseDTO retrieveById() {
        return this.organizationService.getUserById();
    }


    @PutMapping("/update/{id}")
    @Tag(name = "Organization", description = "APIs for managing organizations")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Organization updated successfully"), @ApiResponse(responseCode = "400", description = "Invalid input provided"), @ApiResponse(responseCode = "404", description = "Organization not found"), @ApiResponse(responseCode = "500", description = "Internal server error")})
    //@PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    public ResponseDTO updateOrganization(@RequestBody final OrganizationDTO organizationDTO, @PathVariable final String id) {
        return this.organizationService.updateOrganization(organizationDTO, id);
    }


    @DeleteMapping("/remove/{id}")
    @Tag(name = "Organization", description = "APIs for managing organizations")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Organization removed successfully"), @ApiResponse(responseCode = "404", description = "Organization not found"), @ApiResponse(responseCode = "403", description = "Access denied"), @ApiResponse(responseCode = "500", description = "Internal server error")})
    //@PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    public ResponseDTO removeOrganization(@PathVariable final String id) {
        return this.organizationService.removeOrganization(id);
    }
}
