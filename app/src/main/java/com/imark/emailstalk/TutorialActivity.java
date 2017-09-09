package com.imark.emailstalk;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imark.emailstalk.Adapter.TutorialAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TutorialActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbarText)
    TextView textViewToolbar;

    @BindView(R.id.left)
    ImageView imageViewLeft;
    private ViewPager intro_images;
    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    private TutorialAdapter tutorialAdapter;

    private int[] mImageResources = {
            R.drawable.slide2,
            R.drawable.slide1,
            R.drawable.slide3
    };

    public TutorialActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_layout);
        ButterKnife.bind(this);
        textViewToolbar.setText(R.string.tutorials);
        imageViewLeft.setVisibility(View.VISIBLE);
        imageViewLeft.setImageResource(R.drawable.left);
        intro_images = (ViewPager) findViewById(R.id.pager_introduction);
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        tutorialAdapter = new TutorialAdapter(this, mImageResources);
        intro_images.setAdapter(tutorialAdapter);
        intro_images.setCurrentItem(0);
        intro_images.setOnPageChangeListener(this);
        setUiPageViewController();

    }


    @OnClick(R.id.left)
    void leftButton() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
        }
        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setUiPageViewController() {

        dotsCount = tutorialAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
    }

}
