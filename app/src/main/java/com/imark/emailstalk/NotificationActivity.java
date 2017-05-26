package com.imark.emailstalk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.imark.emailstalk.Adapter.NotificationAdapter;

import java.util.ArrayList;
import java.util.List;

import APIResponse.NotificationObject;
import CustomControl.SimpleDividerItemDecoration;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbarText)
    TextView textViewToolbar;
    @BindView(R.id.left)
    ImageView imageViewLeft;
    @BindView(R.id.right)
    ImageView imageViewRight;
    @BindView(R.id.recyclerViewNotification)
    RecyclerView recyclerViewAccepted;
    private List<NotificationObject> notificationObjectList = new ArrayList<>();
    NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);
        ButterKnife.bind(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewAccepted.setLayoutManager(layoutManager);
        notificationAdapter = new NotificationAdapter(notificationObjectList, this);
        recyclerViewAccepted.setAdapter(notificationAdapter);
        recyclerViewAccepted.addItemDecoration(new SimpleDividerItemDecoration(this, R.drawable.line_divider));
        setUpEventsData();
        textViewToolbar.setText(R.string.notification);
        imageViewLeft.setVisibility(View.VISIBLE);
        imageViewLeft.setImageResource(R.drawable.left);
    }

    @OnClick(R.id.left)
    void leftButton() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setUpEventsData() {
        NotificationObject NotificationObject = new NotificationObject("Contrary to popular belief, Loren ipsum is not simple random text", "read");
        notificationObjectList.add(NotificationObject);

        NotificationObject = new NotificationObject("Contrary to popular belief, Loren ipsum is not simple random text", "Read");
        notificationObjectList.add(NotificationObject);

        NotificationObject = new NotificationObject("Contrary to popular belief, Loren ipsum is not simple random text", "Read");
        notificationObjectList.add(NotificationObject);

        NotificationObject = new NotificationObject("Contrary to popular belief, Loren ipsum is not simple random text", "Read");
        notificationObjectList.add(NotificationObject);

        NotificationObject = new NotificationObject("Contrary to popular belief, Loren ipsum is not simple random text", "Read");
        notificationObjectList.add(NotificationObject);

        NotificationObject = new NotificationObject("Contrary to popular belief, Loren ipsum is not simple random text", "Read");
        notificationObjectList.add(NotificationObject);

        NotificationObject = new NotificationObject("Contrary to popular belief, Loren ipsum is not simple random text", "Read");
        notificationObjectList.add(NotificationObject);

        NotificationObject = new NotificationObject("Contrary to popular belief, Loren ipsum is not simple random text", "Read");
        notificationObjectList.add(NotificationObject);

    }
}
