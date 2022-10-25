package com.app.kokonut.woody.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kms.AWSKMSClient;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.DataKeySpec;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.GenerateDataKeyRequest;
import com.amazonaws.services.kms.model.GenerateDataKeyResult;
import com.app.kokonut.woody.common.component.AesCrypto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.HashMap;

@Service
public class AwsKmsService {

	@Value("${kokonut_aws_kms_id}")
	private String KEY_ID;

	@Value("${kokonut_aws_kms_access}")
	private String ACCESS_KEY;

	@Value("${kokonut_aws_kms_secret}")
	private String SECRET_KEY;

//	private final AwsKmsHistoryRepository awsKmsHistoryRepository;

//    @Autowired
//    public AwsKmsService(AwsKmsHistoryRepository awsKmsHistoryRepository){
//        this.awsKmsHistoryRepository = awsKmsHistoryRepository;
//    }

	public HashMap<String, Object> encrypt(String encKey) {

    	HashMap<String, Object> returnMap = new HashMap<String, Object>();
    	String encryptText = "";
    	String dataKey = "";
    	String errorMsg = "";

        try {
        	//AWS 계정의 Access Key, Secret Key. 추후 property로 설정하기

        	AWSKMSClient client = CreateClient();

        	//키 생성
        	GenerateDataKeyRequest generateDataKeyRequest = new GenerateDataKeyRequest()
        		    .withKeyId(KEY_ID) //KEY ID는 AWS KMS의 고객관리형 키에서 arn:aws:kms:... 부분 뒤 key / 의 값을 넣는다.
        		    .withKeySpec(DataKeySpec.AES_256); //암호화 방법

    		GenerateDataKeyResult dataKeyResult = client.generateDataKey(generateDataKeyRequest);

    		//암호화
    		byte[] enc = AesCrypto.encrypt(encKey, dataKeyResult.getPlaintext().array());
    		encryptText = java.util.Base64.getEncoder().encodeToString(enc);

//		    Cipher cipher = Cipher.getInstance(TRANSFORM);
//		    cipher.init(Cipher.ENCRYPT_MODE,
//		                new SecretKeySpec(dataKeyResult.getPlaintext().array(), "AES"));
//		    encryptText = java.util.Base64.getEncoder().encodeToString(cipher.doFinal(encKey.getBytes()));

    		//후에 복호화 시 사용할 dataKey값
		    ByteBuffer datakeyBf = dataKeyResult.getCiphertextBlob();
		    dataKey = java.util.Base64.getEncoder().encodeToString(datakeyBf.array());
		    System.out.println("encryptText : " + encryptText);

        } catch (Exception e) {
        	errorMsg = e.getMessage();
            e.printStackTrace();
            System.out.println("encrypt fail: " + e.getMessage());
        }

        returnMap.put("errorMsg", errorMsg);
        returnMap.put("encryptText", encryptText);
        returnMap.put("dataKey", dataKey);

		// 기록저장 메서드 추가할 것
//        awsKmsHistoryDao.insert("ENC");
        return returnMap;
    }
    
    public HashMap<String, Object> decrypt(String encryptText, String dataKey) {

    	HashMap<String, Object> returnMap = new HashMap<String, Object>();
    	String decryptText = "";
    	String errorMsg = "";
    	
        try {
        	
        	
        	AWSKMSClient client = CreateClient();
        	//dataKey : 최초 암호화 생성 시 dataKeyResult를 통해 나온 값을 가지고 있다가 복호화 할 때 사용한다.
		    DecryptRequest decryptRequest = new DecryptRequest()
		    	    .withCiphertextBlob(ByteBuffer.wrap(java.util.Base64.getDecoder().decode(dataKey))); 
		    
	    	ByteBuffer plainTextKey = client.decrypt(decryptRequest).getPlaintext();
	    	
		    byte[] decodeBase64src = java.util.Base64.getDecoder().decode(encryptText); //암호화 해서 저장된 키 값
	    	decryptText = AesCrypto.decrypt(decodeBase64src, plainTextKey.array());
	    	plainTextKey.clear(); //메모리 해제
//		    Cipher cipher2 = Cipher.getInstance(TRANSFORM);
//		    cipher2.init(Cipher.DECRYPT_MODE, new SecretKeySpec(plainTextKey.array(), "AES"));
//		    decryptText = new String(cipher2.doFinal(decodeBase64src));
		    
		    System.out.println("decryptText : " + decryptText);
		    
        } catch (Exception e) {
        	errorMsg = e.getMessage();
            e.printStackTrace();
            System.out.println("decrypt fail: " + e.getMessage());
        }
        
        returnMap.put("errorMsg", errorMsg);
        returnMap.put("decryptText", decryptText);

		// 기록저장 메서드 추가할 것
//		awsKmsHistoryRepository.save("DEC");
        return returnMap;
    }
	
    private AWSKMSClient CreateClient() {
    	//AWS 계정의 Access Key, Secret Key. 추후 property로 설정하기
    	BasicAWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
    	AWSKMSClient client = (AWSKMSClient) AWSKMSClientBuilder.standard()
    	    .withRegion(Regions.AP_NORTHEAST_2) //접속지역 설정. AWS 테스트 계정의 지역을 US_EAST_1로 설정해둠. 서울 : AP_NORTHEAST_2. 추후 실제 사용계정은 지역설정 따로 해야함
    	    .withCredentials(new AWSStaticCredentialsProvider(credentials))
    	    .build();
		
		return client;
    }
}
