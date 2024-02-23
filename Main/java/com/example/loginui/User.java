package com.example.loginui;

public class User {
    private final String email;
    private final String password;
    private final String firstname;
    private final String lastname;

    // Constructor, getters, and setters
    public User(String email, String password, String firstname, String lastname) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
}
