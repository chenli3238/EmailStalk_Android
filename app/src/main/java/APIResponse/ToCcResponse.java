package APIResponse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Imark on 5/25/2017.
 */

public class ToCcResponse {

    @SerializedName("userName")
    String userName;
    @SerializedName("emailID")
    String emailID;
    @SerializedName("EmailOpen")
    int EmailOpen;
    @SerializedName("LastEmailOpen")
    String LastEmailOpen;

    public String getEmailID() {
        return emailID;
    }

    public int getEmailOpen() {
        return EmailOpen;
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

    public void setEmailOpen(int emailOpen) {
        EmailOpen = emailOpen;
    }

    public void setLastEmailOpen(String lastEmailOpen) {
        LastEmailOpen = lastEmailOpen;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
