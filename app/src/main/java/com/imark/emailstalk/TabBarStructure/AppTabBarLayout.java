package com.imark.emailstalk.TabBarStructure;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.imark.emailstalk.R;

/**
 * Created by User on 5/18/2017.
 */

public class AppTabBarLayout extends TabLayout {
    private int[] tabIcons = {
           // R.drawable.read_tab_state
    };

    public AppTabBarLayout(Context context) {
        super(context);
    }

    public AppTabBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setTabsFromPagerAdapter(PagerAdapter adapter) {
        this.removeAllTabs();
        int i = 0;
        for (int count = adapter.getCount(); i < count; ++i) {
            LinearLayout layout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.custom_tabiconlayout, null);
            ImageView iconImageView = (ImageView) layout.findViewById(R.id.tabIcon);
            iconImageView.setBackgroundResource(tabIcons[i]);
            this.addTab(this.newTab().setCustomView(layout));
        }
    }


    public AppTabBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
