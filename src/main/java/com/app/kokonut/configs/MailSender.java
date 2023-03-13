package com.app.kokonut.configs;


import com.app.kokonut.email.emailHistory.EmailHistoryRepository;
import com.app.kokonut.email.emailHistory.EmailHistory;
import com.app.kokonut.keydata.KeyDataService;
import com.app.kokonut.keydata.dtos.KeyDataMAILDto;
import com.app.kokonut.navercloud.NaverCloudPlatformService;
import com.app.kokonut.navercloud.dto.NCloudPlatformMailRequest;
import com.app.kokonut.navercloud.dto.RecipientForRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;


@Slf4j
@Service
public class MailSender {

	public final String frontServerDomainIp;
	public final String mailHost; // 보내는 사람의 이메일
	public final String myHost; // otp_url

	private final  NaverCloudPlatformService naverCloudPlatformService;
	private final EmailHistoryRepository emailHistoryRepository;

	@Autowired
	public MailSender(KeyDataService keyDataService, NaverCloudPlatformService naverCloudPlatformService, EmailHistoryRepository emailHistoryRepository) {
		KeyDataMAILDto keyDataMAILDto = keyDataService.mail_key();
		this.frontServerDomainIp = keyDataMAILDto.getFRONTSERVERDOMAINIP();
		this.naverCloudPlatformService = naverCloudPlatformService;
		this.emailHistoryRepository = emailHistoryRepository;
		this.mailHost = keyDataMAILDto.getMAILHOST();
		this.myHost = keyDataMAILDto.getOTPURL();
	}

	public boolean sendMail(String toEmail, String toName, String title, String contents) {
		return sendMail(mailHost, "kokonut", toEmail, toName, title, contents);
	}

	// 기존 코코넛 inquiryController에서 사용 중, 해당 기능 아직 리팩토링 전. 추후 변경 예정.
	public boolean inquirySendMail(String toEmail, String toName, String title, String contents) {
		return sendMail(toEmail, toName, mailHost, "kokonut", title, contents);
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
			emailHistory.setEhFrom(fromEmail);
			emailHistory.setEhFromName(fromName);
			emailHistory.setEhTo(toEmail);
			emailHistory.setEhToName(toName);
			emailHistory.setEhTitle(title);
			emailHistory.setEhContents(contents);
			emailHistory.setEhRegdate(LocalDateTime.now());
			emailHistoryRepository.save(emailHistory);
			log.info("### 이메일 발송 내역 저장 성공");
		}else {
			log.error("### 네이버 클라우드 플랫폼 서비스 sendMail 실패");
		}
		return result;
	}

	// TODO 메일 유형에 따라 발송시 HTML 화면으로 만들어줌.
	public String getHTML2(String viewURL) throws IOException {
		log.info("viewURL : "+viewURL);
		String mailViewURL = "http://"+myHost + viewURL;
		log.info("mailViewURL : "+mailViewURL);

		URL url = new URL(mailViewURL);
		URLConnection conn = url.openConnection();
		InputStream is = conn.getInputStream();
		return IOUtils.toString(is, StandardCharsets.UTF_8);
	}

	//TODO keyData 테이블에 데이터 추가후 사용
	public String getHTML5(HashMap<String, String> callTemplate) throws IOException {
		String htmlURL = frontServerDomainIp+"/src/template/"+callTemplate.get("template")+".html";
		URL url = new URL(htmlURL);
		URLConnection conn = url.openConnection();
		InputStream is = conn.getInputStream();
		String renaderdHtml = IOUtils.toString(is, StandardCharsets.UTF_8);
		Set<String>keySet = callTemplate.keySet();
		for ( String key : keySet){
			renaderdHtml = renaderdHtml.replace("{"+key+"}", callTemplate.get(key).toString());
		}
		return renaderdHtml;
	}
}
