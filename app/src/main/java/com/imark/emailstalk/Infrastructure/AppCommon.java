package com.imark.emailstalk.Infrastructure;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.Preference;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.imark.emailstalk.Interface.MYPerference;
import com.imark.emailstalk.LoginActivity;
import com.imark.emailstalk.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import API.EmailStalkService;
import API.ServiceGenerator;
import APIEntity.TokenEntity;
import APIResponse.UnRegisterTokenResponse;
import APIResponse.UpdateDeviceTokenResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class AppCommon {
    public static AppCommon mInstance = null;
    static Context mContext;

    public static AppCommon getInstance(Context _Context) {
        if (mInstance == null) {
            mInstance = new AppCommon();
        }
        mContext = _Context;
        return mInstance;
    }

    public static void btn_click(ImageView textView) {
        textView.setSelected(true);
    }

    public void btn_click1(ImageView textView1, ImageView textView2) {
        textView1.setSelected(false);
        textView2.setSelected(false);
    }

    public void setUserId(int userId) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(MYPerference.mUserLogin, MODE_PRIVATE).edit();
        editor.putBoolean(MYPerference.Login, true);
        editor.putInt(MYPerference.userId, userId);
        editor.apply();
    }

    public int getUserId() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mUserLogin, MODE_PRIVATE);
        return mSharedPreferences.getInt(MYPerference.userId, 0);
    }

    public void setTokenId(String tokenId) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(MYPerference.mDeviceToken, MODE_PRIVATE).edit();
        editor.putString(MYPerference.tokenId, tokenId);
        editor.apply();

    }

    public void clearPreference() {
        SharedPreferences.Editor editorLogin = mContext.getSharedPreferences(MYPerference.mUserLogin, MODE_PRIVATE).edit();
        editorLogin.clear();
        editorLogin.apply();
        SharedPreferences.Editor editor = mContext.getSharedPreferences(MYPerference.mDeviceToken, MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        SharedPreferences.Editor editorPreferences = mContext.getSharedPreferences(MYPerference.mPreferences, MODE_PRIVATE).edit();
        editorPreferences.clear();
        editorPreferences.apply();
    }

    public String getTokenId() {
        SharedPreferences prefs = mContext.getSharedPreferences(MYPerference.mDeviceToken, MODE_PRIVATE);
        String token = prefs.getString(MYPerference.tokenId, null);
        return token;
    }

    public boolean isUserLogIn() {
        SharedPreferences prefs = mContext.getSharedPreferences(MYPerference.mUserLogin, MODE_PRIVATE);
        return prefs.getBoolean("login", false);
    }

    public boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public void showDialog(final Activity mActivity, String error) {
        if (!mActivity.isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setCancelable(false);
            builder.setTitle(error);
            builder.setIcon(R.drawable.appicon);
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }

    }

    public boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public void savePreferences(int isDailyReportEnabled, String dailyReportTime) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(MYPerference.mPreferences, MODE_PRIVATE).edit();
        editor.putInt(MYPerference.isDailyReportEnabled, isDailyReportEnabled);
        editor.putString(MYPerference.dailyReportTime, dailyReportTime);
        editor.apply();
    }


    public void setNotificationEnabled(int isPushNotificationsEnabled) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(MYPerference.mPreferences, MODE_PRIVATE).edit();
        editor.putInt(MYPerference.isPushNotificationsEnabled, isPushNotificationsEnabled);
        editor.apply();
    }

    public void setNotificationType(int notificationType) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(MYPerference.mPreferences, MODE_PRIVATE).edit();
        editor.putInt(MYPerference.notificationType, notificationType);
        editor.apply();

    }

    public int getNotificationType() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPreferences, MODE_PRIVATE);
        return mSharedPreferences.getInt(MYPerference.notificationType, 0);
    }

    public int getpushNotification() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPreferences, MODE_PRIVATE);
        return mSharedPreferences.getInt(MYPerference.isPushNotificationsEnabled, 0);
    }

    public int getDailyReport() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPreferences, MODE_PRIVATE);
        return mSharedPreferences.getInt(MYPerference.isDailyReportEnabled, 0);
    }

    public String getDailyReportTime() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPreferences, MODE_PRIVATE);
        return mSharedPreferences.getString(MYPerference.dailyReportTime, null);
    }

    public String getUserName(){
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mUserLogin, MODE_PRIVATE);
        return mSharedPreferences.getString(MYPerference.userName, null);
    }

    public String getEmail(){
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mUserLogin, MODE_PRIVATE);
        return mSharedPreferences.getString(MYPerference.email, null);
    }

    public String getRegion() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPreferences, MODE_PRIVATE);
        return mSharedPreferences.getString(MYPerference.region, null);
    }

    public String getTimezone() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPreferences, MODE_PRIVATE);
        return mSharedPreferences.getString(MYPerference.timezone, null);
    }

    public void setUserName(String userName) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(MYPerference.mUserLogin, MODE_PRIVATE).edit();
        editor.putString(MYPerference.userName, userName);
        editor.apply();
    }

    public void setEmail(String email) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(MYPerference.mUserLogin, MODE_PRIVATE).edit();
        editor.putString(MYPerference.email, email);
        editor.apply();
    }

    public void setRegion(String region) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(MYPerference.mPreferences, MODE_PRIVATE).edit();
        editor.putString(MYPerference.region, region);
        editor.apply();
    }

    public void setTimeZone(String timezone) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(MYPerference.mPreferences, MODE_PRIVATE).edit();
        editor.putString(MYPerference.timezone, timezone);
        editor.apply();
    }

    public boolean callUnRegisterToken() {
        if (isConnectingToInternet(mContext)) {
            TokenEntity tokenEntity = new TokenEntity(getUserId());
            EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
            Call<UnRegisterTokenResponse> call = emailStalkService.unRegisterTokenResponseCall(tokenEntity);
            call.enqueue(new Callback<UnRegisterTokenResponse>() {
                @Override
                public void onResponse(Call<UnRegisterTokenResponse> call, Response<UnRegisterTokenResponse> response) {
                    UnRegisterTokenResponse unRegisterTokenResponse = response.body();
                    //int success = response.body().getSuccess();
                    if (unRegisterTokenResponse.getSuccess() == 1) {
                        Log.d("Email", "Updated");
                    } else {
                        //                     AppCommon.getInstance(this).showDialog(activity, response.body().getError());
                    }

                }

                @Override
                public void onFailure(Call<UnRegisterTokenResponse> call, Throwable t) {

                }
            });
            return true;
        } else {
            return false;
        }

    }

    public void callUpdateTokenAPI(String refreshedToken, int userId) {

        if (isConnectingToInternet(mContext)) {
            TokenEntity tokenEntity = new TokenEntity(userId, refreshedToken);
            EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
            Call<UpdateDeviceTokenResponse> call = emailStalkService.updateDeviceTokenCall(tokenEntity);
            call.enqueue(new Callback<UpdateDeviceTokenResponse>() {
                @Override
                public void onResponse(Call<UpdateDeviceTokenResponse> call, Response<UpdateDeviceTokenResponse> response) {
                    UpdateDeviceTokenResponse updateDeviceTokenResponse = response.body();
                    //int success = response.body().getSuccess();
                    if (updateDeviceTokenResponse.getSuccess() == 1) {
                        Log.d("Email", "Updated");
                    } else {
                    }

                }

                @Override
                public void onFailure(Call<UpdateDeviceTokenResponse> call, Throwable t) {

                }
            });
        }
    }

    public void updateRefreshToken(String refreshedToken) {
        if(isUserLogIn()){
           if(getTokenId().equals(refreshedToken)){

           }else{
               updateRefreshToken(refreshedToken);
           }
        }
    }
}
