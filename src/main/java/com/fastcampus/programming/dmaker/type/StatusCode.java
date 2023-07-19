package com.fastcampus.programming.dmaker.type;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StatusCode {

    EMPLOYED("고용"),
    RETIRED("퇴직");

    private final String description;
}
