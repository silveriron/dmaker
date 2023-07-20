package com.fastcampus.programming.dmaker.converter;

import com.fastcampus.programming.dmaker.dto.CreateDeveloper;
import com.fastcampus.programming.dmaker.dto.DeveloperDetailDto;
import com.fastcampus.programming.dmaker.entity.DeveloperEntity;
import com.fastcampus.programming.dmaker.type.StatusCode;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public class DMakerConverter {

    public DeveloperEntity toEntity(@NotNull CreateDeveloper.Request request) {
        return DeveloperEntity.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getExperienceYears())
                .memberId(request.getMemberId())
                .statusCode(StatusCode.EMPLOYED)
                .name(request.getName())
                .age(request.getAge())
                .build();
    }

    public DeveloperDetailDto toDeveloperDetailDto(@NotNull DeveloperEntity developerEntity) {
        return DeveloperDetailDto.builder()
                .developerLevel(developerEntity.getDeveloperLevel())
                .developerSkillType(developerEntity.getDeveloperSkillType())
                .experienceYears(developerEntity.getExperienceYears())
                .statusCode(developerEntity.getStatusCode())
                .memberId(developerEntity.getMemberId())
                .name(developerEntity.getName())
                .age(developerEntity.getAge())
                .build();
    }
}
