package com.example.monthlysnack.model.customer;

import org.springframework.util.Assert;

import java.util.Objects;

public class Postcode {
    private final String postcode;

    public Postcode(String postcode) {
        Assert.notNull(postcode, "우편번호는 null이 될 수 없습니다.");
        Assert.isTrue(postcode.length() != 5,
                "우편번호는 5자리여야 합니다.");
        this.postcode = postcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Postcode postcode1 = (Postcode) o;
        return postcode.equals(postcode1.postcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postcode);
    }

    public String getPostcode() {
        return postcode;
    }
}
