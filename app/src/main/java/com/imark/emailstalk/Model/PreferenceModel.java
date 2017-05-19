package com.imark.emailstalk.Model;

public class PreferenceModel {

    private String heading;
    private String info;

    public PreferenceModel(String heading, String info){
        this.heading = heading;
        this.info = info;
    }

    public  PreferenceModel(String heading){
        this.heading = heading;
    }

    public String getHeading() {
        return heading;
    }

    public String getInfo() {
        return info;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
