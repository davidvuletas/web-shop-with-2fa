package com.webshop.account;

import lombok.Getter;

public enum CardIssuer {

    VISA ("Visa"),
    MASTERCARD ("MasterCard"),
    VISA_ELECTRON ("Visa Electron"),
    MAESTRO ("Maestro"),
    AMERICAN_EXPRESS ("American Express"),
    DINERS_CLUB_US_CA (" Diners Club US & CA"),
    DISCOVER ("Discover"),
    DINERS_CLUB_INTERNATIONAL ("Diners Club International"),
    ROYAL_BANK_OF_CANADA ("Royal Bank of Canada");

    @Getter
    private final String name;

    CardIssuer(String name) {
        this.name = name;
    }
}
