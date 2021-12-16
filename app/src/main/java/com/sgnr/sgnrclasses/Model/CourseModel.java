package com.sgnr.sgnrclasses.Model;

public class CourseModel {

    private String CourseName;
    private String CourseImage;
    private String CoursePrice;

    public CourseModel(String courseName, String courseImage, String coursePrice) {
        CourseName = courseName;
        CourseImage = courseImage;
        CoursePrice = coursePrice;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getCourseImage() {
        return CourseImage;
    }

    public void setCourseImage(String courseImage) {
        CourseImage = courseImage;
    }

    public String getCoursePrice() {
        return CoursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        CoursePrice = coursePrice;
    }
}
