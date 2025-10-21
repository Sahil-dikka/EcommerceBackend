package com.shopify.ecommerce.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class PaymentInformation {

    @Column(name = "cardholder_name")
    private String cardHolderName;


    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "expiration_date")
    private LocalDate expriationDate;

    @Column(name = "cvv")
    private String cvv;



}
