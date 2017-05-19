package APIEntity;

import com.google.gson.annotations.SerializedName;


public class LoginEntity {
    @SerializedName("emailID")
    String emailID;

    @SerializedName("password")
    String password;

    @SerializedName("tokenID")
    String tokenID;

    @SerializedName("deviceType")
    String deviceType;

    public LoginEntity(String emailID, String password, String tokenID, String deviceType) {
        this.emailID = emailID;
        this.password = password;
        this.tokenID = tokenID;
        this.deviceType = deviceType;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
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
