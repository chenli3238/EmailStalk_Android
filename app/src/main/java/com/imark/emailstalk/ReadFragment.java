package com.imark.emailstalk;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.imark.emailstalk.Adapter.MailAdapter;
import com.imark.emailstalk.Infrastructure.AppCommon;
import com.imark.emailstalk.Response.CommonRowResponse;

import java.util.ArrayList;
import java.util.List;

import API.EmailStalkService;
import API.ServiceGenerator;
import APIResponse.EmailObject;
import APIResponse.EmailResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadFragment extends Fragment {
    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefresh;

    @BindView(R.id.noEmailFound)
    RelativeLayout relativeLayoutNoEmail;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.bottomProgressBar)
    ProgressBar bottomProgressBar;

    private List<EmailObject> emailObjectList = new ArrayList<>();

    MailAdapter mailAdapter;

    RecyclerView.LayoutManager layoutManager;

    ProgressDialog progress;
    Call emailResponseCall;

    int offset = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.all_mail, container, false);
        ButterKnife.bind(this, v);
        progress = new ProgressDialog(getContext());
        progress.setMessage(getResources().getString(R.string.please_wait));
        progress.setCancelable(false);
        layoutManager = new LinearLayoutManager(getContext());
        recycleView.setLayoutManager(layoutManager);
        emailObjectList = new ArrayList<EmailObject>();
        mailAdapter = new MailAdapter(ReadFragment.this, emailObjectList);
        recycleView.setAdapter(mailAdapter);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                offset = 1;
                emailObjectList.clear();
                callGetListOfEmailAPI();
            }
        });
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        progressBar.setVisibility(View.VISIBLE);
        callGetListOfEmailAPI();
        return v;
    }


    private void callGetListOfEmailAPI() {
        AppCommon.getInstance(getActivity()).setNonTouchableFlags(getActivity());
        if (AppCommon.getInstance(getContext()).isConnectingToInternet(getContext())) {
        int userid = AppCommon.getInstance(getContext()).getUserId();
        String email = AppCommon.getInstance(getContext()).getEmail();
        final EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
            emailResponseCall = emailStalkService.getListOfEmails(userid, 0, offset, email);
        emailResponseCall.enqueue(new Callback<EmailResponse>() {
            @Override
            public void onResponse(Call<EmailResponse> call, Response<EmailResponse> response) {
                AppCommon.getInstance(getActivity()).clearNonTouchableFlags(getActivity());
                progressBar.setVisibility(View.GONE);
                int success = response.body().getSuccess();
                if (success == 1) {
                    relativeLayoutNoEmail.setVisibility(View.GONE);
                    swipeRefresh.setRefreshing(false);
                    if (emailObjectList != null || emailObjectList.size() > 0) {
                        for (EmailObject emailObject : response.body().getEmailObjectList()) {
                            emailObjectList.add(emailObject);
                        }
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    relativeLayoutNoEmail.setVisibility(View.VISIBLE);
                    swipeRefresh.setRefreshing(false);
                }
                mailAdapter.updateList(emailObjectList, offset);
            }

            @Override
            public void onFailure(Call<EmailResponse> call, Throwable t) {
                AppCommon.getInstance(getActivity()).clearNonTouchableFlags(getActivity());
                progressBar.setVisibility(View.GONE);
                bottomProgressBar.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
                if (emailObjectList == null || emailObjectList.size() == 0) {
                    relativeLayoutNoEmail.setVisibility(View.VISIBLE);
                }
            }
        });

        } else {
            AppCommon.getInstance(getActivity()).clearNonTouchableFlags(getActivity());
            progressBar.setVisibility(View.GONE);
            AppCommon.getInstance(getContext()).showDialog(getActivity(), getResources().getString(R.string.network_alert));
        }
    }

    public void fetchMoreData() {
        bottomProgressBar.setVisibility(View.VISIBLE);
        offset = offset + 1;
        callGetListOfEmailAPI();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (emailResponseCall != null) {
            emailResponseCall.cancel();
        }
    }
}