package com.app.kokonut.woody.common.component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kms.AWSKMSClient;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.DataKeySpec;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.GenerateDataKeyRequest;
import com.amazonaws.services.kms.model.GenerateDataKeyResult;
import com.app.kokonut.awsKmsHistory.AwsKmsHistoryRepository;
import com.app.kokonut.awsKmsHistory.dto.AwsKmsResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.ByteBuffer;
import java.util.HashMap;

/**
 * @author Woody
 * Date : 2022-12-22
 * Remark : AWS Kms 암호화키 발급 유틸리티
 */
@Slf4j
@Service
public class AwsKmsUtil {

    @Value("${kokonut.aws.kms.id}")
    private String KEY_ID;

    @Value("${kokonut.aws.kms.access}")
    private String ACCESS_KEY;

    @Value("${kokonut.aws.kms.secret}")
    private String SECRET_KEY;

    private AWSKMSClient CreateClient() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        return (AWSKMSClient) AWSKMSClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    public AwsKmsResultDto encrypt(String encKey) {
        log.info("encrypt 호출");

        AwsKmsResultDto awsKmsResultDto = new AwsKmsResultDto();

        String encryptText;
        String dataKey;
        String result = "success";

        try {

            AWSKMSClient client = CreateClient();

            //키 생성
            GenerateDataKeyRequest generateDataKeyRequest = new GenerateDataKeyRequest()
                    .withKeyId(KEY_ID) //KEY ID는 AWS KMS의 고객관리형 키에서 arn:aws:kms:... 부분 뒤 key / 의 값을 넣는다.
                    .withKeySpec(DataKeySpec.AES_256); //암호화 방법

            GenerateDataKeyResult dataKeyResult = client.generateDataKey(generateDataKeyRequest);

            //암호화
            byte[] enc = AesCrypto.encrypt(encKey, dataKeyResult.getPlaintext().array());
            encryptText = java.util.Base64.getEncoder().encodeToString(enc);

            // 복호화 시 사용할 dataKey값
            ByteBuffer datakeyBf = dataKeyResult.getCiphertextBlob();
            dataKey = java.util.Base64.getEncoder().encodeToString(datakeyBf.array());

            log.error("KMS 키생성 성공 : " + encryptText);
        } catch (Exception e) {
            log.error("KMS 키생성 실패 : " + e.getMessage());
            result = "fail";
            awsKmsResultDto.setResult(result);
            return awsKmsResultDto;
        }

        awsKmsResultDto.setResult(result);
        awsKmsResultDto.setEncryptText(encryptText);
        awsKmsResultDto.setDataKey(dataKey);

        return awsKmsResultDto;
    }

    public AwsKmsResultDto decrypt(String encryptText, String dataKey) {

        log.info("decrypt 호출");

        AwsKmsResultDto awsKmsResultDto = new AwsKmsResultDto();

        String decryptText;
        String result = "success";

        try {

            AWSKMSClient client = CreateClient();
            //dataKey : 최초 암호화 생성 시 dataKeyResult를 통해 나온 값을 가지고 있다가 복호화 할 때 사용한다.
            DecryptRequest decryptRequest = new DecryptRequest()
                    .withCiphertextBlob(ByteBuffer.wrap(java.util.Base64.getDecoder().decode(dataKey)));

            ByteBuffer plainTextKey = client.decrypt(decryptRequest).getPlaintext();

            byte[] decodeBase64src = java.util.Base64.getDecoder().decode(encryptText); //암호화 해서 저장된 키 값
            decryptText = AesCrypto.decrypt(decodeBase64src, plainTextKey.array());
            plainTextKey.clear(); //메모리 해제

            log.error("KMS 키생성 성공 : " + decryptText);
        } catch (Exception e) {
            log.error("KMS 키생성 실패 : " + e.getMessage());
            result = "fail";
            awsKmsResultDto.setResult(result);
            return awsKmsResultDto;
        }

        awsKmsResultDto.setResult(result);
        awsKmsResultDto.setDecryptText(decryptText);

        return awsKmsResultDto;
    }


}