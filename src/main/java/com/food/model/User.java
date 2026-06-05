package com.food.model;

public class User 
{
    private int    id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String role;

    public User() {}

    public User(int id, String name, String email, String password,
                String phone, String address, String role) 
    {
        this.id       = id;
        this.name     = name;
        this.email    = email;
        this.password = password;
        this.phone    = phone;
        this.address  = address;
        this.role     = role;
    }

    // Getters
    public int    getId()       { return id; }
    public String getName()     { return name; }
    public String getEmail()    { return email; }
    public String getPassword() { return password; }
    public String getPhone()    { return phone; }
    public String getAddress()  { return address; }
    public String getRole()     { return role; }

    // Setters
    public void setId(int id)             { this.id       = id; }
    public void setName(String name)      { this.name     = name; }
    public void setEmail(String email)    { this.email    = email; }
    public void setPassword(String pw)    { this.password = pw; }
    public void setPhone(String phone)    { this.phone    = phone; }
    public void setAddress(String addr)   { this.address  = addr; }
    public void setRole(String role)      { this.role     = role; }
}
