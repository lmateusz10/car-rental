package io.github.rentalcar.application.service;

import io.github.rentalcar.application.domain.Reservation;
import io.github.rentalcar.application.domain.User;
import io.github.rentalcar.application.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Reservation.
 */
@Service
@Transactional
public class ReservationService {

    private final Logger log = LoggerFactory.getLogger(ReservationService.class);

    private final ReservationRepository reservationRepository;
    private UserService userService;

    public ReservationService(
        ReservationRepository reservationRepository,
        UserService userService
    ) {

        this.reservationRepository = reservationRepository;
        this.userService = userService;
    }

    /**
     * Save a reservation.
     *
     * @param reservation the entity to save
     * @return the persisted entity
     */
    public Reservation save(Reservation reservation) {
        User user = userService.getUserWithAuthorities().get();
        List list = userService.getAuthorities();
        List d = userService.getAuthorities();
        reservation.setUser(user);
        log.debug("Request to save Reservation : {}", reservation);
        return reservationRepository.save(reservation);
    }

    /**
     * Get all the reservations.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        log.debug("Request to get all Reservations");
        return reservationRepository.findAll();
    }


    /**
     * Get one reservation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Reservation> findOne(Long id) {
        log.debug("Request to get Reservation : {}", id);
        return reservationRepository.findById(id);
    }

    /**
     * Delete the reservation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Reservation : {}", id);
        reservationRepository.deleteById(id);
    }
}
