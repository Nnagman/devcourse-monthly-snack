package com.example.monthlysnack.model;

import org.springframework.util.Assert;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email {
    private final String address;

    public Email(String address) {
        Assert.notNull(address, "주소는 null이 될 수 없습니다.");
        Assert.isTrue(address.length() >= 4 && address.length() <= 50,
                "주소는 5자리에서 50자리 사이여야 합니다.");
        Assert.isTrue(checkAddress(address), "유효하지 않은 이메일 주소입니다.");
        this.address = address;
    }

    private static boolean checkAddress(String address) {
        return Pattern.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", address);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return address.equals(email.address);
    }

    @Override
    public int hashCode() {

        return Objects.hash(address);
    }

    public String getAddress() {
        return address;
    }
}