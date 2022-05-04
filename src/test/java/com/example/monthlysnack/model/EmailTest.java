package com.example.monthlysnack.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

class EmailTest {
    @Test
    @DisplayName("같은 값을 가진 Email 객체가 서로 같은지 확인")
    void checkEquals() {
        Email email = new Email("nnagman@gmail.com");
        assertThat(email, is(new Email("nnagman@gmail.com")));
    }

    @Test
    @DisplayName("같은 값을 가진 Email 객체는 같은 hashcode를 가진다.")
    void checkHashCodeSame() {
        Email email1 = new Email("nnagman@gmail.com");
        Email email2 = new Email("nnagman@gmail.com");
        int hashCode1 = email1.hashCode();
        int hashCode2 = email2.hashCode();

        assertThat(hashCode1, is(hashCode2));
    }

    @Test
    @DisplayName("값은 값을 가진 Email 객체는 HashMap의 Key값도 같다.")
    void checkHashMapKeySame() {
        Email email = new Email("nnagman@kakao.com");
        Map<Email, String> hs = new HashMap<>();
        hs.put(email, "email");
        hs.put(new Email("nnagman@kakao.com"), "hi");
        hs.put(new Email("nnagman@kakao.com"), "test");

        assertThat(hs.size(), is(1));
        assertThat(hs.get(email), is("test"));
    }

    @Test
    @DisplayName("email 주소 형식이 틀리다면 생성 불가")
    void checkAddress() {
        assertThrows(IllegalArgumentException.class, () -> new Email(null));
        assertThrows(IllegalArgumentException.class, () -> new Email("asdasdaa"));
        assertThrows(IllegalArgumentException.class, () -> new Email("a@a"));
        assertThrows(IllegalArgumentException.class, () -> new Email("aa.com"));
        assertThrows(IllegalArgumentException.class, () ->
                new Email("sadasdasdasdasdasdasdasda@sadasdasdasdasdasdasdasdasadasdasdasdasdasdasdasda.com"));
    }
}