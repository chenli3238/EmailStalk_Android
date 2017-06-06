package com.imark.emailstalk;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.imark.emailstalk.Adapter.PreferenceAdapter;
import com.imark.emailstalk.Infrastructure.AppCommon;
import com.imark.emailstalk.Model.PreferenceModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import API.EmailStalkService;
import API.ServiceGenerator;
import APIEntity.PreferencesEntity;
import APIResponse.PreferenceResponse;
import CustomControl.SimpleDividerItemDecoration;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    @BindView(R.id.textViewDailyReport)
    TextView textViewDailyReport;

    private int mHour, mMinute;

    private List<PreferenceModel> preferenceModelList = new ArrayList<>();
    PreferenceAdapter preferenceAdapter;
    int push, daily;

    ProgressDialog progress;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    Call<PreferenceResponse> preferenceResponseCall;

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
        textViewToolbar.setText(R.string.preference);
        imageViewLeft.setVisibility(View.VISIBLE);
        imageViewLeft.setImageResource(R.drawable.left);
        progress = new ProgressDialog(this);
        progress.setMessage(getResources().getString(R.string.please_wait));
        progress.setCancelable(false);
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
        push = AppCommon.getInstance(PreferenceActivity.this).getpushNotification();
//        PreferenceModel preferenceModel = new PreferenceModel(getResources().getString(R.string.push_notification_header),
//                getResources().getString(R.string.push_notification_detail), (push == 1));
//        preferenceModelList.add(preferenceModel);
        daily = AppCommon.getInstance(this).getDailyReport();
        PreferenceModel preferenceModel = new PreferenceModel(getResources().getString(R.string.daily_report_header),
                getResources().getString(R.string.daily_report_detail), (daily == 1));
        preferenceModelList.add(preferenceModel);
        String reportTime = AppCommon.getInstance(this).getDailyReportTime();
        textViewDailyReport.setText(reportTime);
    }

    public void setAction(int position, Button button) {
        switch (position) {
            case 0:
                if (button.isSelected()) {
                    button.setSelected(false);
                    daily = 0;
                } else {
                    button.setSelected(true);
                    daily = 1;
                }
                break;

        }
    }

    @OnClick(R.id.textViewDailyReport)
    void setTextViewDailyReport() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        final TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        textViewDailyReport.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    @OnClick(R.id.buttonSavePreference)
    void buttonSavePreference() {
        AppCommon.getInstance(this).setNonTouchableFlags(this);
        if (AppCommon.getInstance(this).isConnectingToInternet(this)) {
            progressBar.setVisibility(View.VISIBLE);
        String dailyReport;
        if (daily == 1) {
            dailyReport = "1";
        } else {
            dailyReport = "0";
        }
        int userId = AppCommon.getInstance(this).getUserId();
        final String reportTime = textViewDailyReport.getText().toString().trim();
        PreferencesEntity preferencesEntity = new PreferencesEntity(userId, dailyReport, reportTime);
        EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
            preferenceResponseCall = emailStalkService.savePreferences(preferencesEntity);
        preferenceResponseCall.enqueue(new Callback<PreferenceResponse>() {
            @Override
            public void onResponse(Call<PreferenceResponse> call, Response<PreferenceResponse> response) {
                AppCommon.getInstance(PreferenceActivity.this).clearNonTouchableFlags(PreferenceActivity.this);
                int success = response.body().getSuccess();
                progressBar.setVisibility(View.GONE);
                if (success == 1) {

                    AppCommon.getInstance(PreferenceActivity.this).showDialog(PreferenceActivity.this, getResources().getString(R.string.preference_alert));
                    AppCommon.getInstance(PreferenceActivity.this).savePreferences(daily, reportTime);
                } else {
                    AppCommon.getInstance(PreferenceActivity.this).showDialog(PreferenceActivity.this, response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<PreferenceResponse> call, Throwable t) {
                AppCommon.getInstance(PreferenceActivity.this).clearNonTouchableFlags(PreferenceActivity.this);
                progressBar.setVisibility(View.GONE);
                AppCommon.getInstance(PreferenceActivity.this).showDialog(PreferenceActivity.this, getResources().getString(R.string.network_error));

            }
        });
        } else {
            AppCommon.getInstance(PreferenceActivity.this).clearNonTouchableFlags(PreferenceActivity.this);
            AppCommon.getInstance(this).showDialog(this, getResources().getString(R.string.network_alert));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (preferenceResponseCall != null) {
            preferenceResponseCall.cancel();
        }
    }
}
