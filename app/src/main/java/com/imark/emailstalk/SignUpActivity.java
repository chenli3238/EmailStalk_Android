package com.imark.emailstalk;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends Activity {

    @BindView(R.id.userNameText)
    EditText userNameText;

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
    }
    @OnClick(R.id.loginBtn)
    public void setLoginBtn(){
        startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
    }
}
