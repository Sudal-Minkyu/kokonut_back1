package com.app.kokonut.common.component;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.app.kokonut.keydata.KeyDataService;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author Woody
 * Date : 2022-12-22
 * Remark : AWS S3 파일이미지 업로드용 유틸리티
 */
@Slf4j
@Service
public class AwsS3Util {

    private final String AWSBUCKET;

    private final AmazonS3 s3Client;

    @Autowired
    public AwsS3Util(KeyDataService keyDataService, AmazonS3 s3Client) {
        this.s3Client = s3Client;
        this.AWSBUCKET = keyDataService.findByKeyValue("aws_s3_bucket");
    }

    // AWS 사진 파일 업로드
    public String imageFileUpload(MultipartFile multipartFile, String storedFileName, String uploadPath) throws IOException {
        log.info("imageFileUpload 호출");

        log.info("AWSBUCKET : "+AWSBUCKET);

        ObjectMetadata omd = new ObjectMetadata();
        omd.setContentType(multipartFile.getContentType());
        omd.setContentLength(multipartFile.getSize());
        omd.setHeader("filename", storedFileName);//한글명들어가면 오류남
        String awsFilePath =AWSBUCKET+ uploadPath;

        // Copy file to the target location (Replacing existing file with the same name)
        s3Client.putObject(new PutObjectRequest(awsFilePath, storedFileName, multipartFile.getInputStream(), omd));

        //이미지 화일이면 섬네일변환후 울리기 파일명앞에 "s_" 를 붙임
        if(Objects.requireNonNull(multipartFile.getContentType()).substring(0,5).equalsIgnoreCase("IMAGE")){
            BufferedImage originamImage = ImageIO.read(multipartFile.getInputStream());
            BufferedImage destImg = Scalr.resize(originamImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 200);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(destImg, getExtension(storedFileName), os);
            byte[] buffer = os.toByteArray();
            InputStream destImgInputStream = new ByteArrayInputStream(buffer);

            ObjectMetadata s_omd = new ObjectMetadata();
            s_omd.setContentType(multipartFile.getContentType());
            s_omd.setContentLength(buffer.length);
            s_omd.setHeader("filename", "s_" +storedFileName);//한글명들어가면 오류남

            // Copy file to the target location (Replacing existing file with the same name)
            s3Client.putObject(new PutObjectRequest(awsFilePath, "s_"+storedFileName, destImgInputStream, s_omd));

            return storedFileName;

        }else{
            return null;
        }
    }

    // AWS 일반 파일 업로드
    public void nomalFileUpload(MultipartFile multipartFile, String fileName,String uploadPath) throws IOException {

        ObjectMetadata omd = new ObjectMetadata();
        omd.setContentType(multipartFile.getContentType());
        omd.setContentLength(multipartFile.getSize());
        omd.setHeader("filename", fileName); // 한글명들어가면 오류남
        String awsFilePath =AWSBUCKET+ uploadPath;

        s3Client.putObject(new PutObjectRequest(awsFilePath, fileName, multipartFile.getInputStream(), omd));
    }

    // AWS 파일삭제
    public void deleteObject(String bucketPath, String fileName) throws AmazonServiceException {
        s3Client.deleteObject(new DeleteObjectRequest(AWSBUCKET +  bucketPath, fileName));
    }

    // 파일이름 UUID 길이체크
    private String getExtension(String fileName) {
        int dotPosition = fileName.lastIndexOf('.');

        if (-1 != dotPosition && fileName.length() - 1 > dotPosition) {
            return fileName.substring(dotPosition + 1);
        } else {
            return "";
        }
    }

}