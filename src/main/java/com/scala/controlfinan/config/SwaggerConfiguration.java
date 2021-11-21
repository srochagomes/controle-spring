package com.scala.controlfinan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author lgdamy@raiadrogasil.com on 13/03/2021
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration{

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfo("Scala Challenge", "Microsserviço ", "1.0", "urn:tos",
                        new Contact("Scala","","scala@scala.com"), "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<VendorExtension>()))
                .tags(new Tag("Accounts", "Services for manage accounts"),
                        new Tag("Categories", "Services for manage categories"),
                        new Tag("Transactions", "Services for manage transactions"))
                .consumes(Set.of(MediaType.APPLICATION_JSON_VALUE))
                .produces(Set.of(MediaType.APPLICATION_JSON_VALUE))
                .ignoredParameterTypes(Pageable.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.scala.controlfinan.controller"))
                .paths(PathSelectors.any()).build()
                .pathMapping("/")
                .securityContexts(Collections.singletonList(SecurityContext.builder().securityReferences(globalAuth()).build()))
                ;
    }

    private List<SecurityReference> globalAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[2];
        authorizationScopes[0] = new AuthorizationScope("read", "Grants read access");
        authorizationScopes[1] = new AuthorizationScope("write", "Grants write access");
        return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
    }

}
