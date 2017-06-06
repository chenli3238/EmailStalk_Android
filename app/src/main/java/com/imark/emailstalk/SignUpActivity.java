package com.imark.emailstalk;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.imark.emailstalk.Infrastructure.AppCommon;

import java.util.Calendar;
import java.util.TimeZone;

import API.EmailStalkService;
import API.ServiceGenerator;
import APIEntity.RegistrationEntity;
import APIResponse.RegistrationResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends Activity {

    @BindView(R.id.userFNameText)
    EditText userFNameText;

    @BindView(R.id.userLNameText)
    EditText userLNameText;

    @BindView(R.id.emailEditText)
    EditText emailEditText;

    @BindView(R.id.passwordEditText)
    EditText passwordEditText;

    @BindView(R.id.signUpBtn)
    TextView signUpBtn;

    @BindView(R.id.loginBtn)
    TextView loginBtn;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    TimeZone tz;
    private ProgressDialog progress;
    Call<RegistrationResponse> responseModelCall;

    FirebaseInstanceIDService firebaseInstanceIDService = new FirebaseInstanceIDService();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        ButterKnife.bind(this);

        progress = new ProgressDialog(this);
        progress.setMessage(getResources().getString(R.string.please_wait));
        progress.setCancelable(false);
    }

    @OnClick(R.id.loginBtn)
    public void setLoginBtn() {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        finish();
    }

    @OnClick(R.id.signUpBtn)
    public void setSignUpBtn() {
        String fName = userFNameText.getText().toString().trim();
        String lName = userLNameText.getText().toString().trim();
        final String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        if (fName.isEmpty()) {
            userFNameText.setError("First Name must be filled");
        } else if (lName.isEmpty()) {
            userLNameText.setError("Last Name must be filled");
        } else if (email.isEmpty()) {
            emailEditText.setError("Email must be filled");
        } else if (!AppCommon.getInstance(SignUpActivity.this).isEmailValid(email)) {
            emailEditText.setError("Please enter valid Email");
        } else if (password.isEmpty()) {
            passwordEditText.setError("Password must be filled");
        } else {
            Calendar cal = Calendar.getInstance();
            tz = cal.getTimeZone();
            AppCommon.getInstance(this).setNonTouchableFlags(this);
            if (AppCommon.getInstance(this).isConnectingToInternet(this)) {
                progressBar.setVisibility(View.VISIBLE);
                final String token = firebaseInstanceIDService.getDeviceToken();
                RegistrationEntity registrationEntity = new RegistrationEntity(fName, lName, email, tz.getID(), password, token, getResources().getString(R.string.android));
                EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
                responseModelCall = emailStalkService.registrationResponseCall(registrationEntity);
                responseModelCall.enqueue(new Callback<RegistrationResponse>() {
                    @Override
                    public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                        AppCommon.getInstance(SignUpActivity.this).clearNonTouchableFlags(SignUpActivity.this);
                        if (response.code() == 200) {
                            progressBar.setVisibility(View.GONE);
                            int success = response.body().getSuccess();
                            if (success == 1) {
                                int userId = response.body().getRegistrationObject().getUserID();
                                int isVerify = response.body().getRegistrationObject().getIsVerify();
                                int notificationType = response.body().getRegistrationObject().getNotificationType();
                                int isPushNotificationsEnabled = response.body().getRegistrationObject().getIsPushNotificationsEnabled();
                                int isDailyReportEnabled = response.body().getRegistrationObject().getIsDailyReportEnabled();
                                String dailyReportTime = response.body().getRegistrationObject().getDailyReportTime();
                                String userName = response.body().getRegistrationObject().getUserFirstName() + " " + response.body().getRegistrationObject().getUserLastName();
                                String region = response.body().getRegistrationObject().getRegion();
                                String timezone = response.body().getRegistrationObject().getTimezone();
                                AppCommon.getInstance(SignUpActivity.this).savePreferences(isDailyReportEnabled, dailyReportTime);
                                AppCommon.getInstance(SignUpActivity.this).setUserId(userId);
                                AppCommon.getInstance(SignUpActivity.this).setNotificationEnabled(isPushNotificationsEnabled);
                                AppCommon.getInstance(SignUpActivity.this).setTokenId(token);
                                AppCommon.getInstance(SignUpActivity.this).setUserName(userName);
                                AppCommon.getInstance(SignUpActivity.this).setEmail(email);
                                AppCommon.getInstance(SignUpActivity.this).setPrimaryEmail(email);
                                AppCommon.getInstance(SignUpActivity.this).setNotificationType(notificationType);
                                AppCommon.getInstance(SignUpActivity.this).setRegion(region);
                                AppCommon.getInstance(SignUpActivity.this).setTimeZone(timezone);
                                showDialog(response.body().getError());

                            } else {
                                AppCommon.getInstance(SignUpActivity.this).showDialog(SignUpActivity.this, response.body().getError());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                        AppCommon.getInstance(SignUpActivity.this).clearNonTouchableFlags(SignUpActivity.this);
                        progressBar.setVisibility(View.GONE);
                        AppCommon.getInstance(SignUpActivity.this).showDialog(SignUpActivity.this, getResources().getString(R.string.network_error));
                    }
                });
            } else {
                AppCommon.getInstance(SignUpActivity.this).clearNonTouchableFlags(SignUpActivity.this);
                AppCommon.getInstance(this).showDialog(this, getResources().getString(R.string.network_alert));
            }
        }
    }

    public void showDialog(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        setTitle("Registration successful");
        builder.setMessage(error);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                finish();
            }
        });
        builder.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (responseModelCall != null) {
            responseModelCall.cancel();
        }
    }
}
