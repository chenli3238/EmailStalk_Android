package com.imark.emailstalk;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        ButterKnife.bind(this);
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        Log.d("Time zone","tz"+tz.getID());
    }

    @OnClick(R.id.loginBtn)
    public void setLoginBtn() {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
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
            SharedPreferences.Editor editor = getSharedPreferences("UserLogin", MODE_PRIVATE).edit();
            editor.putBoolean("login", true);
            editor.apply();
            startActivity(new Intent(SignUpActivity.this, Home.class));
            finish();
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
