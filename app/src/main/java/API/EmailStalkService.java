package API;

import APIEntity.ChangePasswordEntity;
import APIEntity.EmailEntity;
import APIEntity.ForgotPasswordEntity;
import APIEntity.LoginEntity;
import APIEntity.NotificationEntity;
import APIEntity.PreferencesEntity;
import APIEntity.ProfileEntity;
import APIEntity.RegistrationEntity;
import APIEntity.TokenEntity;
import APIResponse.EmailDetailResponse;
import APIResponse.EmailResponse;
import APIResponse.LinkedEmailResponse;
import APIResponse.NotificationResponse;
import APIResponse.PasswordResponse;
import APIResponse.LoginResponse;
import APIResponse.PreferenceResponse;
import APIResponse.ProfileResponse;
import APIResponse.RegistrationResponse;
import APIResponse.SecondaryEmailResponse;
import APIResponse.TimeZoneResponse;
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
    Call<EmailResponse> getListOfEmails(
            @Query("userID") int userId, @Query("type") int type, @Query("offset") int offset, @Query("emailID") String emailID
    );

    @POST("setProfile/")
    Call<ProfileResponse> setProfile(
            @Body ProfileEntity profileEntity
    );

    @POST("savePreferences/")
    Call<PreferenceResponse> savePreferences(
            @Body PreferencesEntity preferencesEntity
    );

    @POST("Notification/")
    Call<NotificationResponse> Notification(
            @Body NotificationEntity notificationEntity
    );

    @POST("enablePushNotification/")
    Call<NotificationResponse> enablePushNotification(
            @Query("userID") int userId, @Query("Status") int type
    );

    @POST("addNewEmailAccount/")
    Call<SecondaryEmailResponse> addNewEmailAccount(
            @Body EmailEntity emailEntity
    );

    @GET("getLinkedEmail/")
    Call<LinkedEmailResponse> getLinkedEmail(
            @Query("userID") int userId
    );

    @GET("getListIOFTimeZone/")
    Call<TimeZoneResponse> getListIOFTimeZone(
            @Query("userID") int userId
    );

    @GET("getEmailDetail/")
    Call<EmailDetailResponse> getEmailDetail(
            @Query("userID") int userId, @Query("messageID") String messageId
    );
}
