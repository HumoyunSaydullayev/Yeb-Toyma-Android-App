package com.example.yebtoyma.Model;

public class User {
    String User_id,User_name,User_phone;

    public User() {
    }

    public User(String user_id, String user_name, String user_phone) {
        User_id = user_id;
        User_name = user_name;
        User_phone = user_phone;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getUser_phone() {
        return User_phone;
    }

    public void setUser_phone(String user_phone) {
        User_phone = user_phone;
    }
}
