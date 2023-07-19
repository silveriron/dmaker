package com.fastcampus.programming.dmaker.dto;

import com.fastcampus.programming.dmaker.entity.DeveloperEntity;
import com.fastcampus.programming.dmaker.type.DeveloperLevel;
import com.fastcampus.programming.dmaker.type.DeveloperSkillType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class CreateDeveloper {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {

        @NotNull
        private DeveloperLevel developerLevel;

        @NotNull
        private DeveloperSkillType developerSkillType;

        @NotNull
        @Max(20)
        @Min(0)
        private Integer experienceYears;

        @NotNull
        private String memberId;

        @NotNull
        private String name;

        @Min(18)
        private Integer age;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {

        private DeveloperLevel developerLevel;
        private DeveloperSkillType developerSkillType;
        private Integer experienceYears;
        private String memberId;

        public static Response fromEntity(DeveloperEntity developerEntity) {
            return Response.builder()
                    .developerLevel(developerEntity.getDeveloperLevel())
                    .developerSkillType(developerEntity.getDeveloperSkillType())
                    .experienceYears(developerEntity.getExperienceYears())
                    .memberId(developerEntity.getMemberId())
                    .build();
        }
    }


}
