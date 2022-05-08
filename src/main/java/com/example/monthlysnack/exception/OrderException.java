package com.example.monthlysnack.exception;

public class OrderException {
    private OrderException() {

    }

    public static class OrderNotFoundException extends RuntimeException {
        public OrderNotFoundException(String message) {
            super(message);
        }
    }

    public static class OrderNotRegisterException extends RuntimeException {
        public OrderNotRegisterException(String message) {
            super(message);
        }
    }

    public static class OrderNotUpdateException extends RuntimeException {
        public OrderNotUpdateException(String message) {
            super(message);
        }
    }

    public static class OrderNotDeleteException extends RuntimeException {
        public OrderNotDeleteException(String message) {
            super(message);
        }
    }

    public static class OrderItemNotDeleteException extends RuntimeException {
        public OrderItemNotDeleteException(String message) {
            super(message);
        }
    }
}
