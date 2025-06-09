package edu.xda.doan1.model;

public class User {
    private int id;
    private String username;
    private String password;

    public User(){

    }

    public User(String s, String s1) {
        this.username = s;
        this.password = s1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
