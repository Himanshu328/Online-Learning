package com.sgnr.sgnrclasses.Model;

import java.util.List;

public class DemoModelResponse {

    private String status;
    private String message;
    private List<DemoModel> data;

    public DemoModelResponse(String status, String message, List<DemoModel> data) {
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

    public List<DemoModel> getData() {
        return data;
    }

    public void setData(List<DemoModel> data) {
        this.data = data;
    }
}
