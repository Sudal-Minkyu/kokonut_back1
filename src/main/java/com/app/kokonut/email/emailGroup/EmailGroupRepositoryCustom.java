package com.app.kokonut.email.emailGroup;

import com.app.kokonut.email.emailGroup.dto.EmailGroupAdminInfoDto;
import com.app.kokonut.email.emailGroup.dto.EmailGroupListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface EmailGroupRepositoryCustom {
    EmailGroupAdminInfoDto findEmailGroupAdminInfoByIdx(Integer idx);

    // 이메일 목록 조회 -> 기존의 코코넛 메서트 : SelectEmailGroupList
    Page<EmailGroupListDto> findEmailGroupPage(Pageable pageable);

    // 이메일 목록 조회 -> 기존의 코코넛 메서트 : SelectEmailGroupList
    List<EmailGroupListDto> findEmailGroupDetails();
}
