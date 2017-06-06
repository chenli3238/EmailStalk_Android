package com.imark.emailstalk;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.imark.emailstalk.Infrastructure.AppCommon;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import API.EmailStalkService;
import API.ServiceGenerator;
import APIEntity.EmailEntity;
import APIResponse.SecondaryEmailResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEmailActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbarText)
    TextView textViewToolbar;
    @BindView(R.id.left)
    ImageView imageViewLeft;
    @BindView(R.id.right)
    ImageView imageViewRight;
    @BindView(R.id.email)
    EditText emailEditText;
    ProgressDialog progress;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Call<SecondaryEmailResponse> secondaryEmailResponseCall;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_email);
        ButterKnife.bind(this);
        textViewToolbar.setText(R.string.secondary_email);
        imageViewLeft.setVisibility(View.VISIBLE);
        imageViewLeft.setImageResource(R.drawable.left);
        progress = new ProgressDialog(this);
        progress.setMessage(getResources().getString(R.string.please_wait));
        progress.setCancelable(false);
    }

    @OnClick(R.id.left)
    void leftButton() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.submitAddEmail)
    void submitAddEmail() {
        String email = emailEditText.getText().toString().trim();
        if (email.isEmpty()) {
            emailEditText.setError("Email must be filled");
        } else if (!AppCommon.getInstance(AddEmailActivity.this).isEmailValid(email)) {
            emailEditText.setError("Please enter valid Email");
        } else {
            AppCommon.getInstance(this).setNonTouchableFlags(this);
            if (AppCommon.getInstance(this).isConnectingToInternet(this)) {
                progressBar.setVisibility(View.VISIBLE);
                EmailEntity emailEntity = new EmailEntity(AppCommon.getInstance(this).getUserId(), email);
                EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
                secondaryEmailResponseCall = emailStalkService.addNewEmailAccount(emailEntity);
                secondaryEmailResponseCall.enqueue(new Callback<SecondaryEmailResponse>() {
                    @Override
                    public void onResponse(Call<SecondaryEmailResponse> call, Response<SecondaryEmailResponse> response) {
                        AppCommon.getInstance(AddEmailActivity.this).clearNonTouchableFlags(AddEmailActivity.this);
                        progressBar.setVisibility(View.GONE);
                        int success = response.body().getSuccess();
                        if (success == 1) {
                            emailEditText.setText("");
                            showDialog();
                            //   finish();
                        } else {
                            AppCommon.getInstance(AddEmailActivity.this).showDialog(AddEmailActivity.this, response.body().getError());
                        }
                    }

                    @Override
                    public void onFailure(Call<SecondaryEmailResponse> call, Throwable t) {
                        AppCommon.getInstance(AddEmailActivity.this).clearNonTouchableFlags(AddEmailActivity.this);
                        progressBar.setVisibility(View.GONE);
                    }
                });

            } else {
                AppCommon.getInstance(this).clearNonTouchableFlags(this);
                AppCommon.getInstance(this).showDialog(this, getResources().getString(R.string.network_alert));
            }
        }
    }

    public void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(getResources().getString(R.string.verify_email));
        builder.setIcon(R.drawable.appicon);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finish();
                }
            });
        builder.show();
    }
}
