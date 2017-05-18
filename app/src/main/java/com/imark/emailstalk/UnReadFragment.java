package com.imark.emailstalk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imark.emailstalk.Adapter.MailAdapter;
import com.imark.emailstalk.Response.CommonRowResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 5/18/2017.
 */

public class UnReadFragment extends Fragment {
    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefresh;

    ArrayList<CommonRowResponse> commonRowArray = new ArrayList<>();
    MailAdapter mailAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.all_mail, container, false);
        ButterKnife.bind(this, v);
        setData();
        layoutManager = new LinearLayoutManager(getContext());
        recycleView.setLayoutManager(layoutManager);
        mailAdapter = new MailAdapter(UnReadFragment.this,commonRowArray);
        recycleView.setAdapter(mailAdapter);
        return v;
    }
   /* @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_mail);
        ButterKnife.bind(this);
        setData();
        layoutManager = new LinearLayoutManager(AllMail.this);
        recycleView.setLayoutManager(layoutManager);
        mailAdapter = new MailAdapter(AllMail.this,commonRowArray);
        recycleView.setAdapter(mailAdapter);


    }*/

    private void setData() {
        commonRowArray.add(new CommonRowResponse("Test Mail","Read twice","10:10 AM Feb 22","To: Adam smith"));
        commonRowArray.add(new CommonRowResponse("Test Mail","Read twice","10:10 AM Feb 22","To: Adam smith"));
        commonRowArray.add(new CommonRowResponse("Test Mail","Read twice","10:10 AM Feb 22","To: Adam smith"));
        commonRowArray.add(new CommonRowResponse("Test Mail","Read twice","10:10 AM Feb 22","To: Adam smith"));
        commonRowArray.add(new CommonRowResponse("Test Mail","Read twice","10:10 AM Feb 22","To: Adam smith"));
        commonRowArray.add(new CommonRowResponse("Test Mail","Read twice","10:10 AM Feb 22","To: Adam smith"));
        commonRowArray.add(new CommonRowResponse("Test Mail","Read twice","10:10 AM Feb 22","To: Adam smith"));
        commonRowArray.add(new CommonRowResponse("Test Mail","Read twice","10:10 AM Feb 22","To: Adam smith"));
        commonRowArray.add(new CommonRowResponse("Test Mail","Read twice","10:10 AM Feb 22","To: Adam smith"));
    }}
