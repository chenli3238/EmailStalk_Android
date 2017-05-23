package APIEntity;

public class EmailEntity {
    int userId;
    int type;
    int count;

    public EmailEntity(int userId, int type, int count){
        this.userId = userId;
        this.type = type;
        this.count = count;
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
}
