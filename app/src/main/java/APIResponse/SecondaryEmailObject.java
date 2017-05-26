package APIResponse;


import com.google.gson.annotations.SerializedName;

public class SecondaryEmailObject {

    @SerializedName("email")
    String email;
    @SerializedName("verify")
    int verify;

    public SecondaryEmailObject(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public int getVerify() {
        return verify;
    }

    public void setVerify(int verify) {
        this.verify = verify;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
