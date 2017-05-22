package com.imark.emailstalk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.imark.emailstalk.Adapter.NavigationAdapter;
import com.imark.emailstalk.Infrastructure.AppCommon;
import com.imark.emailstalk.Model.NavigationModel;

import java.util.ArrayList;
import java.util.List;

import API.EmailStalkService;
import API.ServiceGenerator;
import APIResponse.UpdateDeviceTokenResponse;
import CustomControl.SimpleDividerItemDecoration;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {
    @BindView(R.id.recyclerViewNavigation)
    RecyclerView recyclerViewNavigation;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.toolbarText)
    TextView textViewtoolbar;

    @BindView(R.id.left)
    ImageView imageViewLeft;

    @BindView(R.id.right)
    ImageView imageViewRight;

    @BindView(R.id.readBtn)
    ImageView readBtn;

    @BindView(R.id.unreadBtn)
    ImageView unreadBtn;

    @BindView(R.id.allMailBtn)

    ImageView allMailBtn;
    Fragment selectFragment = null;
    private List<NavigationModel> navigationModelArrayList = new ArrayList<>();
    NavigationAdapter navigationAdapter;
    boolean flag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        ButterKnife.bind(this);
        textViewtoolbar.setText(R.string.home);
        navigationAdapter = new NavigationAdapter(navigationModelArrayList, this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewNavigation.setLayoutManager(layoutManager);
        recyclerViewNavigation.setAdapter(navigationAdapter);
         recyclerViewNavigation.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext(), R.drawable.line_divider_navigation));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        setUpNavigationdrawer();
        imageViewRight.setVisibility(View.VISIBLE);
        imageViewRight.setImageResource(R.drawable.notification);
    }

    private void setUpNavigationdrawer() {
        NavigationModel navigationModel = new NavigationModel(R.drawable.home, getResources().getString(R.string.home));
        navigationModelArrayList.add(navigationModel);

        navigationModel = new NavigationModel(R.drawable.account, getResources().getString(R.string.account));
        navigationModelArrayList.add(navigationModel);

        navigationModel = new NavigationModel(R.drawable.how, getResources().getString(R.string.how));
        navigationModelArrayList.add(navigationModel);

        navigationModel = new NavigationModel(R.drawable.prefference, getResources().getString(R.string.preference));
        navigationModelArrayList.add(navigationModel);

        navigationModel = new NavigationModel(R.drawable.faq, getResources().getString(R.string.faq));
        navigationModelArrayList.add(navigationModel);

        navigationModel = new NavigationModel(R.drawable.support, getResources().getString(R.string.support));
        navigationModelArrayList.add(navigationModel);

        navigationModel = new NavigationModel(R.drawable.report, getResources().getString(R.string.report));
        navigationModelArrayList.add(navigationModel);

        navigationModel = new NavigationModel(R.drawable.setting, getResources().getString(R.string.setting));
        navigationModelArrayList.add(navigationModel);

        navigationModel = new NavigationModel(R.drawable.logout, getResources().getString(R.string.logout));
        navigationModelArrayList.add(navigationModel);

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

    @Override
    protected void onPause() {
        super.onPause();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
        }
    }


}
