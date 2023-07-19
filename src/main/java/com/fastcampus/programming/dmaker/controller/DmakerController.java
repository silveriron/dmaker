package com.fastcampus.programming.dmaker.controller;

import com.fastcampus.programming.dmaker.Dto.CreateDeveloper;
import com.fastcampus.programming.dmaker.service.DeveloperService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DmakerController {

    private final DeveloperService developerService;

    @GetMapping("/developers")
    public List<String> getAllDevelopers() {

        return List.of("developer1", "developer2");
    }

    @PostMapping("/create-developer")
    public CreateDeveloper.Response createDevelopers(@RequestBody @Valid CreateDeveloper.Request request) {
        return developerService.createDeveloper(request);
    }



}
