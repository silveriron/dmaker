package com.fastcampus.programming.dmaker.service;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @InjectMocks
    private DMakerService dMakerService;

    private final CreateDeveloper.Request defaultRequest = CreateDeveloper.Request.builder()
            .developerLevel(DeveloperLevel.SENIOR)
            .developerSkillType(DeveloperSkillType.BACK_END)
            .experienceYears(12)
            .memberId("memberId")
            .name("name")
            .age(32)
            .build();

    private final DeveloperEntity defaultDeveloperEntity = DeveloperEntity.builder()
            .developerLevel(DeveloperLevel.SENIOR)
            .developerSkillType(DeveloperSkillType.BACK_END)
            .experienceYears(12)
            .statusCode(StatusCode.EMPLOYED)
            .name("name")
            .age(12)
            .build();

    @Test
    public void getDeveloper() {
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(defaultDeveloperEntity));


        DeveloperDetailDto developerDetail = dMakerService.getDeveloperById("memberId");

        assertEquals(DeveloperLevel.SENIOR, developerDetail.getDeveloperLevel());
        assertEquals(DeveloperSkillType.BACK_END, developerDetail.getDeveloperSkillType());
    }


    @Test
    void createDeveloperTest_success() {

        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.empty());

        ArgumentCaptor<DeveloperEntity> captor =
                ArgumentCaptor.forClass(DeveloperEntity.class);

        CreateDeveloper.Response developer = dMakerService.createDeveloper(defaultRequest);

        verify(developerRepository, times(1))
                .save(captor.capture());

        DeveloperEntity savedDeveloper = captor.getValue();
        assertEquals(DeveloperLevel.SENIOR, savedDeveloper.getDeveloperLevel());
        assertEquals(DeveloperSkillType.BACK_END, savedDeveloper.getDeveloperSkillType());
        assertEquals(32, savedDeveloper.getAge());
    }

    @Test
    void createDeveloperTest_failed_duplicated() {
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(defaultDeveloperEntity));
        ArgumentCaptor<DeveloperEntity> captor =
                ArgumentCaptor.forClass(DeveloperEntity.class);

        DMakerException dMakerException = assertThrows(DMakerException.class,
                () -> dMakerService.createDeveloper(defaultRequest));

        assertEquals(DMakerErrorCode.DUPLICATED_MEMBER_ID, dMakerException.getDMakerErrorCode());
    }
}