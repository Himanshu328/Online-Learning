package com.sgnr.sgnrclasses.Model;

public class PaidModel {

    private String lesson_ID;
    private String lesson_Name;
    private String CourseNotes;
    private String lesson_VedioLink;

    public PaidModel(String lesson_ID, String lesson_Name, String courseNotes, String lesson_VedioLink) {
        this.lesson_ID = lesson_ID;
        this.lesson_Name = lesson_Name;
        CourseNotes = courseNotes;
        this.lesson_VedioLink = lesson_VedioLink;
    }

    public String getLesson_ID() {
        return lesson_ID;
    }

    public void setLesson_ID(String lesson_ID) {
        this.lesson_ID = lesson_ID;
    }

    public String getLesson_Name() {
        return lesson_Name;
    }

    public void setLesson_Name(String lesson_Name) {
        this.lesson_Name = lesson_Name;
    }

    public String getLesson_VedioLink() {
        return lesson_VedioLink;
    }

    public void setLesson_VedioLink(String lesson_VedioLink) {
        this.lesson_VedioLink = lesson_VedioLink;
    }

    public String getCourseNotes() {
        return CourseNotes;
    }

    public void setCourseNotes(String courseNotes) {
        CourseNotes = courseNotes;
    }
}
