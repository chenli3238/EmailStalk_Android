package com.imark.emailstalk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddEmail extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbarText)
    TextView textViewToolbar;
    @BindView(R.id.left)
    ImageView imageViewLeft;
    @BindView(R.id.right)
    ImageView imageViewRight;
    @BindView(R.id.email)
    EditText emailEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_email);
        ButterKnife.bind(this);
        textViewToolbar.setText(R.string.secondary_email);
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

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    @OnClick(R.id.submitAddEmail)
    void submitAddEmail() {
        String email = emailEditText.getText().toString().trim();
        if (email.isEmpty()) {
            emailEditText.setError("Email must be filled");
        } else if (!isEmailValid(email)) {
            emailEditText.setError("Please enter valid Email");
        } else {

        }
    }
}
