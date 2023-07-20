package com.fastcampus.programming.dmaker.controller;

import com.fastcampus.programming.dmaker.dto.DeveloperDto;
import com.fastcampus.programming.dmaker.service.DMakerService;
import com.fastcampus.programming.dmaker.type.DeveloperLevel;
import com.fastcampus.programming.dmaker.type.DeveloperSkillType;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DMakerController.class)
class DMakerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DMakerService dMakerService;

    protected MediaType contentType =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    StandardCharsets.UTF_8);

    @Test
    void getAllDeveloper() throws Exception {
        DeveloperDto seniorDeveloperDto = DeveloperDto.builder()
                .developerLevel(DeveloperLevel.SENIOR)
                .developerSkillType(DeveloperSkillType.BACK_END)
                .memberId("senior")
                .build();
        DeveloperDto juniorDeveloperDto = DeveloperDto.builder()
                        .developerLevel(DeveloperLevel.JUNIOR)
                                .developerSkillType(DeveloperSkillType.BACK_END)
                                        .memberId("junior")
                                                .build();

        given(dMakerService.getAllEmployedDevelopers())
                .willReturn(List.of(seniorDeveloperDto, juniorDeveloperDto));

        mockMvc.perform(get("/developers").contentType(contentType))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(
                        jsonPath("$.[0].developerSkillType",
                                CoreMatchers.is(DeveloperSkillType.BACK_END.name())))
                .andExpect(
                        jsonPath("$.[0].developerLevel",
                                CoreMatchers.is(DeveloperLevel.SENIOR.name()))
                );

    }

}