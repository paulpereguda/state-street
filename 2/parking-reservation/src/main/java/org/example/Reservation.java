package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class Reservation {

    private Car car;
    private DateRange period;

}
