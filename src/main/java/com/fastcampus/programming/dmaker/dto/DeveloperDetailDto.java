package com.fastcampus.programming.dmaker.dto;

import com.fastcampus.programming.dmaker.entity.DeveloperEntity;
import com.fastcampus.programming.dmaker.type.DeveloperLevel;
import com.fastcampus.programming.dmaker.type.DeveloperSkillType;
import com.fastcampus.programming.dmaker.type.StatusCode;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeveloperDetailDto {

    private DeveloperLevel developerLevel;

    private DeveloperSkillType developerSkillType;

    private Integer experienceYears;

    private StatusCode statusCode;

    private String memberId;

    private String name;

    private Integer age;

    public static DeveloperDetailDto fromEntity(DeveloperEntity developerEntity) {
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
