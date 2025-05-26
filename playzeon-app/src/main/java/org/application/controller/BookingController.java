package org.application.controller;

import org.application.dto.BookingDTO;
import org.application.dto.ResponseDTO;
import org.application.service.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    //@PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseDTO createBooking(@RequestBody final BookingDTO bookingDTO) {
        return this.bookingService.createBooking(bookingDTO);
    }

    @GetMapping("/retrieve")
    //@PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseDTO retrieveBooking() {
        return this.bookingService.retrieveBooking();
    }

    @PutMapping("/update/{id}")
    //@PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseDTO updateBooking(@RequestBody BookingDTO bookingDTO, @PathVariable final String id) {
        return this.bookingService.updateBooking(bookingDTO, id);
    }

    @DeleteMapping("/remove/{id}")
    //@PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseDTO removeBooking(@PathVariable final String id) {
        return this.bookingService.removeBooking(id);
    }

    @GetMapping("/retrieve-by-id/{id}")
    public ResponseDTO retrieveByID(@PathVariable final String id) {
        return this.bookingService.retrieveById(id);
    }

    @GetMapping("/retrieve-by-date")
    public ResponseDTO retrieveByDate()  {
        return this.bookingService.retrieveByDate();
    }
}
