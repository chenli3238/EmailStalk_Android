package com.imark.emailstalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.imark.emailstalk.Adapter.SettingsAdapter;
import com.imark.emailstalk.Infrastructure.AppCommon;
import com.imark.emailstalk.Model.PreferenceModel;

import java.util.ArrayList;
import java.util.List;

import API.EmailStalkService;
import API.ServiceGenerator;
import APIEntity.NotificationEntity;
import APIResponse.NotificationResponse;
import CustomControl.SimpleDividerItemDecoration;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbarText)
    TextView textViewToolbar;

    @BindView(R.id.left)
    ImageView imageViewLeft;

    @BindView(R.id.right)
    ImageView imageViewRight;

    @BindView(R.id.recyclerViewSetting)

    RecyclerView recyclerViewSetting;

    private List<PreferenceModel> preferenceModelList = new ArrayList<>();

    SettingsAdapter settingsAdapter;

    FirebaseInstanceIDService firebaseInstanceIDService = new FirebaseInstanceIDService();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        ButterKnife.bind(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewSetting.setLayoutManager(layoutManager);
        settingsAdapter = new SettingsAdapter(preferenceModelList, this);
        recyclerViewSetting.setAdapter(settingsAdapter);
        recyclerViewSetting.addItemDecoration(new SimpleDividerItemDecoration(this, R.drawable.line_divider));
        setUpEventsData();
        textViewToolbar.setText(R.string.setting);
        imageViewLeft.setVisibility(View.VISIBLE);
        imageViewLeft.setImageResource(R.drawable.left);
    }

    @OnClick(R.id.left)
    void leftButton() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setUpEventsData() {

        PreferenceModel preferenceModel = new PreferenceModel(getResources().getString(R.string.account));
        preferenceModelList.add(preferenceModel);

        preferenceModel = new PreferenceModel(getResources().getString(R.string.change_password));
        preferenceModelList.add(preferenceModel);

        preferenceModel = new PreferenceModel(getResources().getString(R.string.privacy));
        preferenceModelList.add(preferenceModel);

        preferenceModel = new PreferenceModel(getResources().getString(R.string.terms_condition));
        preferenceModelList.add(preferenceModel);

        preferenceModel = new PreferenceModel(getResources().getString(R.string.tutorials));
        preferenceModelList.add(preferenceModel);

        preferenceModel = new PreferenceModel(getResources().getString(R.string.push_notification));
        preferenceModelList.add(preferenceModel);

        preferenceModel = new PreferenceModel(getResources().getString(R.string.single_notification));
        preferenceModelList.add(preferenceModel);

        preferenceModel = new PreferenceModel(getResources().getString(R.string.multiple_notification));
        preferenceModelList.add(preferenceModel);
    }

    public void setClickAction(int position, Switch notificationSwitch) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, AccountsActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, ChangePassword.class));
                break;
            case 2:
                startActivity(new Intent(this, PrivacyActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, TermsConditionActivity.class));
                break;
            case 4:
    //            startActivity(new Intent(this, TermsConditionActivity.class));
                break;
            case 5:
                if (notificationSwitch.isChecked()) {
                    final String token = firebaseInstanceIDService.getDeviceToken();
                    AppCommon.getInstance(this).callUpdateTokenAPI(token, AppCommon.getInstance(this).getUserId());
                } else {
                    AppCommon.getInstance(this).callUnRegisterToken();
                }
                break;
            case 6:
                //      enablePushNotification(1);
                break;
            case 7:
                //     enablePushNotification(2);
                break;
        }
    }

    private void enablePushNotification(int type) {
        int userId = AppCommon.getInstance(this).getUserId();
        NotificationEntity notificationEntity = new NotificationEntity(userId, type);
        EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
        Call<NotificationResponse> notificationResponseCall = emailStalkService.Notification(notificationEntity);
        notificationResponseCall.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                int success = response.body().getSuccess();
                if (success == 1) {
                  //  AppCommon.getInstance(SettingActivity.this).showDialog(SettingActivity.this, response.body().getResult());

                } else {
                    AppCommon.getInstance(SettingActivity.this).showDialog(SettingActivity.this, response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {

            }
        });
    }
}
