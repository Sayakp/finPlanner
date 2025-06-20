package com.finplanner.finplanner.exception;

public class RegistrationValidationException extends RuntimeException {
    private final RegistrationErrorCode code;

    public RegistrationValidationException(RegistrationErrorCode code) {
        super(code.name());
        this.code = code;
    }

    public RegistrationErrorCode getCode() {
        return code;
    }

    public enum RegistrationErrorCode {
        USERNAME_TAKEN("Username is already taken"),
        EMAIL_IN_USE("Email is already in use"),
        ROLE_NOT_FOUND("Role not found");

        private String message;

        RegistrationErrorCode(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
