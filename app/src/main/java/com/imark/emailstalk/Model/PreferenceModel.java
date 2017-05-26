package com.imark.emailstalk.Model;

public class PreferenceModel {

    private String heading;
    private String info;
    boolean checkBox;

    public PreferenceModel(String heading, String info, boolean checkBox) {
        this.heading = heading;
        this.info = info;
        this.checkBox = checkBox;
    }

    public PreferenceModel(String heading) {
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

    public boolean isCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }

}
