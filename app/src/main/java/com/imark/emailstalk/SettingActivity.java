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

import com.imark.emailstalk.Adapter.SettingsAdapter;
import com.imark.emailstalk.Model.PreferenceModel;

import java.util.ArrayList;
import java.util.List;

import CustomControl.SimpleDividerItemDecoration;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbarText)
    TextView textViewToolbar;
    @BindView(R.id.left)
    ImageView imageViewLeft;
    @BindView(R.id.right)
    ImageView imageViewRight;
    @BindView(R.id.recyclerViewSetting)
    RecyclerView recyclerViewSetting;
    private List<PreferenceModel> preferenceModelList = new ArrayList<>();
    SettingsAdapter settingsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        ButterKnife.bind(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewSetting.setLayoutManager(layoutManager);
        settingsAdapter = new SettingsAdapter(preferenceModelList, this);
        recyclerViewSetting.setAdapter(settingsAdapter);
        recyclerViewSetting.addItemDecoration(new SimpleDividerItemDecoration(this, R.drawable.line_divider));
        setUpEventsData();
        textViewToolbar.setText(R.string.setting);
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
        PreferenceModel preferenceModel = new PreferenceModel("Account");
        preferenceModelList.add(preferenceModel);

        preferenceModel = new PreferenceModel("Change Password");
        preferenceModelList.add(preferenceModel);

        preferenceModel = new PreferenceModel("Add Secondary Email");
        preferenceModelList.add(preferenceModel);

        preferenceModel = new PreferenceModel("Single Push Notification");
        preferenceModelList.add(preferenceModel);

        preferenceModel = new PreferenceModel("Multiple Push Notification");
        preferenceModelList.add(preferenceModel);
    }
}
