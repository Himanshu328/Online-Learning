package com.sgnr.sgnrclasses.Model;

import java.util.List;

public class McqModelResponse {

    private String status;
    private String message;
    private List<McqModel> data;

    public McqModelResponse(String status, String message, List<McqModel> data) {
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

    public List<McqModel> getData() {
        return data;
    }

    public void setData(List<McqModel> data) {
        this.data = data;
    }
}
