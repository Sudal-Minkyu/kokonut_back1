package com.app.kokonut.auth.jwt.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.app.kokonut.keydata.KeyDataService;
import com.app.kokonut.keydata.dtos.KeyDataAWSS3Dto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author Woody
 * Date : 2022-10-25
 * Time :
 * Remark : WebConfig
 */
@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final String AWSS3ACCESSKEY;

    private final String AWSS3SECRETKEY;

    @Autowired
    public WebConfig(KeyDataService keyDataService) {
        KeyDataAWSS3Dto keyDataAWSS3Dto = keyDataService.aws_S3_Key();
        this.AWSS3ACCESSKEY = keyDataAWSS3Dto.getAWSS3ACCESSKEY();
        this.AWSS3SECRETKEY = keyDataAWSS3Dto.getAWSS3SECRETKEY();
    }

    @Bean
    public BasicAWSCredentials AwsCredentianls() {
        return new BasicAWSCredentials(AWSS3ACCESSKEY,AWSS3SECRETKEY);
    }

    @Bean
    public AmazonS3 AwsS3Client(){
        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(this.AwsCredentianls()))
                .build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper;
    }

    @Bean
    public JpaResultMapper jpaResultMapper(){
        return new JpaResultMapper();
    }

}