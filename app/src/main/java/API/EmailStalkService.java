package API;

import APIEntity.LoginEntity;
import APIEntity.RegistrationEntity;
import APIResponse.LoginResponse;
import APIResponse.RegistrationResponse;
import APIResponse.UnRegisterTokenResponse;
import APIResponse.UpdateDeviceTokenResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface EmailStalkService {

    @POST("Login/")
    Call<LoginResponse> loginResponseCall(
            @Body LoginEntity loginEntity
    );

    @POST("Register/")
    Call<RegistrationResponse> registrationResponseCall(
            @Body RegistrationEntity registrationEntity
    );

    @GET("updateDeviceToken")
    Call<UpdateDeviceTokenResponse> updateDeviceTokenCall(
            @Query("userID") String userId,
            @Query("tokenID") String tokenId
    );

    @GET("")
    Call<UnRegisterTokenResponse> unRegisterTokenResponseCall(
            @Query("userId") String userId
    );

//    @GET("forgotPassword")
//    Call<ForgotPasswordResponse> forgotPasswordTokenCall(
//            @Body RegistrationEntity registrationEntity
//    );
}
