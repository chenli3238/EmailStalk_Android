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
import android.widget.RelativeLayout;

import com.imark.emailstalk.Adapter.EmailAdapter;
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

    ArrayList<CommonRowResponse> commonRowArray = new ArrayList<>();
    private List<EmailObject> emailObjectList = new ArrayList<>();
    MailAdapter mailAdapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog progress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.all_mail, container, false);
        ButterKnife.bind(this, v);
        progress = new ProgressDialog(getContext());
        progress.setMessage(getResources().getString(R.string.please_wait));
        progress.setCancelable(false);
        setData();
        layoutManager = new LinearLayoutManager(getContext());
        recycleView.setLayoutManager(layoutManager);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setData();
            }
        });
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        return v;
    }


    private void setData() {
        progress.show();
        int userid = AppCommon.getInstance(getContext()).getUserId();
        String email = AppCommon.getInstance(getContext()).getEmail();
        final EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
        Call<EmailResponse> emailResponseCall = emailStalkService.getListOfEmails(userid, 0, 0, email);
        emailResponseCall.enqueue(new Callback<EmailResponse>() {
            @Override
            public void onResponse(Call<EmailResponse> call, Response<EmailResponse> response) {
                progress.dismiss();
                int success = response.body().getSuccess();
                if(success ==1){
                    swipeRefresh.setVisibility(View.VISIBLE);
                    recycleView.setVisibility(View.VISIBLE);
                    relativeLayoutNoEmail.setVisibility(View.GONE);
                    emailObjectList = response.body().getEmailObjectList();
                    mailAdapter = new MailAdapter(ReadFragment.this, emailObjectList);
                    recycleView.setAdapter(mailAdapter);
                    mailAdapter.notifyDataSetChanged();
                    swipeRefresh.setRefreshing(false);

                }else{
                    swipeRefresh.setVisibility(View.GONE);
                    recycleView.setVisibility(View.GONE);
                    relativeLayoutNoEmail.setVisibility(View.VISIBLE);
 //                   AppCommon.getInstance(getContext()).showDialog(getActivity(),response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<EmailResponse> call, Throwable t) {
                Log.d("EmailStalk",t.toString());
                progress.dismiss();
            }
        });

    }
}