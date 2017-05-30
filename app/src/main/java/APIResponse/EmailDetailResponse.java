package APIResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Imark on 5/30/2017.
 */

public class EmailDetailResponse {

    @SerializedName("success")
    int success;

    @SerializedName("result")
    EmailObject emailObject;

    @SerializedName("error")
    String error;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public EmailObject getEmailObject() {
        return emailObject;
    }

    public void setEmailObject(EmailObject emailObject) {
        this.emailObject = emailObject;
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
