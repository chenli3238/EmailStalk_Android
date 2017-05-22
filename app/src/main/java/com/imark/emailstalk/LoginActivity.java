package com.imark.emailstalk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.imark.emailstalk.Infrastructure.AppCommon;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import API.EmailStalkService;
import API.ServiceGenerator;
import APIEntity.LoginEntity;
import APIResponse.LoginResponse;
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
        progress.setMessage("Authenticating");
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
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        if (email.isEmpty()) {
            emailEditText.setError("Email must be filled");
        } else if (!isEmailValid(email)) {
            emailEditText.setError("Please enter valid Email");
        } else if (password.isEmpty()) {
            passwordEditText.setError("Password must be filled");
        } else {
            progress.show();
            firebaseInstanceIDService.onTokenRefresh();
            LoginEntity loginEntity = new LoginEntity(email, password, AppCommon.getInstance(this).getTokenId(), getResources().getString(R.string.android));
            EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
            Call<LoginResponse> responseModelCall = emailStalkService.loginResponseCall(loginEntity);
            responseModelCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.code() == 200) {
                        progress.dismiss();
                        int success = response.body().getSuccess();
                        if (success == 1) {
                            int userId = response.body().getLoginObject().getUserID();
                            SharedPreferences.Editor editor = getSharedPreferences("UserLogin", MODE_PRIVATE).edit();
                            editor.putBoolean("login", true);
                            editor.putInt("userId", userId);
                            editor.apply();
                            startActivity(new Intent(LoginActivity.this, Home.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, response.body().getError(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    progress.dismiss();
                    Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


}
