package APIResponse;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmailObject {

    @SerializedName("emailID")
    int emailID;
    @SerializedName("emailTitle")
    String emailTitle;
    @SerializedName("emailRead")
    int emailRead;
    @SerializedName("dateTime")
    String dateTime;
    @SerializedName("isRead")
    int isRead;
    @SerializedName("to")
    List<ToCcResponse> toResponses;
    @SerializedName("cc")
    List<ToCcResponse> ccResponses;

    public int getEmailID() {
        return emailID;
    }

    public int getEmailRead() {
        return emailRead;
    }

    public int getIsRead() {
        return isRead;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getEmailTitle() {
        return emailTitle;
    }

    public void setEmailID(int emailID) {
        this.emailID = emailID;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setEmailRead(int emailRead) {
        this.emailRead = emailRead;
    }

    public void setEmailTitle(String emailTitle) {
        this.emailTitle = emailTitle;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public List<ToCcResponse> getToResponses() {
        return toResponses;
    }

    public void setToResponses(List<ToCcResponse> toResponses) {
        this.toResponses = toResponses;
    }

    public List<ToCcResponse> getCcResponses() {
        return ccResponses;
    }

    public void setCcResponses(List<ToCcResponse> ccResponses) {
        this.ccResponses = ccResponses;
    }

}

