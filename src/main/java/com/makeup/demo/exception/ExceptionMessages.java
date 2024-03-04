package com.makeup.demo.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessages {

    ENTITY_NOT_FOUND("Podana godzina jest już niedostępna, proszę wybrać inną"),
    CLIENT_ID_NOT_FOUND("Nie istnieje klient o takim kodzie, zweryfikuj to");


    private final String message;
}
