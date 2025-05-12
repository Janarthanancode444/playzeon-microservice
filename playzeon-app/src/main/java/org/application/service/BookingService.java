package org.application.service;

import jakarta.transaction.Transactional;
import org.app.entity.Booking;
import org.app.entity.Center;
import org.application.dto.BookingDTO;
import org.application.exception.BookingRequestExceptionService;
import org.application.exception.CenterRequestServiceException;
import org.application.repository.BookingRepository;
import org.application.repository.CenterRepository;
import org.application.util.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.user.dto.ResponseDTO;
import org.user.util.Constants;

@Service
public class BookingService {
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
        final Center center = this.centerRepository.findById(bookingDTO.getCenterId()).orElseThrow(() -> new CenterRequestServiceException(Constants.CENTERID, Constants.CENTERID, Constants.POST, authenticationService.getCurrentUser(), Constants.CREATE, Constants.CENTER));
        final Booking booking = new Booking();
        booking.setName(bookingDTO.getName());
        booking.setPhone(bookingDTO.getPhone());
        booking.setAddress(bookingDTO.getAddress());
        booking.setEmail(bookingDTO.getEmail());
        booking.setFromTime(bookingDTO.getFromTime());
        booking.setToTime(bookingDTO.getToTime());
        booking.setCenter(center);
        booking.setCreatedBy(authenticationService.getCurrentUser());
        booking.setUpdatedBy(authenticationService.getCurrentUser());
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
        if (bookingDTO.getFromTime() != null) {
            booking.setFromTime(bookingDTO.getFromTime());
        }
        if (bookingDTO.getToTime() != null) {
            booking.setToTime(bookingDTO.getToTime());
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
