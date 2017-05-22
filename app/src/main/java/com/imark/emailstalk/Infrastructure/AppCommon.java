package com.imark.emailstalk.Infrastructure;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.imark.emailstalk.Interface.MYPerference;
import com.imark.emailstalk.R;

/**
 * Created by User on 5/18/2017.
 */

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
    public void setUserId(String userId) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(MYPerference.USER_ID, userId);
        mEditor.apply();
    }

    public String getUserId() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getString(MYPerference.USER_ID, "");
    }

    public void setTokenId(String tokenId) {
       SharedPreferences mSharedPreferences = mContext.getSharedPreferences("TokenId",Context.MODE_APPEND);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(MYPerference.tokenId, tokenId);
        mEditor.apply();
        /*SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(MYPerference.tokenId, tokenId);
        mEditor.apply();*/
    }

    public String getTokenId() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getString(MYPerference.tokenId, "");
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


}
