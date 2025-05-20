package org.application.service;

import jakarta.transaction.Transactional;
import org.app.entity.Image;
import org.application.dto.ResponseDTO;
import org.application.exception.ImageRequestServiceException;
import org.application.repository.ImageRepository;
import org.application.util.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.user.util.Constants;

import java.io.IOException;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final AuthenticationService authenticationService;

    public ImageService(ImageRepository imageRepository, AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        this.imageRepository = imageRepository;
    }

    @Transactional
    public ResponseDTO createImage(final MultipartFile file, final int type) throws IOException {
        final Image image = new Image();
        image.setImage(file.getBytes());
        image.setType(type);
        image.setCreatedBy(authenticationService.getCurrentUser());
        image.setUpdatedBy(authenticationService.getCurrentUser());
        return new ResponseDTO(Constants.CREATED, this.imageRepository.save(image), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.imageRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveId(final String id) {
        return new ResponseDTO(Constants.RETRIEVED, this.imageRepository.findById(id), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO updateImage(final String id, final MultipartFile file, final int type) throws IOException {
        final Image image = this.imageRepository.findById(id).orElseThrow(() -> new ImageRequestServiceException(Constants.IMAGEID, Constants.IMAGEID, Constants.PUT, authenticationService.getCurrentUser(), Constants.UPDATE, Constants.IMAGE));
        if (file != null) {
            image.setImage(file.getBytes());
        }
        if (type != 0) {
            image.setType(type);
        }
        return new ResponseDTO(Constants.UPDATED, this.imageRepository.save(image), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO removeImage(final String id) {
        if (!this.imageRepository.existsById(id)) {
            throw new ImageRequestServiceException(Constants.IMAGEID, Constants.IMAGEID, Constants.DELETE, authenticationService.getCurrentUser(), Constants.REOMVE, Constants.IMAGE);
        }
        this.imageRepository.deleteById(id);
        return new ResponseDTO(Constants.DELETED, id, HttpStatus.OK.getReasonPhrase());

    }
}
