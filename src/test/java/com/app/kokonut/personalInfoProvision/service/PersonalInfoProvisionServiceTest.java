package com.app.kokonut.personalInfoProvision.service;

import com.app.kokonut.refactor.personalInfoProvision.dto.PersonalInfoProvisionListDto;
import com.app.kokonut.refactor.personalInfoProvision.dto.PersonalInfoProvisionMapperDto;
import com.app.kokonut.refactor.personalInfoProvision.service.PersonalInfoProvisionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest
class PersonalInfoProvisionServiceTest {

    @Autowired
    private PersonalInfoProvisionService personalInfoProvisionService;

    @Test
    @DisplayName("정보제공 목록 조회 테스트")
    public void findByProvisionListTest(){

        // given
        PersonalInfoProvisionMapperDto personalInfoProvisionMapperDto = new PersonalInfoProvisionMapperDto();
        personalInfoProvisionMapperDto.setCompanyIdx(13); // 기업 고유값ID 13 조회

        // when
        List<PersonalInfoProvisionListDto> personalInfoProvisionListDtos = personalInfoProvisionService.findByProvisionList(personalInfoProvisionMapperDto);
        System.out.println("personalInfoProvisionListDtos : "+personalInfoProvisionListDtos);

    }

}