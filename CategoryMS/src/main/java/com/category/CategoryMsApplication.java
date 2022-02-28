package com.category;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@EnableMongoAuditing
//@EnableSwagger2

public class CategoryMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategoryMsApplication.class, args);
		//System.out.print(new Date());
	}
@Bean
public Docket docket()
{
	ApiInfoBuilder apiInfoBuilder=new ApiInfoBuilder();
	apiInfoBuilder.title("category service");
	apiInfoBuilder.description("crud operation on categories");
    ApiInfo apiInfo=apiInfoBuilder.build();

	return new Docket(DocumentationType.SWAGGER_2)
		.select()
		.apis(RequestHandlerSelectors.basePackage("com.category"))//where to search restcontroller
		.paths(PathSelectors.any())//which path/endpoints is included for creating doc
		.build()
		.apiInfo(apiInfo)		
	;
	
}

}
