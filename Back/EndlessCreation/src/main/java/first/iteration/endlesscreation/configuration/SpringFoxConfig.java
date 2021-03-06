package first.iteration.endlesscreation.configuration;

import first.iteration.endlesscreation.service.UserDetailsImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestHeader;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;

import java.util.Collections;
import java.util.List;
import static java.util.Collections.singletonList;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
    public static final String tile = "tile";
    public static final String tag = "tag";
    public static final String groupData = "groupData";
    public static final String color = "color";
    public static final String comment = "comment";
    public static final String book = "book";
    public static final String bookPage = "page";
    public static final String bookReview = "review";
    public static final String user = "user";
    public static final String file = "file";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("first.iteration.endlesscreation"))
                .paths(PathSelectors.any())
//                .paths(PathSelectors.regex("(?!/roleEntities).+"))
//                .paths(PathSelectors.regex("(?!/userEntities).+"))
                .build()
                .apiInfo(createApiInfo())
                .tags(new Tag(tile, ""), new Tag(tag, ""), new Tag(groupData, ""), new Tag(color, ""), new Tag(comment, ""),
                        new Tag(book, ""), new Tag(bookPage, ""), new Tag(bookReview, ""), new Tag(user, ""), new Tag(groupData, ""),
                        new Tag(file, ""))
                .ignoredParameterTypes(UserDetailsImpl.class, RequestHeader.class)
                .securityContexts(singletonList(createContext()))
                .securitySchemes(singletonList(createSchema()));
    }

    private ApiInfo createApiInfo() {
        return new ApiInfo("Endless Creation Application",
                "",
                "1.0.0",
                "",
                new Contact("", "", ""),
                "",
                "",
                Collections.emptyList());
    }


    private SecurityContext createContext() {
        return SecurityContext.builder()
                .securityReferences(createRef())
                .forPaths(PathSelectors.any())
                .build();
    }

    //
    private List<SecurityReference> createRef() {
        AuthorizationScope authorizationScope = new AuthorizationScope(
                "global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return singletonList(new SecurityReference("apiKey", authorizationScopes));
    }


    private SecurityScheme createSchema() {
        return new ApiKey("apiKey", "Authorization", "header");
    }
}


