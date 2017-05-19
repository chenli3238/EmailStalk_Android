package com.imark.emailstalk;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.imark.emailstalk.Infrastructure.AppCommon;

import API.EmailStalkService;
import API.ServiceGenerator;
import APIResponse.UpdateDeviceTokenResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        callUpdateDeviceToken(refreshedToken);
    }

    private void callUpdateDeviceToken(String refreshedToken) {
        if(AppCommon.getInstance(this).isConnectingToInternet(this)){
            EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
            Call call = emailStalkService.updateDeviceTokenCall(AppCommon.getInstance(this).getUserId(),refreshedToken);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    UpdateDeviceTokenResponse updateDeviceTokenResponse = (UpdateDeviceTokenResponse) response.body();
                    if(updateDeviceTokenResponse.getSuccess() == 1){

                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {

                }
            });
        }
    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
    }
}
