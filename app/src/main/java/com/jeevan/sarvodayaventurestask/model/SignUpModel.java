package com.jeevan.sarvodayaventurestask.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

@Entity
public class SignUpModel {

    @ColumnInfo(name = "name")
    String name;

    @NotNull
    @PrimaryKey
    String phoneNumber;

    @ColumnInfo(name = "email")
    String email;

    @ColumnInfo(name = "gender")
    String gender;

    @ColumnInfo(name = "password")
    String password;

    public SignUpModel() {
    }

    public SignUpModel(String name, String phoneNumber, String email, String gender, String password) {

        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.password = password;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NonNull String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
