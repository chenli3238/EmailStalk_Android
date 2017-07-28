package com.imark.emailstalk;

import android.app.Application;

import com.zendesk.sdk.network.impl.ZendeskConfig;


public class EmailStalk extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ZendeskConfig.INSTANCE.init(this, getResources().getString(R.string.zendesk_url),
                getResources().getString(R.string.zendesk_appid), getResources().getString(R.string.zendesk_clientid));
      //  initUrbanAirship();
    }
//    private void initUrbanAirship() {
//
//        // Initialize Urban Airship
//        UAirship.takeOff(this);
//
//        // Enable push notification
//        UAirship.shared().getPushManager().setUserNotificationsEnabled(true);
//
//        // 'ic_chat_bubble_outline_black_24dp' should be displayed as notification icon
//        final DefaultNotificationFactory defaultNotificationFactory = new DefaultNotificationFactory(getApplicationContext());
//        defaultNotificationFactory.setSmallIconId(R.drawable.appicon);
//        UAirship.shared().getPushManager().setNotificationFactory(defaultNotificationFactory);
//    }

}
