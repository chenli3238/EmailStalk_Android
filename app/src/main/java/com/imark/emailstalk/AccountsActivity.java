package com.imark.emailstalk;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.imark.emailstalk.Infrastructure.AppCommon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import API.EmailStalkService;
import API.ServiceGenerator;
import APIEntity.ProfileEntity;
import APIResponse.ProfileResponse;
import APIResponse.TimeZoneResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountsActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbarText)
    TextView textViewToolbar;
    @BindView(R.id.left)
    ImageView imageViewLeft;
    @BindView(R.id.right)
    ImageView imageViewRight;
    @BindView(R.id.spinnerTimeZone)
    Spinner spinnerTimeZone;
    @BindView(R.id.spinnerCountry)
    Spinner spinnerCountry;
    @BindView(R.id.firstName)
    EditText editTextFirstName;
    @BindView(R.id.lastName)
    EditText editTextLastName;
    @BindView(R.id.saveProfile)
    Button buttonSaveprofile;
    ProgressDialog progressDialog;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_layout);
        ButterKnife.bind(this);
        textViewToolbar.setText(R.string.account);
        imageViewLeft.setVisibility(View.VISIBLE);
        imageViewLeft.setImageResource(R.drawable.left);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        getRegionAndTimeZone();
        String userName = AppCommon.getInstance(this).getUserName();
        String[] split = userName.split(" ");
        editTextFirstName.setText(split[0]);
        editTextLastName.setText(split[1]);

    }

    private void getRegionAndTimeZone() {
        int userId = AppCommon.getInstance(this).getUserId();
        final EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
        final Call<TimeZoneResponse> timeZoneResponseCall = emailStalkService.getListIOFTimeZone(userId);
        timeZoneResponseCall.enqueue(new Callback<TimeZoneResponse>() {
            @Override
            public void onResponse(Call<TimeZoneResponse> call, Response<TimeZoneResponse> response) {
                int success = response.body().getSuccess();
                if (success == 1) {
                    //  timeZoneObjectList = response.body().getTimeZoneObjectList();
                    SetRegionTimeZone(response.body().getTimeZoneList());
                }
            }

            @Override
            public void onFailure(Call<TimeZoneResponse> call, Throwable t) {
//                AppCommon.getInstance(HomeActivity.this).showDialog(HomeActivity.this, "No Network Connection");
            }
        });
    }

    private void SetRegionTimeZone(final ArrayList<String> timeZoneObjectList) {
        final ArrayList<String> arrayList = new ArrayList<String>();
        Set<String> hs = new HashSet<>();
        int regionIndex = 0;
        for (int i = 0; i < timeZoneObjectList.size(); i++) {
            String regionTimeZone = timeZoneObjectList.get(i);
            String[] split = regionTimeZone.split("/");
            String firstSubString = split[0];
            hs.add(firstSubString);
        }
        arrayList.addAll(hs);
        Collections.sort(arrayList, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
        arrayList.add(0, "Select Region");

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, arrayList);
        spinnerCountry.setAdapter(stringArrayAdapter);

        if (!AppCommon.getInstance(this).getRegion().equals("")) {
            regionIndex = getIndexRegion(arrayList, AppCommon.getInstance(this).getRegion());
            spinnerCountry.setSelection(regionIndex);
        }

        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> listClone = new ArrayList<String>();
                int timeZoneIndex = 0;
                if (position == 0) {
                    listClone.add(0, "Select TimeZone");
                } else {
                    String s = arrayList.get(position);
                    for (String string : timeZoneObjectList) {
                        if (string.matches("(?i)(" + s + ").*")) {
                            listClone.add(string);
                        }
                    }
                    listClone.add(0, "Select TimeZone");
                    timeZoneIndex = getIndexRegion(listClone, AppCommon.getInstance(AccountsActivity.this).getTimezone());
                }

                ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(AccountsActivity.this, R.layout.spinner_layout, listClone);
                spinnerTimeZone.setAdapter(stringArrayAdapter);
                if (arrayList.get(position).equals(AppCommon.getInstance(AccountsActivity.this).getRegion())) {
                    spinnerTimeZone.setSelection(timeZoneIndex);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        if (!AppCommon.getInstance(AccountsActivity.this).getTimezone().equals("")) {
//            String s = arrayList.get(regionIndex);
//            List<String> listClone = new ArrayList<String>();
//            for (String string : timeZoneObjectList) {
//                if (string.matches("(?i)(" + s + ").*")) {
//                    listClone.add(string);
//                }
//            }
//            listClone.add(0, "Select TimeZone");
//            ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(AccountsActivity.this, R.layout.spinner_layout, listClone);
//            spinnerTimeZone.setAdapter(ArrayAdapter);
//            int timeZoneIndex = getIndexRegion(listClone, AppCommon.getInstance(AccountsActivity.this).getTimezone());
//            spinnerTimeZone.setSelection(timeZoneIndex);
//        }
    }

    private int getIndexRegion(ArrayList<String> arrayList, String region) {
        int i;
        for (i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals(region)) {
                break;
            }
        }
        return i;
    }

    private int getIndexRegion(List<String> arrayList, String region) {
        int i;
        for (i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals(region)) {
                break;
            }
        }
        return i;
    }

    @OnClick(R.id.left)
    void leftButton() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.saveProfile)
    void setButtonSaveprofile() {
        final String fName = editTextFirstName.getText().toString().trim();
        final String lName = editTextLastName.getText().toString().trim();
        final String region = spinnerCountry.getSelectedItem().toString().trim();
        final String timeZone = spinnerTimeZone.getSelectedItem().toString().trim();
        if (fName.isEmpty()) {
            editTextFirstName.setError("First Name must be filled");
        } else if (lName.isEmpty()) {
            editTextLastName.setError("Last Name must be filled");
        } else if (region.equals("Select Region")) {
            Toast.makeText(this, "Please Select Region", Toast.LENGTH_SHORT).show();
        } else if (timeZone.equals("Select TimeZone")) {
            Toast.makeText(this, "Please Select TimeZone", Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            int userId = AppCommon.getInstance(this).getUserId();
            ProfileEntity profileEntity = new ProfileEntity(userId, fName, lName, region, timeZone);
            EmailStalkService emailStalkService = ServiceGenerator.createService(EmailStalkService.class);
            Call<ProfileResponse> profileResponseCall = emailStalkService.setProfile(profileEntity);
            profileResponseCall.enqueue(new Callback<ProfileResponse>() {
                @Override
                public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                    progressBar.setVisibility(View.GONE);
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        AppCommon.getInstance(AccountsActivity.this).setRegion(region);
                        AppCommon.getInstance(AccountsActivity.this).setTimeZone(timeZone);
                        AppCommon.getInstance(AccountsActivity.this).setUserName(fName + " " + lName);
                        AppCommon.getInstance(AccountsActivity.this).showDialog(AccountsActivity.this, getResources().getString(R.string.account_alert));
                    } else {
                        AppCommon.getInstance(AccountsActivity.this).showDialog(AccountsActivity.this, response.body().getError());
                    }
                }

                @Override
                public void onFailure(Call<ProfileResponse> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                }
            });

        }
    }
}

