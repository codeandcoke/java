package com.svalero.parquenatural.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Activity {
    private int id;
    private String name;
    private String description;
    private Date datetime;
    private float price;
    private String picture;
}
