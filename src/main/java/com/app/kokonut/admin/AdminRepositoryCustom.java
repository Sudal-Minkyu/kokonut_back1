package com.app.kokonut.admin;

import com.app.kokonut.admin.dtos.AdminCompanyInfoDto;
import com.app.kokonut.admin.dtos.AdminEmailInfoDto;
import com.app.kokonut.admin.dtos.AdminOtpKeyDto;

/**
 * @author Woody
 * Date : 2022-11-29
 * Time :
 * Remark : 기존의 코코넛 프로젝트의 Admin Sql 쿼리호출
 */
public interface AdminRepositoryCustom {

    AdminOtpKeyDto findByOtpKey(String email);

    AdminCompanyInfoDto findByCompanyInfo(String email);

    AdminEmailInfoDto findByEmailInfo(Integer idx);

}