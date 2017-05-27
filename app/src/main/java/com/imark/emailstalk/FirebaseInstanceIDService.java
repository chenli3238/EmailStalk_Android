package com.imark.emailstalk;

import android.content.Context;
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
    String refreshedToken;
    Context context;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

//        AppCommon.getInstance(context).setTokenId(refreshedToken);
    }

    public String getDeviceToken() {
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        return refreshedToken;
    }

}
