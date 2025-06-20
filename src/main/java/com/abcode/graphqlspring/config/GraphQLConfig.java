package com.abcode.graphqlspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import reactor.core.publisher.Mono;




@Configuration
public class GraphQLConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder.scalar(UploadScalar.Upload);
    }

    @Bean
    public WebGraphQlInterceptor disableIntrospectionForUnauthorized() {
        return (webInput, interceptorChain) -> {
            boolean isIntrospectionQuery = webInput.getDocument().contains("__schema");
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (isIntrospectionQuery && (auth == null || !auth.isAuthenticated())) {
                return Mono.error(new AccessDeniedException("Introspection disabled"));
            }
            return interceptorChain.next(webInput);
        };
    }


}
