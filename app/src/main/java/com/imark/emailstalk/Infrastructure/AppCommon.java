package com.imark.emailstalk.Infrastructure;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

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


}
