package com.abcode.graphqlspring.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected List<GraphQLError> resolveToMultipleErrors(Throwable ex, DataFetchingEnvironment env) {

        if (ex instanceof ConstraintViolationException violationEx) {
            return violationEx.getConstraintViolations()
                    .stream()
                    .map(violation ->
                            GraphqlErrorBuilder.newError(env)
                                    .message(violation.getPropertyPath() + ": " + violation.getMessage())
                                    .errorType(ErrorType.BAD_REQUEST)
                                    .build()
                    ).collect(Collectors.toList());
        }



        if (ex instanceof UserNotFoundException) {
            return List.of(
                    GraphqlErrorBuilder.newError(env)
                            .message(ex.getMessage())
                            .errorType(ErrorType.NOT_FOUND)
                            .build()
            );
        }

        if (ex instanceof UnauthorizedException) {
            return List.of(
                    GraphqlErrorBuilder.newError(env)
                            .message(ex.getMessage())
                            .errorType(ErrorType.UNAUTHORIZED)
                            .build()
            );
        }

        if (ex instanceof CustomBadRequestException || ex instanceof ConflictException) {
            return List.of(
                    GraphqlErrorBuilder.newError(env)
                            .message(ex.getMessage())
                            .errorType(ErrorType.BAD_REQUEST)
                            .build()
            );
        }

        // Handle JWT Expired
        if (ex instanceof ExpiredJwtException || (ex.getCause() instanceof ExpiredJwtException)) {
            return List.of(
                    GraphqlErrorBuilder.newError(env)
                            .message("Authentication failed: Token has expired.")
                            .errorType(ErrorType.UNAUTHORIZED)
                            .build()
            );
        }

        // Generic fallback
        return List.of(
                GraphqlErrorBuilder.newError(env)
                        .message("Internal error: " + ex.getMessage())
                        .errorType(ErrorType.INTERNAL_ERROR)
                        .build()
        );
    }
}