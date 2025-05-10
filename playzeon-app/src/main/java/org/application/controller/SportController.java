package org.application.controller;

import org.application.dto.SportsDTO;
import org.user.dto.ResponseDTO;
import org.user.exception.BadRequestServiceException;
import org.application.service.SportService;
import org.user.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/sports")
public class SportController {
    private final SportService sportService;

    public SportController(SportService sportService) {
        this.sportService = sportService;
    }

    @PostMapping("/upload")
    //@PreAuthorize("hasAuthority('ROLE_CENTER_ADMIN')")
    public ResponseDTO uploadCSV(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new BadRequestServiceException(Constants.ISEMPTY);
        }
        try {
            File tempFile = File.createTempFile("sports", ".csv");
            file.transferTo(tempFile);
            return this.sportService.insertCSVIntoDatabase(tempFile.getAbsolutePath());
        } catch (IOException e) {
            return new ResponseDTO(Constants.ERRORCSV, Constants.NOT_FOUND, HttpStatus.OK.getReasonPhrase());

        }
    }

    @PostMapping("/create")
    //@PreAuthorize("hasAuthority('ROLE_CENTER_ADMIN')")
    public ResponseDTO createSport(@RequestBody final SportsDTO sportsDTO) {
        return this.sportService.createSport(sportsDTO);
    }

    @GetMapping("/retrieve")
    //@PreAuthorize("hasAuthority('ROLE_CENTER_ADMIN')")
    public ResponseDTO retrieveSports() {
        return this.sportService.retrieveSports();
    }
    @GetMapping("/retrieve/{id}")
    //@PreAuthorize("hasAuthority('ROLE_CENTER_ADMIN')")
    public ResponseDTO retrieveId(@PathVariable final String id) {
        return this.sportService.retrieveSportid(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_CENTER_ADMIN')")
    public ResponseDTO updateSport(@RequestBody final SportsDTO sportsDTO, @PathVariable final String id) {
        return this.sportService.updateSport(sportsDTO, id);
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('ROLE_CENTER_ADMIN')")
    public ResponseDTO removeSport(@PathVariable final String id) {
        return this.sportService.removeSport(id);
    }

}
