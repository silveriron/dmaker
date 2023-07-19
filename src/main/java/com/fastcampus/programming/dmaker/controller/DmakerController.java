package com.fastcampus.programming.dmaker.controller;

import com.fastcampus.programming.dmaker.dto.CreateDeveloper;
import com.fastcampus.programming.dmaker.dto.DeveloperDetailDto;
import com.fastcampus.programming.dmaker.dto.DeveloperDto;
import com.fastcampus.programming.dmaker.service.DeveloperService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DmakerController {

    private final DeveloperService developerService;

    @GetMapping("/developers")
    public List<DeveloperDto> getAllDevelopers() {

        return developerService.getAllDevelopers();
    }

    @PostMapping("/create-developer")
    public CreateDeveloper.Response createDevelopers(@RequestBody @Valid CreateDeveloper.Request request) {
        return developerService.createDeveloper(request);
    }

    @GetMapping("/developer/{memberId}")
    public DeveloperDetailDto getDeveloperById(
            @PathVariable String memberId
    ) {
        return developerService.getDeveloperById(memberId);
    }

}
