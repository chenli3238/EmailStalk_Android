package com.imark.emailstalk;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.imark.emailstalk.Adapter.MailAdapter;
import com.imark.emailstalk.Response.CommonRowResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 5/17/2017.
 */

public class AllMail extends Activity {
    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefresh;

    ArrayList<CommonRowResponse> commonRowArray = new ArrayList<>();
    MailAdapter mailAdapter;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_mail);
        ButterKnife.bind(this);
        setData();
        layoutManager = new LinearLayoutManager(AllMail.this);
        recycleView.setLayoutManager(layoutManager);
        mailAdapter = new MailAdapter(AllMail.this,commonRowArray);
        recycleView.setAdapter(mailAdapter);


    }

    private void setData() {
        commonRowArray.add(new CommonRowResponse("TestMail","view","10:10 Fri 22","To: Amit"));
        commonRowArray.add(new CommonRowResponse("TestMail","view","10:10 Fri 22","To: Amit"));
        commonRowArray.add(new CommonRowResponse("TestMail","view","10:10 Fri 22","To: Amit"));
        commonRowArray.add(new CommonRowResponse("TestMail","view","10:10 Fri 22","To: Amit"));
        commonRowArray.add(new CommonRowResponse("TestMail","view","10:10 Fri 22","To: Amit"));
        commonRowArray.add(new CommonRowResponse("TestMail","view","10:10 Fri 22","To: Amit"));
        commonRowArray.add(new CommonRowResponse("TestMail","view","10:10 Fri 22","To: Amit"));
        commonRowArray.add(new CommonRowResponse("TestMail","view","10:10 Fri 22","To: Amit"));
    }
}
