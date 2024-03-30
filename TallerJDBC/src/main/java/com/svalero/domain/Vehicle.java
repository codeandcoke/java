package com.svalero.domain;

import lombok.*;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
public class Vehicle {

    private int id;
    @NonNull
    private String licensePlate;
    @NonNull
    private String brand;
    @NonNull
    private String model;
    @NonNull
    private int kilometers;
    @NonNull
    private LocalDate registrationDate;
}
