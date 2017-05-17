package com.imark.emailstalk;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_mail);
        ButterKnife.bind(this);
    }
}
