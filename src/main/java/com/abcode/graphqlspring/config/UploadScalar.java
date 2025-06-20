package com.abcode.graphqlspring.config;

import graphql.schema.*;
import org.springframework.web.multipart.MultipartFile;

public class UploadScalar {

    public static final GraphQLScalarType Upload = GraphQLScalarType.newScalar()
            .name("Upload")
            .description("A file part in a multipart request.")
            .coercing(new Coercing<MultipartFile, Void>() {
                @Override
                public MultipartFile parseValue(Object input) throws CoercingParseValueException {
                    if (input instanceof MultipartFile) {
                        return (MultipartFile) input;
                    }
                    throw new CoercingParseValueException("Expected a MultipartFile.");
                }

                @Override
                public MultipartFile parseLiteral(Object input) {
                    throw new CoercingParseLiteralException("Upload literal unsupported");
                }

                @Override
                public Void serialize(Object dataFetcherResult) {
                    throw new CoercingSerializeException("Upload serialization unsupported");
                }
            })
            .build();
}

