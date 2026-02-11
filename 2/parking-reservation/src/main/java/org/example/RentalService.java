package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RentalService {

    private final CarStock carStock;
    private final List<Reservation> reservations = new ArrayList<>();

    public RentalService(CarStock carStock) {
        this.carStock = carStock;
    }

    public Optional<Reservation> reserve(CarType type, LocalDateTime start, int days) {
        DateRange requested = new DateRange(start, days);

        for (Car car : carStock.getCarsByType(type)) {
            if (isAvailable(car, requested)) {
                Reservation reservation = new Reservation(car, requested);
                reservations.add(reservation);
                return Optional.of(reservation);
            }
        }
        return Optional.empty();
    }

    private boolean isAvailable(Car car, DateRange requested) {
        return reservations.stream()
                .filter(r -> r.getCar().getId().equals(car.getId()))
                .noneMatch(r -> r.getPeriod().isDatesOverlaps(requested));
    }
}
