package com.example.schoolpartner.gson;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by q on 2017/4/21.
 */
public class Person extends DataSupport {
    @SerializedName("id")
    private String number;

    private String name;
    private String password;
    private Integer credit;
    private String signature;
    private String phoneNumber;
    private String mager;
    private String gragde;


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMager() {
        return mager;
    }

    public void setMager(String mager) {
        this.mager = mager;
    }

    public String getGragde() {
        return gragde;
    }

    public void setGragde(String gragde) {
        this.gragde = gragde;
    }

    @Override
    public String toString() {
        return "Person{" +
                "number=" + number +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", credit=" + credit +
                ", signature='" + signature + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", mager='" + mager + '\'' +
                ", gragde='" + gragde + '\'' +
                '}';
    }
}
