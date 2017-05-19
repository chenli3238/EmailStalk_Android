package APIResponse;

import com.google.gson.annotations.SerializedName;

public class RegistrationResponse {

    @SerializedName("success")
    int success;

    @SerializedName("result")
    RegistrationObject registrationObject;

    @SerializedName("error")
    String error;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public RegistrationObject getRegistrationObject() {
        return registrationObject;
    }

    public void setRegistrationObject(RegistrationObject registrationObject) {
        this.registrationObject = registrationObject;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
