package org.application.service;

import jakarta.transaction.Transactional;
import org.app.entity.Image;
import org.app.entity.Organization;
import org.app.entity.SportCenterMap;
import org.application.dto.CenterQrDTO;
import org.application.dto.OrganizationCenterDTO;
import org.application.dto.OrganizationDTO;
import org.application.dto.ResponseDTO;
import org.application.exception.OrganizationRequestServiceException;
import org.application.repository.ImageRepository;
import org.application.repository.OrganizationRepository;
import org.application.util.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.user.util.Constants;

import java.util.Optional;

@Service
public class OrganizationService {
    @Autowired
    private WebClient userServiceWebClient;
    private final OrganizationRepository organizationRepository;
    private final AuthenticationService authenticationService;
    private final ImageRepository imageRepository;

    public OrganizationService(OrganizationRepository organizationRepository, ImageRepository imageRepository, AuthenticationService authenticationService) {
        this.organizationRepository = organizationRepository;
        this.authenticationService = authenticationService;
        this.imageRepository = imageRepository;
    }

    @Transactional
    public ResponseDTO createOrganization(final OrganizationDTO organizationDTO) {
        final Organization organization = new Organization();
        final Image image = this.imageRepository.findById(organizationDTO.getImageId()).orElseThrow(() -> new OrganizationRequestServiceException(Constants.ORGANIZATIONID, Constants.IDDOESNOTEXIST, Constants.POST, authenticationService.getCurrentUser(), Constants.ORGANIZATION, Constants.CREATE));
        organization.setName(organizationDTO.getName());
        organization.setPhone(organizationDTO.getPhone());
        organization.setAddress(organizationDTO.getAddress());
        organization.setImage(image);
        organization.setEmail(organizationDTO.getEmail());
        organization.setCreatedBy(authenticationService.getCurrentUser());
        organization.setUpdatedBy(authenticationService.getCurrentUser());
        return new ResponseDTO(Constants.CREATED, this.organizationRepository.save(organization), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveOrganization() {
        return new ResponseDTO(Constants.RETRIEVED, this.organizationRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }


    @Transactional
    public ResponseDTO updateOrganization(final OrganizationDTO organizationDTO, final String id) {
        final Organization existinOrganization = this.organizationRepository.findById(id).orElseThrow(() -> new OrganizationRequestServiceException(Constants.ORGANIZATIONID, Constants.ORGANIZATIONID, Constants.UPDATE, authenticationService.getCurrentUser(), Constants.ORGANIZATION, Constants.UPDATE));
        if (organizationDTO.getName() != null) {
            existinOrganization.setName(organizationDTO.getName());
        }
        if (organizationDTO.getEmail() != null) {
            existinOrganization.setEmail(organizationDTO.getEmail());
        }
        if (organizationDTO.getPhone() != null) {
            existinOrganization.setPhone(organizationDTO.getPhone());
        }
        if (organizationDTO.getAddress() != null) {
            existinOrganization.setAddress(organizationDTO.getAddress());
        }
        return new ResponseDTO(Constants.UPDATED, this.organizationRepository.save(existinOrganization), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO removeOrganization(final String id) {
        if (!this.organizationRepository.existsById(id)) {
            throw new OrganizationRequestServiceException(Constants.ORGANIZATIONID, Constants.DELETE, Constants.REOMVE, authenticationService.getCurrentUser(), Constants.ORGANIZATION, Constants.REOMVE);
        }
        this.organizationRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO getUserById() {
        ResponseDTO responseDTO = userServiceWebClient.get().uri("/user/retrieve").retrieve().bodyToMono(ResponseDTO.class).block();
        return responseDTO;
    }

}
