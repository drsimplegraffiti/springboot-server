package com.abcode.graphqlspring.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String usernameIsAlreadyInUse) {
    }
}
