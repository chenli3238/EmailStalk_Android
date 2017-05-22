package APIEntity;

import com.google.gson.annotations.SerializedName;
import com.imark.emailstalk.ForgotPassword;

public class ForgotPasswordEntity  {
    @SerializedName("emailID")
    String emailID;

    public ForgotPasswordEntity(String emailID){
        this.emailID = emailID;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

}
