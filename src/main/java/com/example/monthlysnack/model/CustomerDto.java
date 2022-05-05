package com.example.monthlysnack.model;

import com.example.monthlysnack.model.customer.Address;
import com.example.monthlysnack.model.customer.Email;
import com.example.monthlysnack.model.customer.Name;
import com.example.monthlysnack.model.customer.Postcode;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerDto {
    private CustomerDto() {

    }

    static public class RegisterCustomer {
        private final String name;
        private final String email;
        private final String address;
        private final String postCode;

        public RegisterCustomer(String name, String emailAddress,
                                String address, String postCode) {
            this.name = new Name(name).getName();
            this.email = new Email(emailAddress).getEmail();
            this.address = new Address(address).getAddress();
            this.postCode = new Postcode(postCode).getPostcode();
        }

        public Customer getCustomer() {
            var now = LocalDateTime.now();
            return new Customer(UUID.randomUUID(),
                    name, email, address, postCode, now, now);
        }
    }

    public record UpdateCustomer(UUID customerId, String name,
                                 String address, String postCode) {
        public UpdateCustomer(UUID customerId, String name,
                              String address, String postCode) {
            this.customerId = customerId;
            this.name = new Name(name).getName();
            this.address = new Address(address).getAddress();
            this.postCode = new Postcode(postCode).getPostcode();
        }
    }
}
