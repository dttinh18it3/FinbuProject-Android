package com.example.finbuproject.models;

import java.sql.Date;

public class user {
    private int User_ID;
    private String User_Name;
    private int Gender;
    private Date User_DoB;
    private String Cellphone;
    private String Address;
    private String Email;
    private String Password;
    private Date Regis_Date;

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int gender) {
        Gender = gender;
    }

    public Date getUser_DoB() {
        return User_DoB;
    }

    public void setUser_DoB(Date user_DoB) {
        User_DoB = user_DoB;
    }

    public String getCellphone() {
        return Cellphone;
    }

    public void setCellphone(String cellphone) {
        Cellphone = cellphone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Date getRegis_Date() {
        return Regis_Date;
    }

    public void setRegis_Date(Date regis_Date) {
        Regis_Date = regis_Date;
    }
}
