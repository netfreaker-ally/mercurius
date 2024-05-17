package com.Mercurius.Bridge.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class OfferRepresentation {

    private String offerId;
    private String name;
    private String description;
    private double discountPercentage;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

   
}
