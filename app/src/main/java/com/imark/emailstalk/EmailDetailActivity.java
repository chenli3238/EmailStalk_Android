package com.imark.emailstalk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imark.emailstalk.Adapter.SettingsAdapter;
import com.imark.emailstalk.Adapter.ToCCAdapter;
import com.imark.emailstalk.Infrastructure.AppCommon;

import java.util.ArrayList;
import java.util.List;

import API.EmailStalkService;
import API.ServiceGenerator;
import APIResponse.EmailObject;
import APIResponse.ToCcResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmailDetailActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbarText)
    TextView textViewToolbar;

    @BindView(R.id.left)
    ImageView imageViewLeft;

    @BindView(R.id.right)
    ImageView imageViewRight;

    @BindView(R.id.eyeImag)
    ImageView imageVieweyeImag;

    @BindView(R.id.eyeImag1)
    ImageView imageVieweyeImag1;

    @BindView(R.id.emailTitle)
    TextView textViewEmailTitle;

    @BindView(R.id.toRecyclerView)
    RecyclerView toRecyclerView;

    @BindView(R.id.ccRecyclerView)
    RecyclerView ccRecyclerView;

    ToCCAdapter toCCAdapter;
    List<ToCcResponse> toResponseList = new ArrayList<>();
    List<ToCcResponse> ccResponseList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_detail_layout);
        ButterKnife.bind(this);
        textViewToolbar.setText(R.string.home);
        imageViewLeft.setVisibility(View.VISIBLE);
        imageViewLeft.setImageResource(R.drawable.left);
        final LinearLayoutManager layoutManagerTo = new LinearLayoutManager(this);
        layoutManagerTo.setOrientation(LinearLayoutManager.VERTICAL);
        toRecyclerView.setLayoutManager(layoutManagerTo);
        final LinearLayoutManager layoutManagerCC = new LinearLayoutManager(this);
        layoutManagerCC.setOrientation(LinearLayoutManager.VERTICAL);
        ccRecyclerView.setLayoutManager(layoutManagerCC);
        SetupToCCList();
    }

    private void SetupToCCList() {
        String type = getIntent().getStringExtra("Type");
        if (type.equals("Local")) {
            String emailList = getIntent().getStringExtra("EmailList");
            Gson gson = new Gson();
            EmailObject emailObject = gson.fromJson(emailList, EmailObject.class);
            toResponseList = emailObject.getToResponses();
            toCCAdapter = new ToCCAdapter(toResponseList, this);
            toRecyclerView.setAdapter(toCCAdapter);
            ccResponseList = emailObject.getCcResponses();
            toCCAdapter = new ToCCAdapter(ccResponseList, this);
            ccRecyclerView.setAdapter(toCCAdapter);
            toCCAdapter.notifyDataSetChanged();
            String title = emailObject.getEmailTitle();
            textViewEmailTitle.setText(title);

            int read = emailObject.getIsRead();
            if (read == 1) {
                imageVieweyeImag.setSelected(true);
                imageVieweyeImag1.setSelected(true);
            } else {
                imageVieweyeImag.setSelected(false);
                imageVieweyeImag1.setSelected(false);
            }
        }
        if (type.equals("Notification")) {
            final EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
            Call<EmailObject> emailObjectCall = emailStalkService.getEmailDetail(AppCommon.getInstance(this).getUserId(), getIntent().getStringExtra("MessageId"));
            emailObjectCall.enqueue(new Callback<EmailObject>() {
                @Override
                public void onResponse(Call<EmailObject> call, Response<EmailObject> response) {
                    toResponseList = response.body().getToResponses();
                    toCCAdapter = new ToCCAdapter(toResponseList, EmailDetailActivity.this);
                    toRecyclerView.setAdapter(toCCAdapter);
                    ccResponseList = response.body().getCcResponses();
                    toCCAdapter = new ToCCAdapter(ccResponseList, EmailDetailActivity.this);
                    ccRecyclerView.setAdapter(toCCAdapter);
                    toCCAdapter.notifyDataSetChanged();
                    String title = response.body().getEmailTitle();
                    textViewEmailTitle.setText(title);

                    int read = response.body().getIsRead();
                    if (read == 1) {
                        imageVieweyeImag.setSelected(true);
                        imageVieweyeImag1.setSelected(true);
                    } else {
                        imageVieweyeImag.setSelected(false);
                        imageVieweyeImag1.setSelected(false);
                    }
                }

                @Override
                public void onFailure(Call<EmailObject> call, Throwable t) {

                }
            });
        }

    }

    @OnClick(R.id.left)
    void leftButton() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
