import static org.example.CarType.SEDAN;
import static org.example.CarType.SUV;
import static org.example.CarType.VAN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.example.CarStock;
import org.example.RentalService;
import org.example.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CarRentalServiceTest {

    private RentalService service;

    @BeforeEach
    void setup() {
        CarStock stock = new CarStock(Map.of(
                SEDAN, 1,
                SUV, 1,
                VAN, 1
        ));
        service = new RentalService(stock);
    }

    @Test
    void shouldReserveCarWhenAvailable() {
        Optional<Reservation> result = service.reserve(SEDAN, LocalDateTime.now(), 3);
        assertTrue(result.isPresent());
        assertEquals(SEDAN, result.get().getCar().getCarType());
    }

    @Test
    void shouldNotReserveWhenNoCarsAvailable() {
        LocalDateTime now = LocalDateTime.now();

        assertTrue(service.reserve(SEDAN, now, 5).isPresent());
        assertTrue(service.reserve(SEDAN, now.plusDays(1), 3).isEmpty());
    }

    @Test
    void shouldAllowReservationAfterPeriodEnds() {
        LocalDateTime now = LocalDateTime.now();

        assertTrue(service.reserve(SEDAN, now, 2).isPresent());
        assertTrue(service.reserve(SEDAN, now.plusDays(2), 2).isPresent());
    }

    @Test
    void shouldHandleDifferentCarTypesIndependently() {
        LocalDateTime now = LocalDateTime.now();

        assertTrue(service.reserve(SEDAN, now, 3).isPresent());
        assertTrue(service.reserve(SUV, now, 3).isPresent());
        assertTrue(service.reserve(VAN, now, 3).isPresent());
    }
}
