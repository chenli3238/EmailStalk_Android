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

import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import API.EmailStalkService;
import API.ServiceGenerator;
import APIEntity.RegistrationEntity;
import APIResponse.LoginResponse;
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

    FirebaseInstanceIDService firebaseInstanceIDService = new FirebaseInstanceIDService();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        ButterKnife.bind(this);
        Calendar cal = Calendar.getInstance();
        tz = cal.getTimeZone();
        Log.d("Time zone", "tz" + tz.getID());
        progress = new ProgressDialog(this);
        progress.setMessage("Please Wait!");
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
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        if (fName.isEmpty()) {
            userFNameText.setError("First Name must be filled");
        } else if (lName.isEmpty()) {
            userLNameText.setError("Last Name must be filled");
        } else if (email.isEmpty()) {
            emailEditText.setError("Email must be filled");
        } else if (!isEmailValid(email)) {
            emailEditText.setError("Please enter valid Email");
        } else if (password.isEmpty()) {
            passwordEditText.setError("Password must be filled");
        } else {
            progress.show();
            final String token = firebaseInstanceIDService.getDeviceToken();
            RegistrationEntity registrationEntity = new RegistrationEntity(fName, lName, email, tz, password, token, getResources().getString(R.string.android));
            EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
            Call<RegistrationResponse> responseModelCall = emailStalkService.registrationResponseCall(registrationEntity);
            responseModelCall.enqueue(new Callback<RegistrationResponse>() {
                @Override
                public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                    if (response.code() == 200) {
                        progress.dismiss();
                        int success = response.body().getSuccess();
                        if (success == 1) {
                            int userId = response.body().getRegistrationObject().getUserID();
                            SharedPreferences.Editor editor = getSharedPreferences("UserLogin", MODE_PRIVATE).edit();
                            editor.putBoolean("login", true);
                            editor.putInt("userId", userId);
                            editor.apply();
                            startActivity(new Intent(SignUpActivity.this, Home.class));
                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, response.body().getError(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                    progress.dismiss();
                    Toast.makeText(SignUpActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
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
