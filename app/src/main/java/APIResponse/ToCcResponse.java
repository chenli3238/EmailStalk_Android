package APIResponse;

import com.google.gson.annotations.SerializedName;

public class ToCcResponse {

    @SerializedName("userName")
    String userName;
    @SerializedName("emailID")
    String emailID;
    @SerializedName("EmailOpen")
    String EmailOpen;
    @SerializedName("LastEmailOpen")
    String LastEmailOpen;

    public ToCcResponse(String userName, String emailID, String emailOpen, String lastEmailOpen) {
        this.userName = userName;
        this.emailID = emailID;
        this.EmailOpen = emailOpen;
        this.LastEmailOpen = lastEmailOpen;
    }

    public String getEmailID() {
        return emailID;
    }


    public String getLastEmailOpen() {
        return LastEmailOpen;
    }

    public String getUserName() {
        return userName;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getEmailOpen() {
        return EmailOpen;
    }

    public void setEmailOpen(String emailOpen) {
        EmailOpen = emailOpen;
    }


    public void setLastEmailOpen(String lastEmailOpen) {
        LastEmailOpen = lastEmailOpen;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
