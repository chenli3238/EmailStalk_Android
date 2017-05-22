package APIEntity;

import com.google.gson.annotations.SerializedName;

public class TokenEntity {
    @SerializedName("userID")
    int userId;
    @SerializedName("tokenID")
    String tokenId;

    public TokenEntity(int userId,String tokenId){
        this.userId = userId;
        this.tokenId = tokenId;
    }
    public TokenEntity(int userId){
        this.userId = userId;
    }
    public int getUserId() {
        return userId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
