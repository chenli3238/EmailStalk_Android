package APIEntity;

import com.google.gson.annotations.SerializedName;

public class NotificationEntity {

    @SerializedName("userID")
    int userid;
    @SerializedName("status")
    boolean status;
    @SerializedName("type")
    int type;

    public NotificationEntity(int userid, boolean status) {
        this.userid = userid;
        this.status = status;
    }

    public NotificationEntity(int userid, int type) {
        this.userid = userid;
        this.type = type;
    }

    public Boolean getStatus() {
        return status;
    }

    public int getType() {
        return type;
    }

    public int getUserid() {
        return userid;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
