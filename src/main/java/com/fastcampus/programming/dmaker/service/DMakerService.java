package com.fastcampus.programming.dmaker.service;

import com.fastcampus.programming.dmaker.converter.DMakerConverter;
import com.fastcampus.programming.dmaker.dto.CreateDeveloper;
import com.fastcampus.programming.dmaker.dto.DeveloperDetailDto;
import com.fastcampus.programming.dmaker.dto.DeveloperDto;
import com.fastcampus.programming.dmaker.dto.EditDeveloper;
import com.fastcampus.programming.dmaker.entity.DeveloperEntity;
import com.fastcampus.programming.dmaker.entity.RetiredDeveloperEntity;
import com.fastcampus.programming.dmaker.exception.DMakerErrorCode;
import com.fastcampus.programming.dmaker.exception.DMakerException;
import com.fastcampus.programming.dmaker.repository.DeveloperRepository;
import com.fastcampus.programming.dmaker.repository.RetiredDeveloperRepository;
import com.fastcampus.programming.dmaker.type.DeveloperLevel;
import com.fastcampus.programming.dmaker.type.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DMakerService {

    private final DeveloperRepository developerRepository;

    private final RetiredDeveloperRepository retiredDeveloperRepository;

    private final DMakerConverter dMakerConverter;

    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request) {

        validateCreateDeveloperRequest(request);

        return CreateDeveloper.Response.fromEntity(
                developerRepository.save(
                        dMakerConverter.toEntity(request)
                )
        );
    }

    private void validateCreateDeveloperRequest(CreateDeveloper.Request request) {

        request.getDeveloperLevel().validateExperienceYear(request.getExperienceYears());

        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent(developer -> {
                    throw new DMakerException(DMakerErrorCode.DUPLICATED_MEMBER_ID);
                });
    }

    public List<DeveloperDto> getAllEmployedDevelopers() {

        List<DeveloperEntity> developerEntityList = developerRepository.findByStatusCode(StatusCode.EMPLOYED);

        return developerEntityList.stream().map(DeveloperDto::fromEntity).collect(Collectors.toList());

    }

    public DeveloperDetailDto getDeveloperById(String memberId) {
        return developerRepository.findByMemberId(memberId).map((dMakerConverter::toDeveloperDetailDto)).orElseThrow(() -> new DMakerException(DMakerErrorCode.NO_DEVELOPER));
    }

    public DeveloperDetailDto editDeveloper(String memberId, EditDeveloper.Request request) {
        request.getDeveloperLevel().validateExperienceYear(request.getExperienceYears());

        DeveloperEntity developerEntity = developerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new DMakerException(DMakerErrorCode.NO_DEVELOPER));

        developerEntity.setDeveloperLevel(request.getDeveloperLevel());
        developerEntity.setDeveloperSkillType(request.getDeveloperSkillType());
        developerEntity.setExperienceYears(request.getExperienceYears());

        return dMakerConverter.toDeveloperDetailDto(developerRepository.save(developerEntity));
    }

    @Transactional
    public DeveloperDetailDto deleteDeveloper(String memberId) {

        DeveloperEntity developerEntity = developerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new DMakerException(DMakerErrorCode.NO_DEVELOPER));
        developerEntity.setStatusCode(StatusCode.RETIRED);

        RetiredDeveloperEntity retiredDeveloperEntity = RetiredDeveloperEntity.builder()
                .memberId(memberId)
                .name(developerEntity.getName())
                .age(developerEntity.getAge())
                .build();

        retiredDeveloperRepository.save(retiredDeveloperEntity);

        return dMakerConverter.toDeveloperDetailDto(developerEntity);
    }
}
