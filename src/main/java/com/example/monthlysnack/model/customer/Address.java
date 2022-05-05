package com.example.monthlysnack.model.customer;

import org.springframework.util.Assert;

import java.util.Objects;

public class Address {
    private final String address;

    public Address(String address) {
        Assert.notNull(address, "주소는 null이 될 수 없습니다.");
        Assert.isTrue(address.length() > 100,
                "주소는 100자리 이하여야 합니다.");
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return address.equals(address1.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    public String getAddress() {
        return address;
    }
}
