package com.sgnr.sgnrclasses.Model;

import java.util.List;

public class ProfileResponseModel {

    private String status;
    private String message;
    private List<ProfileModel> data;

    public ProfileResponseModel(String status, String message, List<ProfileModel> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ProfileModel> getData() {
        return data;
    }

    public void setData(List<ProfileModel> data) {
        this.data = data;
    }
}
