package com.example.monthlysnack.message;

public class ErrorMessage {
    private ErrorMessage() {

    }

    public static final String CUSTOMER_NOT_FOUND
            = "고객 정보를 찾을 수 없습니다.";

    public static final String CUSTOMER_NOT_REGISTER
            = "등록에 실패했습니다. 다시 시도 해주세요.";

    public static final String CUSTOMER_NOT_UPDATE
            = "수정에 실패했습니다. 다시 시도 해주세요.";
}
