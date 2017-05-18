package com.imark.emailstalk.Model;

public class NavigationModel {

    private int NavImage;
    private String navTitle;

    public NavigationModel(int imageView, String string){
        this.NavImage = imageView;
        this.navTitle = string;
    }

    public int getNavImage() {
        return NavImage;
    }

    public String getNavTitle() {
        return navTitle;
    }

    public void setNavImage(int navImage) {
        NavImage = navImage;
    }

    public void setNavTitle(String navTitle) {
        this.navTitle = navTitle;
    }
}
