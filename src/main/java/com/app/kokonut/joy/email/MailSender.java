package com.app.kokonut.joy.email;


import com.app.kokonut.email.emailHistory.EmailHistoryRepository;
import com.app.kokonut.email.emailHistory.entity.EmailHistory;
import com.app.kokonut.navercloud.NaverCloudPlatformService;
import com.app.kokonut.navercloud.dto.NCloudPlatformMailRequest;
import com.app.kokonut.navercloud.dto.RecipientForRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class MailSender {

	@Value("contact@kokonut.me") // TODO ncloud.email.host=contact@kokonut.me 공통 properties 로 추가
	public String emailHost;

	@Value("${otp.hostUrl}")
    public String myHost;

	private final  NaverCloudPlatformService naverCloudPlatformService;
	private final EmailHistoryRepository emailHistoryRepository;

	@Autowired
	public MailSender(NaverCloudPlatformService naverCloudPlatformService, EmailHistoryRepository emailHistoryRepository) {
		this.naverCloudPlatformService = naverCloudPlatformService;
		this.emailHistoryRepository = emailHistoryRepository;
	}

	public boolean sendMail(String toEmail, String toName, String title, String contents) {
		return sendMail(emailHost, "kokonut", toEmail, toName, title, contents);
	}

	// 기존 코코넛 inquiryController에서 사용 중, 해당 기능 아직 리팩토링 전. 추후 변경 예정.
	public boolean inquirySendMail(String toEmail, String toName, String title, String contents) {
		return sendMail(toEmail, toName, emailHost, "kokonut", title, contents);
	}

	@Transactional
	public boolean sendMail(String fromEmail, String fromName, String toEmail, String toName, String title, String contents) {
		log.info("### MailSender.sendMail 시작");
		// 수신자 정보 세팅
		List<RecipientForRequest> recipients = new ArrayList<>();
		RecipientForRequest recipient = new RecipientForRequest();
		recipient.setAddress(toEmail);

		if(toName != null && !toName.isEmpty()) {
			recipient.setName(toName);
		}

		recipient.setType("R");
		recipients.add(recipient);

		// 발신자 정보 세팅
		NCloudPlatformMailRequest req = new NCloudPlatformMailRequest();
		req.setSenderAddress(fromEmail);
		req.setSenderName(fromName);
		req.setTitle(title);
		req.setBody(contents);
		req.setRecipients(recipients);
		req.setUnsubscribeMessage("광고 수신 문구");
		req.setIndividual(true);
		req.setAdvertising(false);

		log.info("### 네이버 클라우드 플랫폼 서비스 sendMail 시작");
		boolean result = naverCloudPlatformService.sendMail(req);
		if(result) {
			log.info("### 네이버 클라우드 플랫폼 서비스 sendMail 성공");
			log.info("### 이메일 발송 내역 저장");
			EmailHistory emailHistory = new EmailHistory();
			emailHistory.setFrom(fromEmail);
			emailHistory.setFromName(fromName);
			emailHistory.setTo(toEmail);
			emailHistory.setToName(toName);
			emailHistory.setTitle(title);
			emailHistory.setContents(contents);

			emailHistoryRepository.save(emailHistory);
			log.info("### 이메일 발송 내역 저장 성공");
		}else {
			log.error("### 네이버 클라우드 플랫폼 서비스 sendMail 실패");
		}
		return result;
	}

}
