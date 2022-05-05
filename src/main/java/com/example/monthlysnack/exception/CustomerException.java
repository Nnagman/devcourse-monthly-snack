package com.example.monthlysnack.exception;

public class CustomerException {
    private CustomerException() {

    }

    public static class CustomerNotFoundException extends RuntimeException {
        public CustomerNotFoundException(String message) {
            super(message);
        }
    }

    public static class CustomerNotRegisterException extends RuntimeException {
        public CustomerNotRegisterException(String message) {
            super(message);
        }
    }

    public static class CustomerNotUpdateException extends RuntimeException {
        public CustomerNotUpdateException(String message) {
            super(message);
        }
    }
}
