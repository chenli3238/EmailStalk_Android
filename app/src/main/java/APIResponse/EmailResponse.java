package APIResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmailResponse {

    @SerializedName("success")
    int success;

    @SerializedName("result")
    List<EmailObject> emailObjectList;

    @SerializedName("error")
    String error;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<EmailObject> getEmailObjectList() {
        return emailObjectList;
    }

    public void setEmailObjectList(List<EmailObject> emailObjectList) {
        this.emailObjectList = emailObjectList;
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
