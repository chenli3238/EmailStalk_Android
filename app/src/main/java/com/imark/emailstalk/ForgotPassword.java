package com.imark.emailstalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by User on 5/17/2017.
 */

public class ForgotPassword extends Activity {
    @BindView(R.id.emailEditText)
    EditText emailEditText;

    @BindView(R.id.submitBtn)
    TextView submitBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.submitBtn)
    public void setSubmitBtn(){
        startActivity(new Intent(ForgotPassword.this,LoginActivity.class));
        finish();
    }
}
