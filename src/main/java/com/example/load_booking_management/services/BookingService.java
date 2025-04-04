package com.example.load_booking_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private LoadService loadService;

    public Booking createBooking(Booking booking) {
        Optional<Load> load = loadService.getLoadById(booking.getLoadId());
        if (load.isPresent() && load.get().getStatus() != Load.LoadStatus.CANCELLED) {
            booking.setStatus(Booking.BookingStatus.PENDING);
            Booking savedBooking = bookingRepository.save(booking);
            load.get().setStatus(Load.LoadStatus.BOOKED);
            loadService.updateLoad(booking.getLoadId(), load.get());
            return savedBooking;
        } else {
            throw new RuntimeException("Cannot create booking for a cancelled or non-existent load");
        }
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(String bookingId) {
        return bookingRepository.findById(bookingId);
    }

    public Booking updateBooking(String bookingId, Booking bookingDetails) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));
        booking.setTransporterId(bookingDetails.getTransporterId());
        booking.setProposedRate(bookingDetails.getProposedRate());
        booking.setComment(bookingDetails.getComment());
        booking.setStatus(bookingDetails.getStatus());
        return bookingRepository.save(booking);
    }

    public void deleteBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));
        Load load = loadService.getLoadById(booking.getLoadId())
                .orElseThrow(() -> new RuntimeException("Load not found with id: " + booking.getLoadId()));
        load.setStatus(Load.LoadStatus.CANCELLED);
        loadService.updateLoad(booking.getLoadId(), load);
        bookingRepository.deleteById(bookingId);
    }
}