package org.application.service;

import jakarta.transaction.Transactional;
import org.app.entity.Center;
import org.app.entity.Image;
import org.app.entity.Organization;
import org.application.dto.CenterDTO;
import org.application.dto.ResponseDTO;
import org.application.exception.CenterRequestServiceException;
import org.application.exception.OrganizationRequestServiceException;
import org.application.repository.CenterRepository;
import org.application.repository.ImageRepository;
import org.application.repository.OrganizationRepository;
import org.application.util.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.user.util.Constants;


@Service
public class CenterService {
    private final CenterRepository centerRepository;
    private final AuthenticationService authenticationService;
    private final OrganizationRepository organizationRepository;
    private final ImageRepository imageRepository;

    public CenterService(CenterRepository centerRepository, ImageRepository imageRepository, AuthenticationService authenticationService, OrganizationRepository organizationRepository) {
        this.centerRepository = centerRepository;
        this.authenticationService = authenticationService;
        this.organizationRepository = organizationRepository;
        this.imageRepository = imageRepository;
    }

    @Transactional
    public ResponseDTO createCenter(final CenterDTO centerDTO) {
        final Center center = new Center();
        final Image image = this.imageRepository.findById(centerDTO.getImageId()).orElseThrow(() -> new CenterRequestServiceException(Constants.IMAGEID, Constants.IMAGEID, Constants.POST, authenticationService.getCurrentUser(), Constants.CREATE, Constants.CENTER));
        final Organization organization = this.organizationRepository.findById(centerDTO.getOrganizationId()).orElseThrow(() -> new OrganizationRequestServiceException(Constants.ORGANIZATIONID, Constants.POST, Constants.CREATE, authenticationService.getCurrentUser(), Constants.ORGANIZATION, Constants.CREATED_USER));
        center.setName(centerDTO.getName());
        center.setPhone(centerDTO.getPhone());
        center.setEmail(centerDTO.getEmail());
        center.setImage(image);
        center.setOrganization(organization);
        center.setAddress(centerDTO.getAddress());
        center.setStartTime(centerDTO.getStartTime());
        center.setEndTime(centerDTO.getEndTime());
        center.setTimeZone(centerDTO.getTimeZone());
        center.setCreatedBy(authenticationService.getCurrentUser());
        center.setUpdatedBy(authenticationService.getCurrentUser());
        return new ResponseDTO(Constants.CREATED, this.centerRepository.save(center), HttpStatus.OK.getReasonPhrase());

    }

    public ResponseDTO retrieveCenter() {
        return new ResponseDTO(Constants.RETRIEVED, this.centerRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO updateCenter(final CenterDTO centerDTO, final String id) {
        final Center center = this.centerRepository.findById(id).orElseThrow(() -> new CenterRequestServiceException(Constants.CENTERID, Constants.CENTERID, Constants.PUT, authenticationService.getCurrentUser(), Constants.UPDATE, Constants.CENTER));
        if (centerDTO.getName() != null) {
            center.setName(centerDTO.getName());
        }
        if (centerDTO.getPhone() != null) {
            center.setPhone(centerDTO.getPhone());
        }
        if (centerDTO.getEmail() != null) {
            center.setEmail(centerDTO.getEmail());
        }
        if (centerDTO.getAddress() != null) {
            center.setAddress(centerDTO.getAddress());
        }
        if (centerDTO.getStartTime() != null) {
            center.setStartTime(centerDTO.getStartTime());
        }
        if (centerDTO.getEndTime() != null) {
            center.setEndTime(centerDTO.getEndTime());
        }
        return new ResponseDTO(Constants.UPDATED, this.centerRepository.save(center), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO removeCenter(final String id) {
        if (!this.centerRepository.existsById(id)) {
            throw new CenterRequestServiceException(Constants.CENTERID, Constants.CENTERID, Constants.DELETE, authenticationService.getCurrentUser(), Constants.REOMVE, Constants.CENTER);
        }
        this.centerRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }
}
