package com.app.kokonut.email.emailHistory;

import com.app.kokonut.email.emailHistory.dtos.EmailHistoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class EmailHistoryService {

    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    /**
     * 이메일 발송 내역 저장
     */
    @Transactional
    public boolean saveEmailHistory(EmailHistoryDto emailHistoryDto) {
        log.info("### saveEmailHistory 호출");
        EmailHistory reciveHistory = new EmailHistory();

        if (emailHistoryDto == null) {
            log.error("### 이메일 발송 내역 정보를 확인할 수 없습니다.");
            return false;
        } else {
            log.info("### saveEmailHistory 저장 시작");
            reciveHistory.setFrom(emailHistoryDto.getFrom());
            reciveHistory.setFromName(emailHistoryDto.getFromName());
            reciveHistory.setTo(emailHistoryDto.getTo());
            reciveHistory.setToName(emailHistoryDto.getToName());
            reciveHistory.setTitle(emailHistoryDto.getTitle());
            reciveHistory.setContents(emailHistoryDto.getContents());

            log.info("### saveEmailHistory 저장");
            EmailHistory saveHistory = emailHistoryRepository.save(reciveHistory);
            if (emailHistoryRepository.existsById(saveHistory.getIdx())) {
                log.info("### 이메일 전송이력 저장에 성공했습니다. : " + saveHistory.getIdx());
                return true;
            } else {
                log.info("### 이메일 전송이력 저장에 실패했습니다. : " + saveHistory.getIdx());
                return false;
            }
        }
    }
}