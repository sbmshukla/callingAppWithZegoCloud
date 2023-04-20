package com.example.myapplication;

public class UserModel {
    String mobileNumber;
    String emailAddress;
    String userPassword;

    public UserModel() {
    }

    public UserModel(String mobileNumber, String emailAddress, String userPassword) {
        this.mobileNumber = mobileNumber;
        this.emailAddress = emailAddress;
        this.userPassword = userPassword;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
