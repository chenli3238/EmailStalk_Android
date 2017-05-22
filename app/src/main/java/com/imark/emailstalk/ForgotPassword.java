package com.imark.emailstalk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
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

    ProgressDialog progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        ButterKnife.bind(this);
        progress = new ProgressDialog(this);
        progress.setMessage("Please Wait...");
        progress.setCancelable(false);
    }

    @OnClick(R.id.submitBtn)
    public void setSubmitBtn() {
        String email = emailEditText.getText().toString().trim();
        if (email.isEmpty()) {
            emailEditText.setError("Email must be filled");
        } else if (!AppCommon.getInstance(this).isEmailValid(email)) {
            emailEditText.setError("please enter valid email");
        } else {
            progress.show();
            ForgotPasswordEntity forgotPasswordEntity = new ForgotPasswordEntity(email);
            EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
            Call<PasswordResponse> forgotPasswordResponseCall = emailStalkService.forgotPasswordCall(forgotPasswordEntity);
            forgotPasswordResponseCall.enqueue(new Callback<PasswordResponse>() {
                @Override
                public void onResponse(Call<PasswordResponse> call, Response<PasswordResponse> response) {
                    if (response.code() == 200) {
                        progress.dismiss();
                        int success = response.body().getSuccess();
                        if (success == 1) {
                            finish();
                        } else {
                            Toast.makeText(ForgotPassword.this, response.body().getError(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                @Override
                public void onFailure(Call<PasswordResponse> call, Throwable t) {
                    progress.dismiss();
                    Toast.makeText(ForgotPassword.this, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
}}