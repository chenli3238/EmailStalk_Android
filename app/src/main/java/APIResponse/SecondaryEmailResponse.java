package APIResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SecondaryEmailResponse {
    @SerializedName("success")
    int success;

    @SerializedName("result")
    String result;

    @SerializedName("result")
    List<SecondaryEmailObject> secondaryEmailObjects;

    @SerializedName("error")
    String error;

    public List<SecondaryEmailObject> getSecondaryEmailObjects() {
        return secondaryEmailObjects;
    }

    public void setSecondaryEmailObjects(List<SecondaryEmailObject> secondaryEmailObjects) {
        this.secondaryEmailObjects = secondaryEmailObjects;
    }


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
