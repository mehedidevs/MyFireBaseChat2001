package com.seu.myfirebasechat2001.Model;

public class User {

     String id;
     String mail;
     String password;
     String profile_img;
     String username;

    public User(String id, String mail, String password, String profile_img, String username) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.profile_img = profile_img;
        this.username = username;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(String profile_img) {
        this.profile_img = profile_img;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
