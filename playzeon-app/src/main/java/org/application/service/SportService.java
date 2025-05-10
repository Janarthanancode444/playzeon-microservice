package org.application.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.transaction.Transactional;
import org.application.dto.SportsDTO;
import org.app.entity.Image;
import org.app.entity.Sport;
import org.application.exception.SportRequestServiceException;
import org.application.repository.CenterRepository;
import org.application.repository.ImageRepository;
import org.application.repository.SportRepository;
import org.user.dto.ResponseDTO;
import org.application.util.AuthenticationService;
import org.user.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class SportService {
    private final SportRepository sportRepository;
    private final CenterRepository centerRepository;
    private final AuthenticationService authenticationService;
    private final ImageRepository imageRepository;

    public SportService(SportRepository sportRepository, ImageRepository imageRepository, CenterRepository centerRepository, AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        this.sportRepository = sportRepository;
        this.centerRepository = centerRepository;
        this.imageRepository = imageRepository;
    }

    @Transactional
    public ResponseDTO createSport(final SportsDTO sportsDTO) {
        final Sport sport = new Sport();
        final Image image = this.imageRepository.findById(sportsDTO.getImageId()).orElseThrow(() -> new SportRequestServiceException(Constants.SPORTSID));
        sport.setName(sportsDTO.getName());
        sport.setSportsType(sportsDTO.getSportsType());
        sport.setImage(image);
        sport.setSportsCategory(sportsDTO.getSportsCategory());
        sport.setCreatedBy(authenticationService.getCurrentUser());
        sport.setUpdatedBy(authenticationService.getCurrentUser());
        return new ResponseDTO(Constants.CREATED, this.sportRepository.save(sport), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveSports() {
        return new ResponseDTO(Constants.RETRIEVED, this.sportRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO updateSport(final SportsDTO sportsDTO, final String id) {
        final Sport sport = this.sportRepository.findById(id).orElseThrow(() -> new SportRequestServiceException(Constants.SPORTSID));
        if (sportsDTO.getName() != null) {
            sport.setName(sportsDTO.getName());
        }
        return new ResponseDTO(Constants.UPDATED, this.sportRepository.save(sport), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO removeSport(final String id) {
        if (!this.sportRepository.existsById(id)) {
            throw new SportRequestServiceException(Constants.SPORTSID);
        }
        this.sportRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO insertCSVIntoDatabase(final String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = reader.readAll();
            records.remove(0);

            for (String[] record : records) {
                //int id = Integer.parseInt(record[0]);
                String name = record[0];
                String sportsType = record[1];
                String sportsCategory = record[2];
                String imageId=record[3];
                final Image image=this.imageRepository.findById(imageId).orElseThrow(()->new SportRequestServiceException(Constants.IMAGEID));

                Sport sport = new Sport();
                sport.setName(name);
                sport.setImage(image);
                sport.setSportsCategory(sportsCategory);
                sport.setSportsType(sportsType);
                sport.setCreatedBy(authenticationService.getCurrentUser());
                sport.setUpdatedBy(authenticationService.getCurrentUser());
                sportRepository.save(sport);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
        return new ResponseDTO(Constants.CREATED, Constants.SUCCESSFULLYCSV, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveSportid(final String id) {
        return new ResponseDTO(Constants.RETRIEVED,this.sportRepository.findById(id),HttpStatus.OK.getReasonPhrase());
    }
}

