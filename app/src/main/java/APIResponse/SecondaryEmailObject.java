package APIResponse;


import com.google.gson.annotations.SerializedName;

public class SecondaryEmailObject {

    @SerializedName("email")
    String email;
    @SerializedName("verify")
    String varify;

    public String getEmail() {
        return email;
    }

    public String getVarify() {
        return varify;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setVarify(String varify) {
        this.varify = varify;
    }
}
