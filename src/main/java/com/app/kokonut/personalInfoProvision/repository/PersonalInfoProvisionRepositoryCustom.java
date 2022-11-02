package com.app.kokonut.personalInfoProvision.repository;

import com.app.kokonut.personalInfoProvision.dto.PersonalInfoProvisionDto;
import com.app.kokonut.personalInfoProvision.dto.PersonalInfoProvisionListDto;
import com.app.kokonut.personalInfoProvision.dto.PersonalInfoProvisionMapperDto;
import com.app.kokonut.personalInfoProvision.dto.PersonalInfoProvisionNumberDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Woody
 * Date : 2022-11-01
 * Time :
 * Remark : 기존의 코코넛 프로젝트의 PersonalInfoProvision Sql 쿼리호출
 */
@Repository
public interface PersonalInfoProvisionRepositoryCustom {

    PersonalInfoProvisionNumberDto findByPersonalInfoProvisionNumber(String prefix); // selectProvisionLatestNumber -> 변경후

    PersonalInfoProvisionDto findByNumberProvision(String number); // selectProvision -> 변경후

    List<PersonalInfoProvisionListDto> findByProvisionList(PersonalInfoProvisionMapperDto personalInfoProvisionMapperDto); // selectProvisionList -> 변경후


}