package com.imark.emailstalk;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.imark.emailstalk.Adapter.EmailAdapter;
import com.imark.emailstalk.Adapter.SettingsAdapter;
import com.imark.emailstalk.Infrastructure.AppCommon;
import com.imark.emailstalk.Model.PreferenceModel;

import java.util.ArrayList;
import java.util.List;

import API.EmailStalkService;
import API.ServiceGenerator;
import APIEntity.NotificationEntity;
import APIEntity.TokenEntity;
import APIResponse.CloseAccountResponse;
import APIResponse.LinkedEmailResponse;
import APIResponse.NotificationResponse;
import APIResponse.SecondaryEmailObject;
import APIResponse.UnRegisterTokenResponse;
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

    @BindView(R.id.singleIndicator)
    Button singleIndicator;

    @BindView(R.id.multipleIndicator)
    Button multipleIndicator;

    @BindView(R.id.closeAccount)
    TextView textViewCloseAccount;

    @BindView(R.id.removeSecondary)
    TextView textViewRemoveSecondary;

    @BindView(R.id.logout)
    TextView textViewLogout;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private List<PreferenceModel> preferenceModelList = new ArrayList<>();

    SettingsAdapter settingsAdapter;

    FirebaseInstanceIDService firebaseInstanceIDService = new FirebaseInstanceIDService();

    ProgressDialog progressDialog;

    private List<SecondaryEmailObject> secondaryEmailObjectArrayList = new ArrayList<>();

    EmailAdapter emailAdapter;

    Dialog dialogEmails;

    Call call;

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
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Closing Account...");
        progressDialog.setCancelable(false);
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

//        preferenceModel = new PreferenceModel(getResources().getString(R.string.privacy));
//        preferenceModelList.add(preferenceModel);
//
//        preferenceModel = new PreferenceModel(getResources().getString(R.string.terms_condition));
//        preferenceModelList.add(preferenceModel);

        preferenceModel = new PreferenceModel(getResources().getString(R.string.tutorials));
        preferenceModelList.add(preferenceModel);

        preferenceModel = new PreferenceModel(getResources().getString(R.string.push_notification));
        preferenceModelList.add(preferenceModel);

        int type = AppCommon.getInstance(this).getNotificationType();
        if (type == 1) {
            singleIndicator.setSelected(true);
        } else {
            multipleIndicator.setSelected(true);
        }

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
                startActivity(new Intent(this, TutorialActivity.class));
                break;
//            case 3:
//                startActivity(new Intent(this, TermsConditionActivity.class));
//                break;
//            case 3:
//                //            startActivity(new Intent(this, TermsConditionActivity.class));
//                break;
            case 3:
                if (notificationSwitch.isChecked()) {
                    setPushNotification(0, notificationSwitch);
                } else {
                    setPushNotification(1, notificationSwitch);
                }
                break;
        }
    }

    @OnClick(R.id.singlePush)
    void SingleClick() {
        singleIndicator.setSelected(true);
        multipleIndicator.setSelected(false);
        enablePushNotification(1);
    }

    @OnClick(R.id.multiplePush)
    void MultipleClick() {
        singleIndicator.setSelected(false);
        multipleIndicator.setSelected(true);
        enablePushNotification(2);
    }

    @OnClick(R.id.closeAccount)
    void setTextViewCloseAccount() {
        buttonLogoutCloseAccount(getResources().getString(R.string.close_text), 1);
    }

    @OnClick(R.id.logout)
    void setTextViewLogout() {
        buttonLogoutCloseAccount(getResources().getString(R.string.logout_text), 2);
    }

    @OnClick(R.id.terms)
    void terms() {
        startActivity(new Intent(this, TermsConditionActivity.class));
    }

    @OnClick(R.id.privacy)
    void privacy() {
        startActivity(new Intent(this, PrivacyActivity.class));
    }

    @OnClick(R.id.removeSecondary)
    void setTextViewRemoveSecondary() {
        // custom dialog
        dialogEmails = new Dialog(this);
        dialogEmails.setContentView(R.layout.remove_account_dialog);
        dialogEmails.setTitle(getResources().getString(R.string.app_name));
        final RecyclerView recyclerViewEmail = (RecyclerView) dialogEmails.findViewById(R.id.recyclerViewEmail);
        final ProgressBar progressBar = (ProgressBar) dialogEmails.findViewById(R.id.progressBar);
        final RelativeLayout relativeLayout = (RelativeLayout) dialogEmails.findViewById(R.id.relativeLayoutNoEmail);
        progressBar.setVisibility(View.VISIBLE);
        final LinearLayoutManager layoutManagerEmail = new LinearLayoutManager(this);
        layoutManagerEmail.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewEmail.setLayoutManager(layoutManagerEmail);
        recyclerViewEmail.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext(), R.drawable.line_divider));
        AppCommon.getInstance(this).setNonTouchableFlags(this);
        if (AppCommon.getInstance(this).isConnectingToInternet(this)) {
            int userId = AppCommon.getInstance(this).getUserId();
            final EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
            Call<LinkedEmailResponse> secondaryEmailResponseCall = emailStalkService.getLinkedEmail(userId);
            secondaryEmailResponseCall.enqueue(new Callback<LinkedEmailResponse>() {
                @Override
                public void onResponse(Call<LinkedEmailResponse> call, Response<LinkedEmailResponse> response) {
                    AppCommon.getInstance(SettingActivity.this).clearNonTouchableFlags(SettingActivity.this);
                    progressBar.setVisibility(View.GONE);
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        secondaryEmailObjectArrayList = response.body().getSecondaryEmailObjects();
                        secondaryEmailObjectArrayList.remove(0);
                        if (secondaryEmailObjectArrayList.size() == 0) {
                            relativeLayout.setVisibility(View.VISIBLE);
                        } else {
                            relativeLayout.setVisibility(View.GONE);
                        }
                        emailAdapter = new EmailAdapter(secondaryEmailObjectArrayList, SettingActivity.this);
                        recyclerViewEmail.setAdapter(emailAdapter);
                        emailAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<LinkedEmailResponse> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    AppCommon.getInstance(SettingActivity.this).clearNonTouchableFlags(SettingActivity.this);
//                AppCommon.getInstance(HomeActivity.this).showDialog(HomeActivity.this, "No Network Connection");
                }
            });
            dialogEmails.show();
        } else {
            AppCommon.getInstance(SettingActivity.this).clearNonTouchableFlags(SettingActivity.this);
            AppCommon.getInstance(this).showDialog(this, getResources().getString(R.string.network_alert));
        }
    }

    private void enablePushNotification(final int type) {
        AppCommon.getInstance(this).setNonTouchableFlags(this);
        if (AppCommon.getInstance(this).isConnectingToInternet(this)) {
            progressBar.setVisibility(View.VISIBLE);
            int userId = AppCommon.getInstance(this).getUserId();
            NotificationEntity notificationEntity = new NotificationEntity(userId, type);
            EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
            Call<NotificationResponse> notificationResponseCall = emailStalkService.Notification(notificationEntity);
            notificationResponseCall.enqueue(new Callback<NotificationResponse>() {
                @Override
                public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                    AppCommon.getInstance(SettingActivity.this).clearNonTouchableFlags(SettingActivity.this);
                    int success = response.body().getSuccess();
                    progressBar.setVisibility(View.GONE);
                    if (success == 1) {
                        //  AppCommon.getInstance(SettingActivity.this).showDialog(SettingActivity.this, response.body().getResult());
                        AppCommon.getInstance(SettingActivity.this).setNotificationType(type);
                        AppCommon.getInstance(SettingActivity.this).showDialog(SettingActivity.this, response.body().getResult());
                    } else {
                        AppCommon.getInstance(SettingActivity.this).showDialog(SettingActivity.this, response.body().getError());
                    }
                }

                @Override
                public void onFailure(Call<NotificationResponse> call, Throwable t) {
                    AppCommon.getInstance(SettingActivity.this).clearNonTouchableFlags(SettingActivity.this);
                    progressBar.setVisibility(View.GONE);
                }
            });
        } else {
            progressBar.setVisibility(View.GONE);
            AppCommon.getInstance(SettingActivity.this).clearNonTouchableFlags(SettingActivity.this);
            AppCommon.getInstance(this).showDialog(this, getResources().getString(R.string.network_alert));
        }
    }

    private void setPushNotification(final int type, final Switch notificationSwitch) {
        AppCommon.getInstance(this).setNonTouchableFlags(this);
        if (AppCommon.getInstance(this).isConnectingToInternet(this)) {
            progressBar.setVisibility(View.VISIBLE);
            int userId = AppCommon.getInstance(this).getUserId();
            EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
            Call<NotificationResponse> notificationResponseCall = emailStalkService.enablePushNotification(userId, type);
            notificationResponseCall.enqueue(new Callback<NotificationResponse>() {
                @Override
                public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                    AppCommon.getInstance(SettingActivity.this).clearNonTouchableFlags(SettingActivity.this);
                    progressBar.setVisibility(View.GONE);
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        if (type == 0) {
                            notificationSwitch.setChecked(false);
                        } else {
                            notificationSwitch.setChecked(true);
                        }
                        //  AppCommon.getInstance(SettingActivity.this).showDialog(SettingActivity.this, response.body().getResult());
                        AppCommon.getInstance(SettingActivity.this).setNotificationEnabled(type);
                        AppCommon.getInstance(SettingActivity.this).showDialog(SettingActivity.this, response.body().getResult());

                    } else {
                        AppCommon.getInstance(SettingActivity.this).showDialog(SettingActivity.this, response.body().getError());
                    }
                }

                @Override
                public void onFailure(Call<NotificationResponse> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    AppCommon.getInstance(SettingActivity.this).clearNonTouchableFlags(SettingActivity.this);
                }
            });
        } else {
            progressBar.setVisibility(View.GONE);
            AppCommon.getInstance(SettingActivity.this).clearNonTouchableFlags(SettingActivity.this);
            AppCommon.getInstance(this).showDialog(this, getResources().getString(R.string.network_alert));
        }
    }

    private void CloseAccount(String email, final int i) {
        AppCommon.getInstance(this).setNonTouchableFlags(this);
        if (AppCommon.getInstance(this).isConnectingToInternet(this)) {
            progressBar.setVisibility(View.VISIBLE);
            int userId = AppCommon.getInstance(this).getUserId();
            EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
            call = emailStalkService.closeAccount(userId, email);
            call.enqueue(new Callback<CloseAccountResponse>() {
                @Override
                public void onResponse(Call<CloseAccountResponse> call, Response<CloseAccountResponse> response) {
                    AppCommon.getInstance(SettingActivity.this).clearNonTouchableFlags(SettingActivity.this);
                    progressBar.setVisibility(View.GONE);
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        if (i == 1) {
                            Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            AppCommon.getInstance(SettingActivity.this).clearPreference();
                        } else {
                            dialogEmails.dismiss();
                            AppCommon.getInstance(SettingActivity.this).showDialog(SettingActivity.this, getResources().getString(R.string.secondary_alert));
                        }

                    } else {
                        AppCommon.getInstance(SettingActivity.this).showDialog(SettingActivity.this, response.body().getError());
                    }
                }

                @Override
                public void onFailure(Call<CloseAccountResponse> call, Throwable t) {
                    AppCommon.getInstance(SettingActivity.this).clearNonTouchableFlags(SettingActivity.this);
                    progressBar.setVisibility(View.GONE);
                }
            });
        } else {
            AppCommon.getInstance(SettingActivity.this).clearNonTouchableFlags(SettingActivity.this);
            AppCommon.getInstance(this).showDialog(this, getResources().getString(R.string.network_alert));
        }
    }

    void buttonLogoutCloseAccount(String string, final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(string)
                .setCancelable(false)
                .setIcon(R.drawable.appicon)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (i == 1) {
                            CloseAccount(AppCommon.getInstance(SettingActivity.this).getPrimaryEmail(), i);
                        } else if (i == 2) {
                            callUnRegisterToken();
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


    public void callUnRegisterToken() {
        AppCommon.getInstance(this).setNonTouchableFlags(this);
        if (AppCommon.getInstance(this).isConnectingToInternet(this)) {
            progressBar.setVisibility(View.VISIBLE);
            TokenEntity tokenEntity = new TokenEntity(AppCommon.getInstance(this).getUserId());
            EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
            Call<UnRegisterTokenResponse> call = emailStalkService.unRegisterTokenResponseCall(tokenEntity);
            call.enqueue(new Callback<UnRegisterTokenResponse>() {
                @Override
                public void onResponse(Call<UnRegisterTokenResponse> call, Response<UnRegisterTokenResponse> response) {
                    UnRegisterTokenResponse unRegisterTokenResponse = response.body();
                    AppCommon.getInstance(SettingActivity.this).clearNonTouchableFlags(SettingActivity.this);
                    progressBar.setVisibility(View.GONE);
                    //int success = response.body().getSuccess();
                    if (unRegisterTokenResponse.getSuccess() == 1) {
                        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        AppCommon.getInstance(SettingActivity.this).clearPreference();
                        Log.d("Email", "Updated");
                    } else {
                        //                     AppCommon.getInstance(this).showDialog(activity, response.body().getError());
                    }

                }

                @Override
                public void onFailure(Call<UnRegisterTokenResponse> call, Throwable t) {
                    AppCommon.getInstance(SettingActivity.this).clearNonTouchableFlags(SettingActivity.this);
                    progressBar.setVisibility(View.GONE);
                }
            });

        } else {
            AppCommon.getInstance(SettingActivity.this).clearNonTouchableFlags(SettingActivity.this);
            AppCommon.getInstance(this).showDialog(this, getResources().getString(R.string.network_alert));
        }

    }


    public void setEmailClickAction(int position) {

        String email = secondaryEmailObjectArrayList.get(position).getEmail();
        dialogEmails.dismiss();
        CloseAccount(email, 2);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (call != null) {
            call.cancel();
        }
    }
}
