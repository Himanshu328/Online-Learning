package com.sgnr.sgnrclasses.Model;

public class McqModel {

    private String Question;
    private String Option_1;
    private String Option_2;
    private String Option_3;
    private String Option_4;
    private String Answer;
    private String Amount;
    private String CourseName;

    public McqModel(String question, String option_1, String option_2, String option_3, String option_4, String answer, String amount, String courseName) {
        Question = question;
        Option_1 = option_1;
        Option_2 = option_2;
        Option_3 = option_3;
        Option_4 = option_4;
        Answer = answer;
        Amount = amount;
        CourseName = courseName;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getOption_1() {
        return Option_1;
    }

    public void setOption_1(String option_1) {
        Option_1 = option_1;
    }

    public String getOption_2() {
        return Option_2;
    }

    public void setOption_2(String option_2) {
        Option_2 = option_2;
    }

    public String getOption_3() {
        return Option_3;
    }

    public void setOption_3(String option_3) {
        Option_3 = option_3;
    }

    public String getOption_4() {
        return Option_4;
    }

    public void setOption_4(String option_4) {
        Option_4 = option_4;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }
}
