package APIResponse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 5/19/2017.
 */

public class RegistrationObject {
    @SerializedName("userID")
    int userID;

   @SerializedName("userFirstName")
    String userFirstName;

    @SerializedName("userLastName")
    String userLastName;

    @SerializedName("isPushNotificationsEnabled")
    int isPushNotificationsEnabled;

    @SerializedName("isVerify")
    int isVerify;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public int getIsPushNotificationsEnabled() {
        return isPushNotificationsEnabled;
    }

    public void setIsPushNotificationsEnabled(int isPushNotificationsEnabled) {
        this.isPushNotificationsEnabled = isPushNotificationsEnabled;
    }

    public int getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(int isVerify) {
        this.isVerify = isVerify;
    }
}
