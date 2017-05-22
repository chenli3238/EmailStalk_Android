package com.imark.emailstalk.Infrastructure;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.Preference;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.imark.emailstalk.Interface.MYPerference;
import com.imark.emailstalk.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        //textView.setTextColor(mContext.getResources().getColor(R.color.white));
    }

    public void btn_click1(ImageView textView1, ImageView textView2) {
        textView1.setSelected(false);
        textView2.setSelected(false);
        //textView1.setTextColor(mContext.getResources().getColor(R.color.gray_font));
      //  textView2.setTextColor(mContext.getResources().getColor(R.color.gray_font));
    }
    public void setUserId(int userId) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(MYPerference.mUserLogin, MODE_PRIVATE).edit();
        editor.putBoolean(MYPerference.Login, true);
        editor.putInt(MYPerference.userId, userId);
        editor.apply();
    }

    public int getUserId() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mUserLogin, MODE_PRIVATE);
        return mSharedPreferences.getInt(MYPerference.userId,0);
    }

    public void setTokenId(String tokenId) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(MYPerference.mDeviceToken, MODE_PRIVATE).edit();
        editor.putString(MYPerference.tokenId, tokenId);
        editor.apply();

    }

    public void clearPreference(){
        SharedPreferences.Editor editorLogin = mContext.getSharedPreferences(MYPerference.mUserLogin, MODE_PRIVATE).edit();
        editorLogin.clear();
        editorLogin.apply();
        SharedPreferences.Editor editor = mContext.getSharedPreferences(MYPerference.mDeviceToken, MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }

    public String getTokenId() {
        SharedPreferences prefs = mContext.getSharedPreferences(MYPerference.mDeviceToken, MODE_PRIVATE);
        String token = prefs.getString(MYPerference.tokenId, null);
        return token;
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
    public void showDialog(Activity mActivity, String error) {
        if (!mActivity.isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setCancelable(false);
            builder.setTitle(error);
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }

    }
    public static boolean isEmailValid(String email) {
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

}
