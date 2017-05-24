package APIEntity;

import com.google.gson.annotations.SerializedName;

public class EmailEntity {
    @SerializedName("userID")
    private int userId;
    @SerializedName("type")
    private int type;
    @SerializedName("count")
    private int count;
    @SerializedName("emailID")
    private String emailId;

    public EmailEntity(int userId, int type, int count, String emailId) {
        this.userId = userId;
        this.type = type;
        this.count = count;
        this.emailId = emailId;
    }

    public EmailEntity(int userId,String emailId){
        this.userId = userId;
        this.emailId = emailId;
    }

    public String getEmailId() {
        return emailId;
    }

    public int getUserId() {
        return userId;
    }

    public int getType() {
        return type;
    }

    public int getCount() {
        return count;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
