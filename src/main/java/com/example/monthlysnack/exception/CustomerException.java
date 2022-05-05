package com.example.monthlysnack.exception;

public class CustomerException {
    private CustomerException() {

    }

    public static class CustomerNotFoundException extends RuntimeException {
        public CustomerNotFoundException(String message) {
            super(message);
        }
    }
}
