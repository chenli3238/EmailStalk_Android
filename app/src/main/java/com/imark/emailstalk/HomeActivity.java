package com.imark.emailstalk;

import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imark.emailstalk.Adapter.EmailAdapter;
import com.imark.emailstalk.Adapter.NavigationAdapter;
import com.imark.emailstalk.Infrastructure.AppCommon;
import com.imark.emailstalk.Model.NavigationModel;

import java.util.ArrayList;
import java.util.List;

import API.EmailStalkService;
import API.ServiceGenerator;
import APIEntity.TokenEntity;
import APIResponse.LinkedEmailResponse;
import APIResponse.SecondaryEmailObject;
import APIResponse.UnRegisterTokenResponse;
import CustomControl.SimpleDividerItemDecoration;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.recyclerViewNavigation)
    RecyclerView recyclerViewNavigation;

    @BindView(R.id.recyclerViewEmail)
    RecyclerView recyclerViewEmail;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.toolbarText)
    TextView textViewtoolbar;

    @BindView(R.id.userName)
    TextView textViewuserName;

    @BindView(R.id.email)
    TextView textViewemail;

    @BindView(R.id.left)
    ImageView imageViewLeft;

    @BindView(R.id.upArrow)
    TextView upArrow;

    @BindView(R.id.downArrow)
    TextView downArrow;

    @BindView(R.id.right)
    ImageView imageViewRight;

    @BindView(R.id.readBtn)
    ImageView readBtn;

    @BindView(R.id.unreadBtn)
    ImageView unreadBtn;

    @BindView(R.id.allMailBtn)
    ImageView allMailBtn;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshEmail;

    Fragment selectFragment = null;

    private List<NavigationModel> navigationModelArrayList = new ArrayList<>();

    NavigationAdapter navigationAdapter;

    private List<SecondaryEmailObject> secondaryEmailResponseList = new ArrayList<>();

    EmailAdapter emailAdapter;

    FirebaseInstanceIDService firebaseInstanceIDService = new FirebaseInstanceIDService();

    Call<LinkedEmailResponse> secondaryEmailResponseCall;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        ButterKnife.bind(this);
        textViewtoolbar.setText(R.string.home);
        //firebaseInstanceIDService.onTokenRefresh();
        navigationAdapter = new NavigationAdapter(navigationModelArrayList, this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewNavigation.setLayoutManager(layoutManager);
        recyclerViewNavigation.setAdapter(navigationAdapter);
        recyclerViewNavigation.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext(), R.drawable.line_divider_navigation));

        final LinearLayoutManager layoutManagerEmail = new LinearLayoutManager(this);
        layoutManagerEmail.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewEmail.setLayoutManager(layoutManagerEmail);
        recyclerViewEmail.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext(), R.drawable.line_divider_navigation));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        setUpNavigationdrawer();
//        imageViewRight.setVisibility(View.VISIBLE);
        imageViewRight.setImageResource(R.drawable.notification);
        String email = AppCommon.getInstance(this).getEmail();

        textViewemail.setText(email);
        setReadBtn();
        getAllEmail();
        swipeRefreshEmail.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllEmail();
            }
        });
        swipeRefreshEmail.setColorSchemeResources(R.color.colorPrimary);
    }


    private void getAllEmail() {
        AppCommon.getInstance(this).setNonTouchableFlags(this);
        if (AppCommon.getInstance(this).isConnectingToInternet(this)) {
        int userId = AppCommon.getInstance(this).getUserId();
        final EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
            secondaryEmailResponseCall = emailStalkService.getLinkedEmail(userId);
        secondaryEmailResponseCall.enqueue(new Callback<LinkedEmailResponse>() {
            @Override
            public void onResponse(Call<LinkedEmailResponse> call, Response<LinkedEmailResponse> response) {
                AppCommon.getInstance(HomeActivity.this).clearNonTouchableFlags(HomeActivity.this);
                int success = response.body().getSuccess();
                if (success == 1) {
                    secondaryEmailResponseList = response.body().getSecondaryEmailObjects();
                    SecondaryEmailObject secondaryEmailObject = new SecondaryEmailObject("Add Email");
                    secondaryEmailResponseList.add(secondaryEmailResponseList.size(), secondaryEmailObject);
                    emailAdapter = new EmailAdapter(secondaryEmailResponseList, HomeActivity.this);
                    recyclerViewEmail.setAdapter(emailAdapter);
                    emailAdapter.notifyDataSetChanged();
                    swipeRefreshEmail.setRefreshing(false);
                } else {
                    swipeRefreshEmail.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<LinkedEmailResponse> call, Throwable t) {
                swipeRefreshEmail.setRefreshing(false);
                AppCommon.getInstance(HomeActivity.this).clearNonTouchableFlags(HomeActivity.this);
//                AppCommon.getInstance(HomeActivity.this).showDialog(HomeActivity.this, "No Network Connection");
            }
        });
        } else {
            AppCommon.getInstance(HomeActivity.this).clearNonTouchableFlags(HomeActivity.this);
    }
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

    @OnClick(R.id.right)
    void Right() {
        startActivity(new Intent(this, NotificationActivity.class));
    }

    @OnClick(R.id.relativeLayoutEmail)
    void relativeLayoutEmail() {
        if (recyclerViewEmail.getVisibility() == View.VISIBLE) {
            recyclerViewEmail.setVisibility(View.GONE);
            swipeRefreshEmail.setVisibility(View.GONE);
            recyclerViewNavigation.setVisibility(View.VISIBLE);
            downArrow.setVisibility(View.VISIBLE);
            upArrow.setVisibility(View.GONE);

        } else {
            recyclerViewEmail.setVisibility(View.VISIBLE);
            swipeRefreshEmail.setVisibility(View.VISIBLE);
            recyclerViewNavigation.setVisibility(View.GONE);
            downArrow.setVisibility(View.GONE);
            upArrow.setVisibility(View.VISIBLE);
        }
    }

//    @OnClick(R.id.addEmail)
//    void addEmail() {
//
//    }

    @OnClick(R.id.readBtn)
    public void setReadBtn() {
        AppCommon.getInstance(this).btn_click(readBtn);
        AppCommon.getInstance(this).btn_click1(unreadBtn, allMailBtn);
        selectFragment = new ReadFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        //  transaction.addToBackStack(AllMailFragment.class.getName());
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
        //   transaction.addToBackStack(ReadFragment.class.getName());
        transaction.commit();
    }

    @OnClick(R.id.allMailBtn)
    public void setAllMailBtn() {
        AppCommon.getInstance(this).btn_click(allMailBtn);
        AppCommon.getInstance(this).btn_click1(unreadBtn, readBtn);

        selectFragment = new AllMailFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        // transaction.addToBackStack(AllMailFragment.class.getName());
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

    @Override
    protected void onResume() {
        super.onResume();
        String userName = AppCommon.getInstance(this).getUserName();
        textViewuserName.setText(userName);
    }

    public void setClickAction(int position) {
        switch (position) {
            case 0:
                getAllEmail();
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                break;
            case 1:
                startActivity(new Intent(this, AccountsActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, HowItWorksActivity.class));
                break;
            case 3:
                Intent intentArr = new Intent(this, PreferenceActivity.class);
                startActivity(intentArr);
                break;
            case 4:
                Intent intentNet = new Intent(this, FAQ.class);
                startActivity(intentNet);
                break;
            case 5:
//                    this.startActivity(new Intent(this, AccountsActivity.class));
                break;
            case 6:
                startActivity(new Intent(this, ReportaBugActivity.class));
                break;
            case 7:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case 8:
                buttonLogout();
                break;
        }
    }

    void buttonLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.logout_text)
                .setCancelable(false)
                .setIcon(R.drawable.appicon)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        boolean flag = AppCommon.getInstance(HomeActivity.this).callUnRegisterToken();
                        if (flag) {
                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            AppCommon.getInstance(HomeActivity.this).clearPreference();
                        } else {
                            dialog.cancel();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle(this.getResources().getString(R.string.app_name));
        alert.show();
    }



    public void setEmailClickAction(int position) {
        if (position == secondaryEmailResponseList.size() - 1) {
            startActivity(new Intent(HomeActivity.this, AddEmailActivity.class));
        } else {
            int verify = secondaryEmailResponseList.get(position).getVerify();
            if (verify == 0) {
                AppCommon.getInstance(this).showDialog(this, "Please Verify your Email");
            } else {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                textViewemail.setText(secondaryEmailResponseList.get(position).getEmail());
                AppCommon.getInstance(this).setEmail(secondaryEmailResponseList.get(position).getEmail());
                setReadBtn();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (secondaryEmailResponseCall != null) {
            secondaryEmailResponseCall.cancel();
        }
    }
}
