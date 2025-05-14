package org.application.service;

import jakarta.transaction.Transactional;
import org.app.entity.Booking;
import org.app.entity.Center;
import org.app.entity.User;
import org.application.dto.BookingDTO;
import org.application.exception.BookingRequestExceptionService;
import org.application.exception.CenterRequestServiceException;
import org.application.repository.BookingRepository;
import org.application.repository.CenterRepository;
import org.application.util.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.user.dto.ResponseDTO;
import org.user.repository.UserRepository;
import org.user.util.Constants;

import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CenterRepository centerRepository;
    private final AuthenticationService authenticationService;

    public BookingService(BookingRepository bookingRepository, CenterRepository centerRepository, AuthenticationService authenticationService) {
        this.bookingRepository = bookingRepository;
        this.centerRepository = centerRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public ResponseDTO createBooking(final BookingDTO bookingDTO) {
        final Booking booking = new Booking();
        final Center center = this.centerRepository.findById(bookingDTO.getCenterId()).orElseThrow(() -> new CenterRequestServiceException(Constants.CENTERID, Constants.CENTERID, Constants.POST, authenticationService.getCurrentUser(), Constants.CREATE, Constants.CENTER));
        final User user = this.userRepository.findById(bookingDTO.getUserId()).orElseThrow(() -> new BookingRequestExceptionService(Constants.BOOKINGID, Constants.BOOKINGID, Constants.PUT, authenticationService.getCurrentUser(), Constants.UPDATE, Constants.BOOKING));
        final Optional<Booking> optionalBooking = this.bookingRepository.findByUserId(bookingDTO.getUserId());
        if (optionalBooking.isPresent()) {
            booking.setMultiBooking(true);
        } else {
            booking.setMultiBooking(false);
        }
        booking.setName(bookingDTO.getName());
        booking.setPhone(bookingDTO.getPhone());
        booking.setAddress(bookingDTO.getAddress());
        booking.setEmail(bookingDTO.getEmail());
        final Optional<Booking> optionalBooking1 = this.bookingRepository.findByStartTime(bookingDTO.getStartTime());
        if (optionalBooking1.isPresent()) {
            throw new BookingRequestExceptionService(Constants.BOOKINGID, Constants.BOOKINGID, Constants.PUT, authenticationService.getCurrentUser(), Constants.UPDATE, Constants.BOOKING);
        } else {
            booking.setStartTime(bookingDTO.getStartTime());
        }
        booking.setEndTime(bookingDTO.getEndTime());
        booking.setCenter(center);
        booking.setUser(user);
        booking.setCreatedBy(this.authenticationService.getCurrentUser());
        booking.setUpdatedBy(this.authenticationService.getCurrentUser());
        return new ResponseDTO(Constants.CREATED, this.bookingRepository.save(booking), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveBooking() {
        return new ResponseDTO(Constants.RETRIEVED, this.bookingRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO updateBooking(final BookingDTO bookingDTO, final String id) {
        final Booking booking = this.bookingRepository.findById(id).orElseThrow(() -> new BookingRequestExceptionService(Constants.BOOKINGID, Constants.BOOKINGID, Constants.PUT, authenticationService.getCurrentUser(), Constants.UPDATE, Constants.BOOKING));
        if (bookingDTO.getName() != null) {
            booking.setName(bookingDTO.getName());
        }
        if (bookingDTO.getPhone() != null) {
            booking.setPhone(bookingDTO.getPhone());
        }
        if (bookingDTO.getAddress() != null) {
            booking.setAddress(bookingDTO.getAddress());
        }
        if (bookingDTO.getStartTime() != null) {
            booking.setStartTime(bookingDTO.getStartTime());
        }
        if (bookingDTO.getEndTime() != null) {
            booking.setEndTime(bookingDTO.getEndTime());
        }
        return new ResponseDTO(Constants.UPDATED, this.bookingRepository.save(booking), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO removeBooking(final String id) {
        if (!this.bookingRepository.existsById(id)) {
            throw new BookingRequestExceptionService(Constants.BOOKINGID, Constants.BOOKINGID, Constants.DELETE, authenticationService.getCurrentUser(), Constants.REOMVE, Constants.BOOKING);
        }
        this.bookingRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }
}
