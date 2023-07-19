package com.fastcampus.programming.dmaker.dto;

import com.fastcampus.programming.dmaker.exception.DMakerErrorCode;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DMakerErrorResponse {
    private DMakerErrorCode errorCode;
    private String errorMessage;
}
