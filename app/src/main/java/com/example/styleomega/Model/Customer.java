package com.example.styleomega.Model;

import com.orm.SugarRecord;

import java.util.List;

public class Customer extends SugarRecord<Customer> {
    private String fname,lname,email,contact,username,password;
    private String imageUrl="";
    public Customer(){

    }

    public Customer(String fname, String lname, String email, String contact, String username, String password) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.contact = contact;
        this.username = username;
        this.password = password;
    }

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }




    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }










}
