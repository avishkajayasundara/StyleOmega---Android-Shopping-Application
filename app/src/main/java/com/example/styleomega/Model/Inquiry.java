package com.example.styleomega.Model;

public class Inquiry {
    String id,name,message,subject,email;

    public Inquiry(String id, String name, String message, String subject, String email) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.subject = subject;
        this.email = email;
    }

    public Inquiry() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
