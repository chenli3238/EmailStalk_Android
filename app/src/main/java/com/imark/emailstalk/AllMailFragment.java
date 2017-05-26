package com.imark.emailstalk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
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

/**
 * Created by User on 5/17/2017.
 */

public class AllMailFragment extends Fragment {
    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefresh;

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
//        mailAdapter = new MailAdapter(AllMailFragment.this,commonRowArray);
//        recycleView.setAdapter(mailAdapter);
     Log.i("tokenId:", FirebaseInstanceId.getInstance().getToken());
        return v;
    }

    private void setData() {
        progress.show();
        int userid = AppCommon.getInstance(getContext()).getUserId();
        String email = AppCommon.getInstance(getContext()).getEmail();
        final EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
        Call<EmailResponse> emailResponseCall = emailStalkService.getListOfEmails(userid, 1, 0, email);
        emailResponseCall.enqueue(new Callback<EmailResponse>() {
            @Override
            public void onResponse(Call<EmailResponse> call, Response<EmailResponse> response) {
                progress.dismiss();
                int success = response.body().getSuccess();
                if(success ==1){
                    emailObjectList = response.body().getEmailObjectList();
                    mailAdapter = new MailAdapter(AllMailFragment.this, emailObjectList);
                    recycleView.setAdapter(mailAdapter);
                    mailAdapter.notifyDataSetChanged();
                }else{
                    AppCommon.getInstance(getContext()).showDialog(getActivity(),response.body().getError());
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
