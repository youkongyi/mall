package com.youkongyi.mall.config;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
  * @description： 阿里云OSS配置
  *     com.youkongyi.mall.config.OSSConfig.
  * @author： Aimer
  * @crateDate： 2022/7/25 19:03
  */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "aliyun.oss")
public class OSSConfig {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    @Bean
    public OSSClient ossClient(){
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration clientConfiguration = new ClientBuilderConfiguration();
        return new OSSClient(endpoint, credentialsProvider , clientConfiguration);
    }

}
