package com.imark.emailstalk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity  extends AppCompatActivity{
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
    @BindView(R.id.spinnerLanguage)
    Spinner spinnerLanguage;
    @BindView(R.id.spinnerCountry)
    Spinner spinnerCountry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        ButterKnife.bind(this);
        textViewToolbar.setText(R.string.setting);
        imageViewLeft.setVisibility(View.VISIBLE);
        imageViewLeft.setImageResource(R.drawable.left);

        ArrayAdapter<CharSequence> adaptertime = ArrayAdapter.createFromResource(this, R.array.timeZone, R.layout.spinner_layout);
        spinnerTimeZone.setAdapter(adaptertime);
        ArrayAdapter<CharSequence> adapterlang = ArrayAdapter.createFromResource(this, R.array.language, R.layout.spinner_layout);
        spinnerLanguage.setAdapter(adapterlang);
        ArrayAdapter<CharSequence> adaptercoun = ArrayAdapter.createFromResource(this, R.array.country, R.layout.spinner_layout);
        spinnerCountry.setAdapter(adaptercoun);


    }

    @OnClick(R.id.left)
    void leftButton(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

