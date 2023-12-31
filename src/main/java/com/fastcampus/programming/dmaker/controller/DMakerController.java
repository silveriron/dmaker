package com.fastcampus.programming.dmaker.controller;

import com.fastcampus.programming.dmaker.dto.CreateDeveloper;
import com.fastcampus.programming.dmaker.dto.DeveloperDetailDto;
import com.fastcampus.programming.dmaker.dto.DeveloperDto;
import com.fastcampus.programming.dmaker.dto.EditDeveloper;
import com.fastcampus.programming.dmaker.service.DMakerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DMakerController {

    private final DMakerService developerService;

    @GetMapping("/developers")
    public List<DeveloperDto> getAllEmployedDevelopers() {
        return developerService.getAllEmployedDevelopers();
    }

    @PostMapping("/create-developer")
    public CreateDeveloper.Response createDevelopers(
            @RequestBody @Valid final CreateDeveloper.Request request
    ) {
        return developerService.createDeveloper(request);
    }

    @GetMapping("/developer/{memberId}")
    public DeveloperDetailDto getDeveloperById(
            @PathVariable String memberId
    ) {
        return developerService.getDeveloperById(memberId);
    }

    @PutMapping("/developer/{memberId}")
    public DeveloperDetailDto editDeveloper(
            @PathVariable String memberId,
            @RequestBody @Valid EditDeveloper.Request request
            ) {
        return developerService.editDeveloper(memberId, request);
    }

    @DeleteMapping("/developer/{memberId}")
    public DeveloperDetailDto deleteDeveloper(
            @PathVariable String memberId
    ) {
        return developerService.deleteDeveloper(memberId);
    }
}
