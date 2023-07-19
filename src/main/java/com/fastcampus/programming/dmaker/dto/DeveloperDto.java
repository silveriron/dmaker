package com.fastcampus.programming.dmaker.dto;

import com.fastcampus.programming.dmaker.entity.DeveloperEntity;
import com.fastcampus.programming.dmaker.type.DeveloperLevel;
import com.fastcampus.programming.dmaker.type.DeveloperSkillType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperDto {

    private DeveloperSkillType developerSkillType;

    private DeveloperLevel developerLevel;

    private String memberId;

    public static DeveloperDto fromEntity(DeveloperEntity developerEntity) {
        return DeveloperDto.builder()
                .developerSkillType(developerEntity.getDeveloperSkillType())
                .developerLevel(developerEntity.getDeveloperLevel())
                .memberId(developerEntity.getMemberId())
                .build();
    }
}
