package com.example.monthlysnack.model.customer;

import org.springframework.util.Assert;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email {
    private final String email;

    public Email(String email) {
        Assert.notNull(email, "주소는 null이 될 수 없습니다.");
        Assert.isTrue(email.length() >= 4 && email.length() <= 50,
                "주소는 5자리에서 50자리 사이여야 합니다.");
        Assert.isTrue(checkAddress(email), "유효하지 않은 이메일 주소입니다.");
        this.email = email;
    }

    private static boolean checkAddress(String address) {
        return Pattern.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", address);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return this.email.equals(email.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public String getEmail() {
        return email;
    }
}