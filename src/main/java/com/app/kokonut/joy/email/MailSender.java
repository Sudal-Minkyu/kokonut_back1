package com.app.kokonut.joy.email;


import com.app.kokonut.email.emailHistory.EmailHistoryService;
import com.app.kokonut.email.emailHistory.dto.EmailHistoryDto;
import com.app.kokonut.navercloud.NaverCloudPlatformService;
import com.app.kokonut.navercloud.dto.NCloudPlatformMailRequest;
import com.app.kokonut.navercloud.dto.RecipientForRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
	private final EmailHistoryService emailHistoryService;

	@Autowired
	public MailSender(NaverCloudPlatformService naverCloudPlatformService, EmailHistoryService emailHistoryService) {
		this.naverCloudPlatformService = naverCloudPlatformService;
		this.emailHistoryService = emailHistoryService;
	}

	public boolean sendMail(String toEmail, String toName, String title, String contents) {
		return sendMail(emailHost, "kokonut", toEmail, toName, title, contents);
	}

	public boolean inquirySendMail(String toEmail, String toName, String title, String contents) {
		return sendMail(toEmail, toName, emailHost, "kokonut", title, contents);
	}

	public boolean sendMail(String fromEmail, String fromName, String toEmail, String toName, String title, String contents) {
		log.info("### MailSender.sendMail 시작");
		// 수신자 정보 세팅
		List<RecipientForRequest> recipients = new ArrayList<RecipientForRequest>();
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
			EmailHistoryDto emailHistoryDto = new EmailHistoryDto();
			emailHistoryDto.setFrom(fromEmail);
			emailHistoryDto.setFromName(fromName);
			emailHistoryDto.setTo(toEmail);
			emailHistoryDto.setToName(toName);
			emailHistoryDto.setTitle(title);
			emailHistoryDto.setContents(contents);
			result = emailHistoryService.saveEmailHistory(emailHistoryDto);
			if(result) {
				log.info("### 이메일 발송 내역 저장 성공");
			}else{
				log.error("### 이메일 발송 내역 저장 실패");
			}

		}else {
			log.error("### 네이버 클라우드 플랫폼 서비스 sendMail 실패");
		}
		return result;
	}

//	코코넛 기존 소스
//	public String getHTML(HttpServletRequest request, String viewURL) throws IOException {
//		String requestURL = request.getRequestURL().toString();
//		String mailViewURL = requestURL.substring(0, StringUtils.ordinalIndexOf(requestURL, "/", 3)) + viewURL;
//
//		URL url = new URL(mailViewURL);
//		URLConnection conn = url.openConnection();
//
//		InputStream is = conn.getInputStream();
//		return IOUtils.toString(is);
//	}

//	public String getHTML2(String viewURL) throws IOException {
//		System.out.println("viewURL : "+viewURL);
//		String mailViewURL = myHost + viewURL;
//		System.out.println("mailViewURL : "+mailViewURL);

//		URL url = new URL(mailViewURL);
//		URLConnection conn = url.openConnection();
		
//		InputStream is = conn.getInputStream();
//		return IOUtils.toString(is);
//	}
	
//	public String getHTML3(HttpServletRequest request, String viewURL) throws IOException {
		
//		InetAddress iAddress = InetAddress.getLocalHost();
//		log.info("### getHTML3 host IP : " + iAddress);
//		log.info("### getHTML host IP getHostAddress : " + iAddress.getHostAddress());
		
//		String mailViewURL = "http://" + iAddress.getHostAddress() + viewURL;
		
//		URL url = new URL(mailViewURL);
//		URLConnection conn = url.openConnection();
		
//		InputStream is = conn.getInputStream();
//		return IOUtils.toString(is);
//	}

//	public String getHTML4(String viewURL) throws IOException {
//		String mailViewURL = "http://isms-p-external-elb-1865218457.ap-northeast-2.elb.amazonaws.com" + viewURL;
		
//		URL url = new URL(mailViewURL);
//		URLConnection conn = url.openConnection();
		
//		InputStream is = conn.getInputStream();
//		return IOUtils.toString(is);
//	}
	
}
