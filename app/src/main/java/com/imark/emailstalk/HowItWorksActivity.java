package com.imark.emailstalk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HowItWorksActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbarText)
    TextView textViewToolbar;
    @BindView(R.id.left)
    ImageView imageViewLeft;
    @BindView(R.id.right)
    ImageView imageViewRight;
    @BindView(R.id.howitworktext)
    TextView textViewWork;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.howitworks_layout);
        ButterKnife.bind(this);
        textViewToolbar.setText(R.string.how);
        imageViewLeft.setVisibility(View.VISIBLE);
        imageViewLeft.setImageResource(R.drawable.left);
        String first = "Over 200,000 people use ";
        String second = "<font color='#6cc285'>Email Stalk </font>";
        String third = "to see what happens to their emails aftre they press ";
        String register = "<font color='#6cc285'>send</font>";
        textViewWork.setText(Html.fromHtml(first + second + third + register));

    }

    @OnClick(R.id.left)
    void leftButton() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
