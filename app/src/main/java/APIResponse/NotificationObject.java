package APIResponse;

public class NotificationObject {

    private String info;
    private String status;

    public NotificationObject(String info, String status) {
        this.info = info;
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public String getStatus() {
        return status;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
