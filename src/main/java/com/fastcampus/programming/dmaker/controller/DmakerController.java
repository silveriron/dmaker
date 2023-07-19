package com.fastcampus.programming.dmaker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DmakerController {

    @GetMapping("/developers")
    public List<String> getAllDevelopers() {
        return List.of("developer1", "developer2");
    }


}
