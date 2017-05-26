package APIResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationResponse {
    @SerializedName("success")
    int success;

    @SerializedName("result")
    List<NotificationObject> notificationObjectList;

    @SerializedName("error")
    String error;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<NotificationObject> getNotificationObjectList() {
        return notificationObjectList;
    }

    public void setNotificationObjectList(List<NotificationObject> notificationObjectList) {
        this.notificationObjectList = notificationObjectList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
