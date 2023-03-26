package com.blog.me.blogging.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

@Configuration
public class SwaggerConfig {

    public static final String AUTH_HEADER = "Authorization";

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(securityContexts())
                .securitySchemes(List.of(apiKey()))
                .select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
                .apiInfo(info());
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTH_HEADER, "header");
    }

    private ApiInfo info() {
        return new ApiInfoBuilder()
                .title("Blog APIs Project")
                .description("College Project")
                .version("1.0.0")
                .contact(new Contact("Hemant", "https://github.com/hemantDwivedi","hemantdwiwedi@gmail.com"))
                .build();
    }

    private List<SecurityContext> securityContexts(){
        return List.of(SecurityContext.builder().securityReferences(securityReferenceList()).build());
    }

    private List<SecurityReference> securityReferenceList(){
        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = scope;
        return List.of(new SecurityReference("JWT", authorizationScopes));
    }
}
