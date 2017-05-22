package APIEntity;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordEntity {

    @SerializedName("userID")
    int userId;
    @SerializedName("previousPassword")
    String previousPassword;
    @SerializedName("newPassword")
    String newPassword;

    public ChangePasswordEntity(int userId,String previousPassword,String newPassword){
        this.userId = userId;
        this.previousPassword = previousPassword;
        this.newPassword = newPassword;
    }

    public int getUserId() {
        return userId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getPreviousPassword() {
        return previousPassword;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setPreviousPassword(String previousPassword) {
        this.previousPassword = previousPassword;
    }
}
