package com.makeup.demo.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessages {

    ENTITY_NOT_FOUND("Podana godzina jest już niedostępna, proszę wybrać inną");


    private final String message;
}
