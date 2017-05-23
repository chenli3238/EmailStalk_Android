package APIEntity;

import com.google.gson.annotations.SerializedName;

public class ProfileEntity {

    @SerializedName("userID")
    int userId;
    @SerializedName("firstName")
    String firstName;
    @SerializedName("lastName")
    String lastName;
    @SerializedName("region")
    String region;
    @SerializedName("timeZone")
    String timeZone;

    public ProfileEntity(int userId, String firstName,String lastName, String region, String timeZone){
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.region = region;
        this.timeZone = timeZone;
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRegion() {
        return region;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
