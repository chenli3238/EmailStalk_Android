package com.imark.emailstalk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.imark.emailstalk.Infrastructure.AppCommon;

import API.EmailStalkService;
import API.ServiceGenerator;
import APIEntity.ForgotPasswordEntity;
import APIResponse.PasswordResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgotPassword extends Activity {
    @BindView(R.id.emailEditText)
    EditText emailEditText;

    @BindView(R.id.submitBtn)
    TextView submitBtn;

    @BindView(R.id.loginBtn)
    TextView loginBtn;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    ProgressDialog progress;

    Call<PasswordResponse> forgotPasswordResponseCall;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        ButterKnife.bind(this);
        progress = new ProgressDialog(this);
        progress.setMessage("Please Wait...");
        progress.setCancelable(false);
    }

    @OnClick(R.id.loginBtn)
    void loginBtn() {
        finish();
    }

    @OnClick(R.id.submitBtn)
    public void setSubmitBtn() {
        String email = emailEditText.getText().toString().trim();
        if (email.isEmpty()) {
            emailEditText.setError("Email must be filled");
        } else if (!AppCommon.getInstance(this).isEmailValid(email)) {
            emailEditText.setError("please enter valid email");
        } else {
            AppCommon.getInstance(this).setNonTouchableFlags(this);
            if (AppCommon.getInstance(this).isConnectingToInternet(this)) {
                progressBar.setVisibility(View.VISIBLE);
                ForgotPasswordEntity forgotPasswordEntity = new ForgotPasswordEntity(email);
                EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
                forgotPasswordResponseCall = emailStalkService.forgotPasswordCall(forgotPasswordEntity);
                forgotPasswordResponseCall.enqueue(new Callback<PasswordResponse>() {
                    @Override
                    public void onResponse(Call<PasswordResponse> call, Response<PasswordResponse> response) {
                        AppCommon.getInstance(ForgotPassword.this).clearNonTouchableFlags(ForgotPassword.this);
                        if (response.code() == 200) {
                            progressBar.setVisibility(View.GONE);
                            int success = response.body().getSuccess();
                            if (success == 1) {
                                AppCommon.getInstance(ForgotPassword.this).showDialog(ForgotPassword.this, response.body().getResult());
                            } else {
                                AppCommon.getInstance(ForgotPassword.this).showDialog(ForgotPassword.this, response.body().getError());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PasswordResponse> call, Throwable t) {
                        AppCommon.getInstance(ForgotPassword.this).clearNonTouchableFlags(ForgotPassword.this);
                        progressBar.setVisibility(View.GONE);
                        AppCommon.getInstance(ForgotPassword.this).showDialog(ForgotPassword.this, getResources().getString(R.string.network_error));

                    }
                });
            } else {
                AppCommon.getInstance(ForgotPassword.this).clearNonTouchableFlags(ForgotPassword.this);
                AppCommon.getInstance(this).showDialog(this, getResources().getString(R.string.network_alert));
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (forgotPasswordResponseCall != null) {
            forgotPasswordResponseCall.cancel();
        }
    }
}