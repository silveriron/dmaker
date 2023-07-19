package com.fastcampus.programming.dmaker.exception;

import lombok.Getter;

@Getter
public class DMakerException extends RuntimeException {

    private final DMakerErrorCode dMakerErrorCode;
    private final String detailMessage;

    public DMakerException(DMakerErrorCode errorCode) {
        super(errorCode.getMessage());
        this.dMakerErrorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

    public DMakerException(DMakerErrorCode errorCode, String detailMessage) {
        super(detailMessage);
        this.dMakerErrorCode = errorCode;
        this.detailMessage = detailMessage;
    }
}
