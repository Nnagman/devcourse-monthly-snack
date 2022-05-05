package com.example.monthlysnack.model.customer;

import org.springframework.util.Assert;

import java.util.Objects;

public class Name {
    private final String name;

    public Name(String name) {
        Assert.notNull(name, "이름은 null이 될 수 없습니다.");
        Assert.isTrue(name.length() <= 50,
                "이름은 50자리 이하여야 합니다.");
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name1 = (Name) o;
        return name.equals(name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }
}
