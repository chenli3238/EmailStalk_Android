package com.imark.emailstalk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.imark.emailstalk.Adapter.PreferenceAdapter;
import com.imark.emailstalk.Model.PreferenceModel;

import java.util.ArrayList;
import java.util.List;

import CustomControl.SimpleDividerItemDecoration;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreferenceActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbarText)
    TextView textViewToolbar;
    @BindView(R.id.left)
    ImageView imageViewLeft;
    @BindView(R.id.right)
    ImageView imageViewRight;
    @BindView(R.id.preferenceRecyclerView)
    RecyclerView preferenceRecyclerView;
    @BindView(R.id.spinnerDelivery)
    Spinner spinnerDelivery;
    private List<PreferenceModel> preferenceModelList = new ArrayList<>();
    PreferenceAdapter preferenceAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference_layout);
        ButterKnife.bind(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        preferenceRecyclerView.setLayoutManager(layoutManager);
        preferenceAdapter = new PreferenceAdapter(preferenceModelList, this);
        preferenceRecyclerView.setAdapter(preferenceAdapter);
        preferenceRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this, R.drawable.line_divider));
        setUpEventsData();
        ArrayAdapter<CharSequence> adapterMe = ArrayAdapter.createFromResource(this, R.array.deliveryArray, R.layout.spinner_layout);
        spinnerDelivery.setAdapter(adapterMe);
        textViewToolbar.setText(R.string.preference);
        imageViewLeft.setVisibility(View.VISIBLE);
        imageViewLeft.setImageResource(R.drawable.left);
        
    }

    @OnClick(R.id.left)
    void leftButton(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setUpEventsData() {
        PreferenceModel preferenceModel = new PreferenceModel("Email notification enabled by default",
                "Recieve email alert straight in your inbox by default (it can be deactivated in each individual message).");
        preferenceModelList.add(preferenceModel);

        preferenceModel = new PreferenceModel("Daily report",
                "Get analysis everyday on your emailopens.See what strategies works bvest and get rid of the ones that don't.");
        preferenceModelList.add(preferenceModel);

    }
}
