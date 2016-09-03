package org.carrot.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by xuan on 16/8/25.
 */
@Entity
public class WishUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String email;
    public String password;
    public String firstName;
    public String lastName;
    public String fullName;
    public String address;
    public String country;
    public String city;
    public String zipCode;
    public String telephoneNumber;
    public String creditCard;
    public String securityCode;
    public String expirationDate;
    public String billingZipCode;
    public Byte hasSignUp;
    public String signUpIp;
    public Date createTime;
    public Date updateTime;

    public WishUser() {

    }

    public WishUser(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
