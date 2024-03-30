package com.svalero.hellojdbi.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Setter(AccessLevel.NONE)
    private int id;
    private String name;
    private float price;
}
