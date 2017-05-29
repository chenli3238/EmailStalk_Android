package APIEntity;

import com.google.gson.annotations.SerializedName;
import com.imark.emailstalk.PreferenceActivity;

public class PreferencesEntity {

    @SerializedName("userID")
    int userId;
    @SerializedName("onEmailNotifications")
    int onEmailNotifications;
    @SerializedName("onDailyReport")
    int onDailyReport;
    @SerializedName("deliveryTime")
    String deliveryTime;

    public PreferencesEntity(int userId, int onDailyReport, String deliveryTime){
        this.userId = userId;
        this.onDailyReport = onDailyReport;
        this.deliveryTime = deliveryTime;
    }

    public int getUserId() {
        return userId;
    }

    public int getOnDailyReport() {
        return onDailyReport;
    }

    public int getOnEmailNotifications() {
        return onEmailNotifications;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setOnDailyReport(int onDailyReport) {
        this.onDailyReport = onDailyReport;
    }

    public void setOnEmailNotifications(int onEmailNotifications) {
        this.onEmailNotifications = onEmailNotifications;
    }

}
