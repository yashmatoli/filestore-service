package com.example.filestore.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

	 @Bean
	    public Docket productApi() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                .apis(RequestHandlerSelectors.basePackage("com.example.filestore.controller"))
	                .paths(regex("/.*"))
	                .build()
	                .apiInfo(metaInfo());
	    }
	 
	 private ApiInfo metaInfo() {
		 List<VendorExtension> vendorExtensions = new ArrayList<>();
	        ApiInfo apiInfo = new ApiInfo(
	                "FileStore Microservice",
	                "FileStore for import and export of tar files",
	                "1.0",
	                "Terms of Service",
	                new Contact("Yash Pal Singh", "www.linkedin.com/in/yash-singh-68945aa8",
	                        "imm.yashsingh@gmail.com"),
	                "Spring Boot Version 2.3.3",
	                "https://start.spring.io/", vendorExtensions
	        );

	        return apiInfo;
	    }
}
