package org.application.controller;

import org.application.dto.CenterQr;
import org.application.dto.SportsCenterMapDTO;
import org.application.service.SportCenterMapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.user.dto.ResponseDTO;

@RestController
@RequestMapping("/api/v1/sportscentermap")
public class SportCenterMapController {
    private final SportCenterMapService sportCenterMapService;

    public SportCenterMapController(final SportCenterMapService sportCenterMapService) {
        this.sportCenterMapService = sportCenterMapService;
    }

    @PostMapping("/create")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO createsportsCenter(@RequestBody final SportsCenterMapDTO sportsCenterMapDTO) {
        return this.sportCenterMapService.createSportsCente(sportsCenterMapDTO);
    }

    @GetMapping("/retrieveAll")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO retrieveAll() {
        return this.sportCenterMapService.retrieveAll();
    }

    @GetMapping("/retrieveId/{id}")
    // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO retrieveId(@PathVariable final String id) {
        return this.sportCenterMapService.getAllSportsByCenterId(id);
    }

    @PutMapping("/update/{id}")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO updateSports(@RequestBody final SportsCenterMapDTO sportsCenterMapDTO, @PathVariable final String id) {
        return this.sportCenterMapService.updateSports(sportsCenterMapDTO, id);
    }

    @DeleteMapping("/remove/{id}")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO removeSports(@PathVariable final String id) {
        return this.sportCenterMapService.removeSports(id);
    }

    @GetMapping("/qr/{centerId}")
    public ResponseEntity<CenterQr> getCenterQRCodeWithSports(@PathVariable String centerId) throws Exception {
        CenterQr response = sportCenterMapService.getCenterQRCodeWithSports(centerId);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/sports/{centerId}")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO getSportsByCenterId(@PathVariable String centerId) {
        return this.sportCenterMapService.getAllSportsByCenterId(centerId);
    }
}
