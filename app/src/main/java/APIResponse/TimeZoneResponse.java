package APIResponse;

import android.widget.ArrayAdapter;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TimeZoneResponse {

    @SerializedName("success")
    int success;

    @SerializedName("result")
    ArrayList<String> timeZoneList;

    @SerializedName("error")
    String error;

    public ArrayList<String> getTimeZoneList() {
        return timeZoneList;
    }

    public void setTimeZoneList(ArrayList<String> timeZoneList) {
        this.timeZoneList = timeZoneList;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
