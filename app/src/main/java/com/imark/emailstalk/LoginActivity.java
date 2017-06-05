package com.imark.emailstalk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.imark.emailstalk.Infrastructure.AppCommon;

import API.EmailStalkService;
import API.ServiceGenerator;
import APIEntity.LoginEntity;
import APIEntity.TokenEntity;
import APIResponse.LoginResponse;
import APIResponse.UpdateDeviceTokenResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

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

    @BindView(R.id.forgotPasswordTextView)
    TextView forgotPasswordTextView;

    private ProgressDialog progress;
    FirebaseInstanceIDService firebaseInstanceIDService = new FirebaseInstanceIDService();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        progress = new ProgressDialog(this);
        progress.setMessage(getResources().getString(R.string.authenticating));
        progress.setCancelable(false);
    }

    @OnClick(R.id.signUpBtn)
    public void setSignUpBtn() {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        finish();
    }

    @OnClick(R.id.forgotPasswordTextView)
    public void setResetPassword() {
        startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
    }

    @OnClick(R.id.loginBtn)
    public void setLoginBtn() {
        final String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        if (email.isEmpty()) {
            emailEditText.setError("Email must be filled");
        } else if (!AppCommon.getInstance(LoginActivity.this).isEmailValid(email)) {
            emailEditText.setError("Please enter valid Email");
        } else if (password.isEmpty()) {
            passwordEditText.setError("Password must be filled");
        } else {
          progressBar.setVisibility(View.VISIBLE);
            final String token = firebaseInstanceIDService.getDeviceToken();
            LoginEntity loginEntity = new LoginEntity(email, password, token, getResources().getString(R.string.android));
            EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
            Call<LoginResponse> responseModelCall = emailStalkService.loginResponseCall(loginEntity);
            responseModelCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.code() == 200) {
                        progressBar.setVisibility(View.GONE);
                        int success = response.body().getSuccess();
                        if (success == 1) {
                            int userId = response.body().getLoginObject().getUserID();
                            int isVerify = response.body().getLoginObject().getIsVerify();
                            int notificationType = response.body().getLoginObject().getNotificationType();
                            int isDailyReportEnabled = response.body().getLoginObject().getIsDailyReportEnabled();
                            int isPushNotificationsEnabled = response.body().getLoginObject().getIsPushNotificationsEnabled();
                            String dailyReportTime = response.body().getLoginObject().getDailyReportTime();
                            String userName = response.body().getLoginObject().getUserFirstName()+" "+response.body().getLoginObject().getUserLastName();
                            String region = response.body().getLoginObject().getRegion();
                            String timezone = response.body().getLoginObject().getTimezone();
                            AppCommon.getInstance(LoginActivity.this).savePreferences(isDailyReportEnabled,dailyReportTime);
                            AppCommon.getInstance(LoginActivity.this).setUserId(userId);
                            AppCommon.getInstance(LoginActivity.this).setNotificationEnabled(isPushNotificationsEnabled);
                            AppCommon.getInstance(LoginActivity.this).setTokenId(token);
                            AppCommon.getInstance(LoginActivity.this).setUserName(userName);
                            AppCommon.getInstance(LoginActivity.this).setEmail(email);
                            AppCommon.getInstance(LoginActivity.this).setPrimaryEmail(email);
                            AppCommon.getInstance(LoginActivity.this).setNotificationType(notificationType);
                            AppCommon.getInstance(LoginActivity.this).setRegion(region);
                            AppCommon.getInstance(LoginActivity.this).setTimeZone(timezone);
                          //  callUpdateTokenAPI(token, userId);
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                            Toast.makeText(LoginActivity.this, response.body().getError(), Toast.LENGTH_SHORT).show();
                        } else {
                            AppCommon.getInstance(LoginActivity.this).showDialog(LoginActivity.this,response.body().getError());
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                   progressBar.setVisibility(View.GONE);
                    AppCommon.getInstance(LoginActivity.this).showDialog(LoginActivity.this,getResources().getString(R.string.network_error));

                }
            });
        }
    }


}
