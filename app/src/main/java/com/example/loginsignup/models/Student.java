package com.example.loginsignup.models;

public class Student {

    private  String email;
    private  String phone;

    // Constructors
    public  Student() {
//        this.email = "";
//        this.phone = "";
    }
    public Student(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }
    // Getters
    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

   // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
