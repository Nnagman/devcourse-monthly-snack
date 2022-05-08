package com.example.monthlysnack.exception;

public class SnackException {
    private SnackException() {

    }

    public static class SnackNotFoundException extends RuntimeException {
        public SnackNotFoundException(String message) {
            super(message);
        }
    }

    public static class SnackNotRegisterException extends RuntimeException {
        public SnackNotRegisterException(String message) {
            super(message);
        }
    }

    public static class SnackNotUpdateException extends RuntimeException {
        public SnackNotUpdateException(String message) {
            super(message);
        }
    }

    public static class SnackNotDeleteException extends RuntimeException {
        public SnackNotDeleteException(String message) {
            super(message);
        }
    }

    public static class SnackCategoryNotFoundException extends RuntimeException {
        public SnackCategoryNotFoundException(String message) {
            super(message);
        }
    }

    public static class SnackCategoryNotRegisterException extends RuntimeException {
        public SnackCategoryNotRegisterException(String message) {
            super(message);
        }
    }

    public static class SnackCategoryNotUpdateException extends RuntimeException {
        public SnackCategoryNotUpdateException(String message) {
            super(message);
        }
    }

    public static class SnackCategoryNotDeleteException extends RuntimeException {
        public SnackCategoryNotDeleteException(String message) {
            super(message);
        }
    }
}
