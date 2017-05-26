package APIResponse;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("success")
    int success;
    @SerializedName("result")
    LoginObject loginObject;
    @SerializedName("error")
    String error;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public LoginObject getLoginObject() {
        return loginObject;
    }

    public void setLoginObject(LoginObject loginObject) {
        this.loginObject = loginObject;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
