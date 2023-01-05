package com.app.kokonut.auth.jwt.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.app.kokonut.keydata.KeyDataService;
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

//    @Value("${kokonut.aws.s3.access}")
    private final String AWSS3ACCESSKEY;

//    @Value("${kokonut.aws.s3.secret}")
    private final String AWSS3SECRETKEY;

    @Autowired
    public WebConfig(KeyDataService keyDataService) {
        this.AWSS3ACCESSKEY = keyDataService.findByKeyValue("aws_s3_access");
        this.AWSS3SECRETKEY = keyDataService.findByKeyValue("aws_s3_secret");
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