package APIResponse;

import com.google.gson.annotations.SerializedName;

public class LoginObject {
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

    @SerializedName("notificationType")
    int notificationType;

    @SerializedName("isDailyReportEnabled")
    int isDailyReportEnabled;

    @SerializedName("dailyReportTime")
    String dailyReportTime;

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


    public int getIsDailyReportEnabled() {
        return isDailyReportEnabled;
    }

    public int getIsVerify() {
        return isVerify;
    }

    public int getNotificationType() {
        return notificationType;
    }

    public void setIsDailyReportEnabled(int isDailyReportEnabled) {
        this.isDailyReportEnabled = isDailyReportEnabled;
    }

    public void setIsVerify(int isVerify) {
        this.isVerify = isVerify;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }

    public String getDailyReportTime() {
        return dailyReportTime;
    }

    public void setDailyReportTime(String dailyReportTime) {
        this.dailyReportTime = dailyReportTime;
    }

}
