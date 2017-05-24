package APIEntity;

import com.google.gson.annotations.SerializedName;

public class NotificationEntity {

    @SerializedName("userID")
    int userid;
    @SerializedName("type")
    int type;

    public NotificationEntity(int userid, int type) {
        this.userid = userid;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public int getUserid() {
        return userid;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
