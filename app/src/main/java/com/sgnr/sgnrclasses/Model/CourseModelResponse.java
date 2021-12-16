package com.sgnr.sgnrclasses.Model;

import java.util.List;

public class CourseModelResponse {

    private String status;
    private String message;
    private List<CourseModel> data;


    public CourseModelResponse(String status, String message, List<CourseModel> data) {
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

    public List<CourseModel> getData() {
        return data;
    }

    public void setData(List<CourseModel> data) {
        this.data = data;
    }
}
