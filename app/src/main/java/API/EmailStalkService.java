package API;

import APIEntity.ChangePasswordEntity;
import APIEntity.ForgotPasswordEntity;
import APIEntity.LoginEntity;
import APIEntity.NotificationEntity;
import APIEntity.ProfileEntity;
import APIEntity.RegistrationEntity;
import APIEntity.TokenEntity;
import APIResponse.NotificationResponse;
import APIResponse.PasswordResponse;
import APIResponse.LoginResponse;
import APIResponse.ProfileResponse;
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

    @POST("updateDeviceToken/")
    Call<UpdateDeviceTokenResponse> updateDeviceTokenCall(
            @Body TokenEntity tokenEntity
    );

    @POST("unRegisterToken/")
    Call<UnRegisterTokenResponse> unRegisterTokenResponseCall(
            @Body TokenEntity tokenEntity
    );

    @POST("forgotPassword/")
    Call<PasswordResponse> forgotPasswordCall(
            @Body ForgotPasswordEntity forgotPasswordEntity
    );

    @POST("changePassword/")
    Call<PasswordResponse> changePasswordCall(
            @Body ChangePasswordEntity changePasswordEntity
    );

    @POST("getListOfEmails/")
    Call<PasswordResponse> getListOfEmails(
            @Body ChangePasswordEntity changePasswordEntity
    );

    @POST("setProfile/")
    Call<ProfileResponse> setProfile(
            @Body ProfileEntity profileEntity
            );

    @POST("enablePushNotification/")
    Call<NotificationResponse> enablePushNotification(
            @Body NotificationEntity notificationEntity
    );

    @POST("Notification/")
    Call<NotificationResponse> Notification(
            @Body NotificationEntity notificationEntity
    );
}
