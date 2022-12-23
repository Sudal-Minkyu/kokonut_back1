package com.app.kokonut.joy.email;


import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class MailSender {

	@Value("contact@kokonut.me") // TODO ncloud.email.host=contact@kokonut.me 공통 properties 로 추가
	public String emailHost;

	@Value("${otp.hostUrl}")
    public String myHost;

    //@Autowired
	//NCloudPlatform naverCloudPlatform; // TODO 우디가 만들어준다고 함
    
    @Autowired
    EmailHistoryService emailHistoryService;
    
	public boolean sendMail(String toEmail, String toName, String title, String contents) {
		return sendMail(emailHost, "kokonut", toEmail, toName, title, contents);
	}

	public boolean inquirySendMail(String toEmail, String toName, String title, String contents) {
		return sendMail(toEmail, toName, emailHost, "kokonut", title, contents);
	}

	public boolean sendMail(String fromEmail, String fromName, String toEmail, String toName, String title, String contents) {
		List<RecipientForRequest> recipients = new ArrayList<RecipientForRequest>();
		RecipientForRequest recipient = new RecipientForRequest();
		recipient.setAddress(toEmail);
		if(toName != null && !toName.isEmpty()) {
			recipient.setName(toName);
		}
		recipient.setType("R");
		recipients.add(recipient);
		
		// 발신자
		NCloudPlatformMailRequest req = new NCloudPlatformMailRequest();
		req.setSenderAddress(fromEmail);
		req.setSenderName(fromName);
		req.setTitle(title);
		req.setBody(contents);
		req.setRecipients(recipients);
		req.setUnsubscribeMessage("광고 수신 문구");
		req.setIndividual(true);
		req.setAdvertising(false);
		
		boolean result = naverCloudPlatform.SendMail(req);
		if(result) {
			logger.info("naver mail send success.");
			
			// 이메일 전송 이력 저장
			HashMap<String, Object> historyInsertMap = new HashMap<String, Object>();
			historyInsertMap.put("from", fromEmail);
			historyInsertMap.put("fromName", fromName);
			historyInsertMap.put("to", toEmail);
			historyInsertMap.put("toName", toName);
			historyInsertMap.put("title", title);
			historyInsertMap.put("contents", contents);
			
			if(!emailHistoryService.insert(historyInsertMap)) {
				logger.error("failed to insert email history.: {}", historyInsertMap.toString());
			}
		} else {
			logger.error("naver mail send fail.");
		}
		return result;
	}
	
	public String getHTML(HttpServletRequest request, String viewURL) throws IOException {
		String requestURL = request.getRequestURL().toString();
		String mailViewURL = requestURL.substring(0, StringUtils.ordinalIndexOf(requestURL, "/", 3)) + viewURL;
		
//		InetAddress iAddress = InetAddress.getLocalHost();
//		logger.info("### getHTML host IP : " + iAddress);
//		logger.info("### getHTML host IP getHostAddress : " + iAddress.getHostAddress());
//		String mailViewURL = iAddress.getHostAddress() + viewURL;
		
		URL url = new URL(mailViewURL);
		URLConnection conn = url.openConnection();
		
		InputStream is = conn.getInputStream();
		return IOUtils.toString(is);
	}

	public String getHTML2(String viewURL) throws IOException {
		System.out.println("viewURL : "+viewURL);
		String mailViewURL = MY_HOST + viewURL;
		System.out.println("mailViewURL : "+mailViewURL);
//		String mailViewURL = "127.0.0.1" + viewURL;
//		String mailViewURL = "http://localhost:8080" + viewURL;
		
		URL url = new URL(mailViewURL);
		URLConnection conn = url.openConnection();
		
		InputStream is = conn.getInputStream();
		return IOUtils.toString(is);
	}
	
	public String getHTML3(HttpServletRequest request, String viewURL) throws IOException {
//		String mailViewURL = requestURL.substring(0, StringUtils.ordinalIndexOf(requestURL, "/", 3)) + viewURL;
		
		InetAddress iAddress = InetAddress.getLocalHost();
		logger.info("### getHTML3 host IP : " + iAddress);
		logger.info("### getHTML host IP getHostAddress : " + iAddress.getHostAddress());
		
		String mailViewURL = "http://" + iAddress.getHostAddress() + viewURL;
		
		URL url = new URL(mailViewURL);
		URLConnection conn = url.openConnection();
		
		InputStream is = conn.getInputStream();
		return IOUtils.toString(is);
	}

	public String getHTML4(String viewURL) throws IOException {
//		String mailViewURL = MY_HOST + viewURL;
//		String mailViewURL = "http://127.0.0.1" + viewURL;
//		String mailViewURL = "http://52.79.70.240" + viewURL;
		String mailViewURL = "http://isms-p-external-elb-1865218457.ap-northeast-2.elb.amazonaws.com" + viewURL;
		
		URL url = new URL(mailViewURL);
		URLConnection conn = url.openConnection();
		
		InputStream is = conn.getInputStream();
		return IOUtils.toString(is);
	}
	
}
