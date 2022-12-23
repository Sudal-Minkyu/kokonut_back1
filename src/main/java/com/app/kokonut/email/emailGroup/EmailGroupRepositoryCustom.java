package com.app.kokonut.email.emailGroup;


import com.app.kokonut.email.emailGroup.dto.EmailGroupAdminInfoDto;

public interface EmailGroupRepositoryCustom {
    EmailGroupAdminInfoDto findEmailGroupAdminInfoByIdx(Integer idx);
}
