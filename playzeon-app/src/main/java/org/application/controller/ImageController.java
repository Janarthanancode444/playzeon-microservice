package org.application.controller;

import org.application.dto.ResponseDTO;
import org.application.service.ImageService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
//import org.user.dto.ResponseDTO;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    //    @PreAuthorize("hasAuthority('ROLE_CENTER_ADMIN')")
    @PostMapping("/create")
    public ResponseDTO createImage(@RequestParam("file") final MultipartFile file, @RequestParam("type") final int type) throws IOException {
        return this.imageService.createImage(file, type);
    }

    @GetMapping("/retrieveAll")
    //@PreAuthorize("hasAuthority('ROLE_CENTER_ADMIN')")
    public ResponseDTO retrieveAll() {
        return this.imageService.retrieveAll();
    }

    @GetMapping("/retrieveId/{id}")
    //@PreAuthorize("hasAuthority('ROLE_CENTER_ADMIN')")
    public ResponseDTO retrieveId(@PathVariable final String id) {
        return this.imageService.retrieveId(id);
    }

    @PutMapping("/update/{id}")
    //@PreAuthorize("hasAuthority('ROLE_CENTER_ADMIN')")
    public ResponseDTO updateImage(@PathVariable final String id, @RequestParam("file") final MultipartFile file, @RequestParam("type") final int type) throws IOException {
        return this.imageService.updateImage(id, file, type);
    }

    @DeleteMapping("/remove/{id}")
    //@PreAuthorize("hasAuthority('ROLE_CENTER_ADMIN')")
    public ResponseDTO removeImage(@PathVariable final String id) {
        return this.imageService.removeImage(id);
    }

}
