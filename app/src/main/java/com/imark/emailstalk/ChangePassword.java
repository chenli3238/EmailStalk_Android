package com.imark.emailstalk;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.imark.emailstalk.Infrastructure.AppCommon;

import API.EmailStalkService;
import API.ServiceGenerator;
import APIEntity.ChangePasswordEntity;
import APIResponse.PasswordResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbarText)
    TextView textViewToolbar;
    @BindView(R.id.left)
    ImageView imageViewLeft;
    @BindView(R.id.right)
    ImageView imageViewRight;
    @BindView(R.id.previousPassword)
    EditText previousPassword;
    @BindView(R.id.newPassword)
    EditText newPassword;
    @BindView(R.id.confirmPassword)
    EditText confirmPassword;
    @BindView(R.id.changePassword)
    Button changePassword;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        ButterKnife.bind(this);
        textViewToolbar.setText(R.string.change_password);
        imageViewLeft.setVisibility(View.VISIBLE);
        imageViewLeft.setImageResource(R.drawable.left);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

    }

    @OnClick(R.id.left)
    void leftButton() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.changePassword)
    void changePassword(){
        String currPass = previousPassword.getText().toString().trim();
        String newPass = newPassword.getText().toString().trim();
        String confPass = confirmPassword.getText().toString().trim();
        if(currPass.isEmpty()){
        previousPassword.setError("Please enter your previous password");
        }else if(newPass.isEmpty()){
            previousPassword.setError("Please enter your new password");
        }else if(confPass.isEmpty()){
            previousPassword.setError("Please Re-enter your password");
        }else if (newPass.equals(confPass)){
            passwordChange(currPass,newPass);
        }else {
            newPassword.setError("Password Mismatch");
            confirmPassword.setError("Password Mismatch");
        }
    }

    private void passwordChange(String currPass, String newPass) {
        progressDialog.show();
        int userId = AppCommon.getInstance(this).getUserId();
        ChangePasswordEntity changePasswordEntity = new ChangePasswordEntity(userId,currPass,newPass);
        EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
        Call<PasswordResponse> passwordResponseCall = emailStalkService.changePasswordCall(changePasswordEntity);
        passwordResponseCall.enqueue(new Callback<PasswordResponse>() {
            @Override
            public void onResponse(Call<PasswordResponse> call, Response<PasswordResponse> response) {
                if(response.code() == 200){
                    progressDialog.dismiss();
                    if(response.body().getSuccess()== 1){
                        Toast.makeText(ChangePassword.this, response.body().getResult(), Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        AppCommon.getInstance(ChangePassword.this).showDialog(ChangePassword.this,response.body().getError());
                    }
                }
            }

            @Override
            public void onFailure(Call<PasswordResponse> call, Throwable t) {

            }
        });
    }
}