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

/**
 * Created by User on 5/17/2017.
 */

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.signUpBtn)
    public void setSignUpBtn(){
        startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
    }
    @OnClick(R.id.forgotPasswordTextView)
    public void setResetPassword(){
       startActivity(new Intent(LoginActivity.this,ForgotPassword.class));
    }
    @OnClick(R.id.loginBtn)
    public void setLoginBtn(){
        startActivity(new Intent(LoginActivity.this,Home.class));
    }

}
