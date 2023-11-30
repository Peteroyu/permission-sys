package com.xmj.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//配置标签，代表该类是一个配置类
@Configuration
//开启Swagger
@EnableSwagger2
public class SwaggerConfig {
    //创建API接口文档
    @Bean
    public Docket createRestApi() {
        //创建一个Docket，可以理解问Docket代表一个文档的构建器
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())//指定api相关的信息
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xmj.controller"))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("客户关系管理系统")
                .description("客户关系管理系统接口文档说明")
                .contact(new Contact("crm", "", "crm@xmj.com"))
                .version("1.0")
                .build();
    }
}