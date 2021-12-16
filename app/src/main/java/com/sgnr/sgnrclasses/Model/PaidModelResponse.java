package com.sgnr.sgnrclasses.Model;

import java.util.List;

public class PaidModelResponse {

    private String status;
    private String message;
    private List<PaidModel> data;

    public PaidModelResponse(String status, String message, List<PaidModel> data) {
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

    public List<PaidModel> getData() {
        return data;
    }

    public void setData(List<PaidModel> data) {
        this.data = data;
    }
}
