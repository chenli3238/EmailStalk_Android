package com.imark.emailstalk.Response;

public class CommonRowResponse {
    String emailTitle, emailStatus,date,reciverName;

    public CommonRowResponse(String emailTitle, String emailStatus, String date, String reciverName) {
        this.emailTitle = emailTitle;
        this.emailStatus = emailStatus;
        this.date = date;
        this.reciverName = reciverName;
    }

    public String getEmailTitle() {
        return emailTitle;
    }

    public void setEmailTitle(String emailTitle) {
        this.emailTitle = emailTitle;
    }

    public String getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(String emailStatus) {
        this.emailStatus = emailStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReciverName() {
        return reciverName;
    }

    public void setReciverName(String reciverName) {
        this.reciverName = reciverName;
    }
}
