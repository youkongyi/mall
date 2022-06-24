package com.youkongyi.mall.component;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
  * @description： OpenApiConfig
  *     com.youkongyi.mall.component.OpenApiConfig
  * @author： Aimer
  * @crateDate： 2022/06/24 17:56
  */
@OpenAPIDefinition(
        info = @Info(
                title = "mall-tiny",
                description = "Mall商城项目在线文档",
                contact = @Contact(name = "Aimer",url = "https://github.com/youkongyi",email = "youkongyi@gmail.com"),
                version = "0.1",
                license = @License(name = "Aimer", url = "https://github.com/youkongyi")
        ),
        // 请求服务地址配置，可以按不同的环境配置
        servers = {
                @Server(
                        url = "http://127.0.0.1:80",
                        description = "本机地址"
                )
        }
)
@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi openApi() {
        return GroupedOpenApi.builder().group("brand").pathsToMatch("/brand/**")
                .build();
    }
}
