package com.sgnr.sgnrclasses.Model;

import com.google.gson.annotations.SerializedName;

public class DemoModel {

    @SerializedName("Demo_ID")
    public String demo_ID;
    @SerializedName("Demo_Name")
    public String demo_Name;
    @SerializedName("Demo_Description")
    public String demo_Description;
    @SerializedName("Demo_Img")
    public String demo_Img;
    @SerializedName("Demo_Vedios")
    public String demo_Vedios;

    public DemoModel(String demo_ID, String demo_Name, String demo_Description, String demo_Img, String demo_Vedios) {
        this.demo_ID = demo_ID;
        this.demo_Name = demo_Name;
        this.demo_Description = demo_Description;
        this.demo_Img = demo_Img;
        this.demo_Vedios = demo_Vedios;
    }

    public String getDemo_ID() {
        return demo_ID;
    }

    public void setDemo_ID(String demo_ID) {
        this.demo_ID = demo_ID;
    }

    public String getDemo_Name() {
        return demo_Name;
    }

    public void setDemo_Name(String demo_Name) {
        this.demo_Name = demo_Name;
    }

    public String getDemo_Description() {
        return demo_Description;
    }

    public void setDemo_Description(String demo_Description) {
        this.demo_Description = demo_Description;
    }

    public String getDemo_Img() {
        return demo_Img;
    }

    public void setDemo_Img(String demo_Img) {
        this.demo_Img = demo_Img;
    }

    public String getDemo_Vedios() {
        return demo_Vedios;
    }

    public void setDemo_Vedios(String demo_Vedios) {
        this.demo_Vedios = demo_Vedios;
    }
}
