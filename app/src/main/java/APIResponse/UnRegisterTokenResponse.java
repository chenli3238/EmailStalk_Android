package APIResponse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 5/19/2017.
 */

public class UnRegisterTokenResponse {

    @SerializedName("success")
    int success;

    @SerializedName("result")
    String result;

    @SerializedName("error")
    String error;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
