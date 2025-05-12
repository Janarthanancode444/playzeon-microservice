package org.application.service;

import jakarta.transaction.Transactional;
import org.app.entity.Center;
import org.app.entity.Sport;
import org.app.entity.SportCenterMap;
import org.application.dto.CenterQr;
import org.application.dto.CenterQrDTO;
import org.application.dto.SportsCenterMapDTO;
import org.application.exception.CenterRequestServiceException;
import org.application.exception.SportCenterMapRequestServiceException;
import org.application.exception.SportRequestServiceException;
import org.application.repository.CenterRepository;
import org.application.repository.SportCenterMapRepository;
import org.application.repository.SportRepository;
import org.application.util.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.user.dto.ResponseDTO;
import org.user.exception.BadRequestServiceException;
import org.user.util.Constants;

import java.util.List;
import java.util.Optional;

import static org.application.util.QrCodeUtil.generateQRCodeImage;

@Service
public class SportCenterMapService {
    private final SportCenterMapRepository sportCenterMapRepository;
    private final CenterRepository centerRepository;
    private final SportRepository sportRepository;
    private final AuthenticationService authenticationService;

    public SportCenterMapService(final SportCenterMapRepository sportCenterMapRepository, final AuthenticationService authenticationService, final CenterRepository centerRepository, final SportRepository sportRepository) {
        this.sportCenterMapRepository = sportCenterMapRepository;
        this.centerRepository = centerRepository;
        this.sportRepository = sportRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public ResponseDTO createSportsCenter(final SportsCenterMapDTO sportsCenterMapDTO) {
        final Center center = this.centerRepository.findById(sportsCenterMapDTO.getCenterId()).orElseThrow(() -> new CenterRequestServiceException(Constants.IMAGEID, Constants.POST, Constants.CREATE, authenticationService.getCurrentUser(), Constants.CREATE, Constants.CENTER));
        final Sport sport = this.sportRepository.findById(sportsCenterMapDTO.getSportsId()).orElseThrow(() -> new SportRequestServiceException(Constants.SPORTSID, Constants.SPORTSID, Constants.CREATE, authenticationService.getCurrentUser(), Constants.CREATE, Constants.SPORT));
        final SportCenterMap sportCenterMap = new SportCenterMap();
        sportCenterMap.setCenter(center);
        sportCenterMap.setSports(sport);
        sportCenterMap.setCreatedBy(authenticationService.getCurrentUser());
        sportCenterMap.setUpdatedBy(authenticationService.getCurrentUser());
        return new ResponseDTO(Constants.CREATED, this.sportCenterMapRepository.save(sportCenterMap), HttpStatus.OK.getReasonPhrase());
    }


    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.sportCenterMapRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO updateSports(final SportsCenterMapDTO sportsCenterMapDTO, final String id) {
        final SportCenterMap sportCenterMap = this.sportCenterMapRepository.findById(id).orElseThrow(() -> new SportCenterMapRequestServiceException(Constants.SPORTSCENTER, Constants.SPORTSCENTER, Constants.PUT, authenticationService.getCurrentUser(), Constants.UPDATE, Constants.SPORTCENTERMAP));
        if (sportsCenterMapDTO.getCenterId() != null) {
            final Center center = this.centerRepository.findById(sportsCenterMapDTO.getSportsId()).orElseThrow(() -> new BadRequestServiceException(Constants.CENTERID));
            sportCenterMap.setCenter(center);
        }
        if (sportsCenterMapDTO.getSportsId() != null) {
            final Sport sport = this.sportRepository.findById(sportsCenterMapDTO.getSportsId()).orElseThrow(() -> new BadRequestServiceException(Constants.SPORTSID));
            sportCenterMap.setSports(sport);
        }
        return new ResponseDTO(Constants.UPDATED, this.sportCenterMapRepository.save(sportCenterMap), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO removeSports(final String id) {
        if (!this.sportCenterMapRepository.existsById(id)) {
            throw new SportCenterMapRequestServiceException(Constants.SPORTSCENTER, Constants.SPORTSCENTER, Constants.DELETE, authenticationService.getCurrentUser(), Constants.REOMVE, Constants.SPORTCENTERMAP);
        }
        this.sportCenterMapRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveId(final String id) {
        return new ResponseDTO(Constants.RETRIEVED, this.sportCenterMapRepository.findById(id), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO getAllSportsByCenterId(String centerId) {
        final Optional<SportCenterMap> sportCenterMapOptional = this.sportCenterMapRepository.findById(centerId);
        final CenterQrDTO dto = new CenterQrDTO();
        if (sportCenterMapOptional.isPresent()) {
            final SportCenterMap sportCenterMap = sportCenterMapOptional.get();
            dto.setCenterId(sportCenterMap.getCenter().getId());
            dto.setCenterName(sportCenterMap.getCenter().getName());
            dto.setSportId(sportCenterMap.getSports().getId());
            dto.setSportName(sportCenterMap.getSports().getName());
            dto.setCreatedAt(sportCenterMap.getSports().getCreatedAt());
            dto.setCreatedBy(sportCenterMap.getSports().getCreatedBy());
            dto.setUpdatedAt(sportCenterMap.getSports().getUpdatedAt());
            dto.setUpdatedBy(sportCenterMap.getSports().getUpdatedBy());
            dto.setSportsType(sportCenterMap.getSports().getSportsType());
            dto.setSportsCategory(sportCenterMap.getSports().getSportsCategory());
            return new ResponseDTO(Constants.RETRIEVED, dto, HttpStatus.OK.getReasonPhrase());
        } else {

            return new ResponseDTO(Constants.NOT_FOUND, null, HttpStatus.NOT_FOUND.getReasonPhrase());
        }
    }

    public CenterQr getCenterQRCodeWithSports(String centerId) throws Exception {
        final List<Sport> sports = sportCenterMapRepository.findAllByCenterId(centerId).stream().map(SportCenterMap::getSports).toList();
        final String qrText = "https://81e3-124-123-80-156.ngrok-free.app/api/v1/sportscentermap/retrieveId/e8ebcda6-30b2-4877-8aeb-1833ccbb86fe";
        final String filePath = "F:\\Internship\\Microservice-playzeon\\output.png";
        generateQRCodeImage(qrText, 300, 300, filePath);
        return new CenterQr(sports, qrText);
    }
}
