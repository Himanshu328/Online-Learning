package com.sgnr.sgnrclasses.Model;

public class ProfileModel {

    private String Username;
    private String Email;
    private String Mobile_no;
    private int ID;

    public ProfileModel(String username, String email, String mobile_no, int ID) {
        Username = username;
        Email = email;
        Mobile_no = mobile_no;
        this.ID = ID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile_no() {
        return Mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        Mobile_no = mobile_no;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
