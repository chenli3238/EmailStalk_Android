package com.imark.emailstalk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.imark.emailstalk.Infrastructure.AppCommon;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by User on 5/18/2017.
 */

public class Home extends FragmentActivity {

    @BindView(R.id.readBtn)
    ImageView readBtn;

    @BindView(R.id.unreadBtn)
    ImageView unreadBtn;

    @BindView(R.id.allMailBtn)
    ImageView allMailBtn;
    Fragment selectFragment = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_fragment);
        ButterKnife.bind(this);
        setAllMailBtn();
    }

    @OnClick(R.id.readBtn)
    public void setReadBtn() {
        AppCommon.getInstance(this).btn_click(readBtn);
       /* if (readBtn.isSelected()) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(readBtn.getLayoutParams());
            // readBtn.setPadding(0,0,0,0);
            lp.setMargins(0, 0, 0, 0);

        } else {
            unreadBtn.setPadding(0, 10, 0, 0);
            allMailBtn.setPadding(0, 10, 0, 0);
        }*/
        AppCommon.getInstance(this).btn_click1(unreadBtn, allMailBtn);
        selectFragment = new ReadFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(AllMail.class.getName());
        transaction.commit();

    }


    @OnClick(R.id.unreadBtn)
    public void setUnreadBtn() {
        AppCommon.getInstance(this).btn_click(unreadBtn);

        AppCommon.getInstance(this).btn_click1(readBtn, allMailBtn);

        selectFragment = new UnReadFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(ReadFragment.class.getName());
        transaction.commit();

    }

    @OnClick(R.id.allMailBtn)
    public void setAllMailBtn() {
        AppCommon.getInstance(this).btn_click(allMailBtn);
       /* if (allMailBtn.isSelected()) {
            allMailBtn.setPadding(0, 0, 0, 0);
        } else {
            unreadBtn.setPadding(0, 10, 0, 0);
            readBtn.setPadding(0, 10, 0, 0);
        }*/
        AppCommon.getInstance(this).btn_click1(unreadBtn, readBtn);

        selectFragment = new AllMail();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(AllMail.class.getName());
        transaction.commit();

    }
}
