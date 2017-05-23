package APIResponse;


import com.google.gson.annotations.SerializedName;

public class EmailObject {

    @SerializedName("emailID")
    String emailID;
    @SerializedName("emailTitle")
    String emailTitle;
    @SerializedName("emailRead")
    String emailRead;
    @SerializedName("dateTime")
    String dateTime;

}
