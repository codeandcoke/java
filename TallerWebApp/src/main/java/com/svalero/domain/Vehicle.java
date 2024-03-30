package com.svalero.domain;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
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
    private String image;
    @NonNull
    private LocalDate registrationDate;
}
