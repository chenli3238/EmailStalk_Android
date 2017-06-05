package APIEntity;

import com.google.android.gms.common.stats.StatsEvent;
import com.google.gson.annotations.SerializedName;

import java.util.TimeZone;

public class RegistrationEntity {
    @SerializedName("first_name")
    String first_name;

    @SerializedName("last_name")
    String last_name;

    @SerializedName("emailID")
    String emailID;

    @SerializedName("timezone")
    String timezone;

    @SerializedName("password")
    String password;

    @SerializedName("tokenID")
    String tokenID;

    @SerializedName("deviceType")
    String deviceType;

    public RegistrationEntity(String first_name, String last_name, String emailID, String timezone, String password, String tokenID, String deviceType) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.emailID = emailID;
        this.timezone = timezone;
        this.password = password;
        this.tokenID = tokenID;
        this.deviceType = deviceType;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTokenID() {
        return tokenID;
    }

    public void setTokenID(String tokenID) {
        this.tokenID = tokenID;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
