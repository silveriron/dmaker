package com.fastcampus.programming.dmaker.service;

import com.fastcampus.programming.dmaker.converter.DMakerConverter;
import com.fastcampus.programming.dmaker.dto.CreateDeveloper;
import com.fastcampus.programming.dmaker.dto.DeveloperDetailDto;
import com.fastcampus.programming.dmaker.entity.DeveloperEntity;
import com.fastcampus.programming.dmaker.exception.DMakerErrorCode;
import com.fastcampus.programming.dmaker.exception.DMakerException;
import com.fastcampus.programming.dmaker.repository.DeveloperRepository;
import com.fastcampus.programming.dmaker.repository.RetiredDeveloperRepository;
import com.fastcampus.programming.dmaker.type.DeveloperLevel;
import com.fastcampus.programming.dmaker.type.DeveloperSkillType;
import com.fastcampus.programming.dmaker.type.StatusCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.fastcampus.programming.dmaker.constant.DMakerConstant.MIN_SENIOR_EXPERIENCE_YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DMakerServiceTest {

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private RetiredDeveloperRepository retiredDeveloperRepository;

    @Mock
    private DMakerConverter dMakerConverter;

    @InjectMocks
    private DMakerService dMakerService;



    private CreateDeveloper.Request getDefaultRequest(
            DeveloperLevel developerLevel,
            DeveloperSkillType developerSkillType,
            int experienceYears
    ) {
        return CreateDeveloper.Request.builder()
                .developerLevel(developerLevel)
                .developerSkillType(developerSkillType)
                .experienceYears(experienceYears)
                .memberId("memberId")
                .name("name")
                .age(32)
                .build();
    }

    private final DeveloperEntity defaultDeveloperEntity = DeveloperEntity.builder()
            .developerLevel(DeveloperLevel.SENIOR)
            .developerSkillType(DeveloperSkillType.BACK_END)
            .experienceYears(12)
            .statusCode(StatusCode.EMPLOYED)
            .name("name")
            .age(12)
            .build();

    private final DeveloperDetailDto defaultDeveloperDetail = DeveloperDetailDto.builder()
            .developerLevel(DeveloperLevel.SENIOR)
            .developerSkillType(DeveloperSkillType.BACK_END)
            .experienceYears(12)
            .statusCode(StatusCode.EMPLOYED)
            .memberId("memberId")
            .name("name")
            .age(32)
            .build();

    @Test
    public void getDeveloper() {
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(defaultDeveloperEntity));

        given(dMakerConverter.toDeveloperDetailDto(any()))
                .willReturn(defaultDeveloperDetail);


        DeveloperDetailDto developerDetail = dMakerService.getDeveloperById("memberId");

        assertEquals(DeveloperLevel.SENIOR, developerDetail.getDeveloperLevel());
        assertEquals(DeveloperSkillType.BACK_END, developerDetail.getDeveloperSkillType());
    }


    @Test
    void createDeveloperTest_success() {

        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.empty());

        given(dMakerConverter.toEntity(any()))
                .willReturn(defaultDeveloperEntity);

        ArgumentCaptor<DeveloperEntity> captor =
                ArgumentCaptor.forClass(DeveloperEntity.class);

        given(developerRepository.save(any()))
                .willReturn(defaultDeveloperEntity);

        dMakerService.createDeveloper(getDefaultRequest(DeveloperLevel.SENIOR, DeveloperSkillType.BACK_END, MIN_SENIOR_EXPERIENCE_YEARS + 1));

        verify(developerRepository, times(1))
                .save(captor.capture());

        DeveloperEntity savedDeveloper = captor.getValue();
        assertEquals(DeveloperLevel.SENIOR, savedDeveloper.getDeveloperLevel());
        assertEquals(DeveloperSkillType.BACK_END, savedDeveloper.getDeveloperSkillType());
        assertEquals(12, savedDeveloper.getAge());
    }

    @Test
    void createDeveloperTest_fail_low_senior() {
        DMakerException dMakerException = assertThrows(DMakerException.class,
                () -> dMakerService.createDeveloper(getDefaultRequest(DeveloperLevel.SENIOR, DeveloperSkillType.BACK_END, 7))
                );

        assertEquals(DMakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED, dMakerException.getDMakerErrorCode());

    }

    @Test
    void createDeveloperTest_fail_duplicated() {
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(defaultDeveloperEntity));
        ArgumentCaptor<DeveloperEntity> captor =
                ArgumentCaptor.forClass(DeveloperEntity.class);

        DMakerException dMakerException = assertThrows(DMakerException.class,
                () -> dMakerService.createDeveloper(getDefaultRequest(DeveloperLevel.SENIOR, DeveloperSkillType.BACK_END, MIN_SENIOR_EXPERIENCE_YEARS + 1)));

        assertEquals(DMakerErrorCode.DUPLICATED_MEMBER_ID, dMakerException.getDMakerErrorCode());
    }
}