package com.imark.emailstalk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.imark.emailstalk.Adapter.NavigationAdapter;
import com.imark.emailstalk.Model.NavigationModel;

import java.util.ArrayList;
import java.util.List;

import CustomControl.SimpleDividerItemDecoration;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Home extends AppCompatActivity{
    @BindView(R.id.recyclerViewNavigation)
    RecyclerView recyclerViewNavigation;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.toolbarText)
    TextView textViewtoolbar;
    @BindView(R.id.left)
    ImageView imageViewLeft;
    private List<NavigationModel> navigationModelArrayList = new ArrayList<>();
    NavigationAdapter navigationAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        ButterKnife.bind(this);
        textViewtoolbar.setText(R.string.home);
        navigationAdapter = new NavigationAdapter(navigationModelArrayList,this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewNavigation.setLayoutManager(layoutManager);
        recyclerViewNavigation.setAdapter(navigationAdapter);
      //  recyclerViewNavigation.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext(), R.drawable.line_divider_navigation));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        setUpNavigationdrawer();
    }

    private void setUpNavigationdrawer() {
        NavigationModel navigationModel = new NavigationModel(R.drawable.account, "Account");
        navigationModelArrayList.add(navigationModel);

        navigationModel = new NavigationModel(R.drawable.how, "How it works");
        navigationModelArrayList.add(navigationModel);

        navigationModel = new NavigationModel(R.drawable.prefference, "Preferences");
        navigationModelArrayList.add(navigationModel);

        navigationModel = new NavigationModel(R.drawable.faq, "FAQ");
        navigationModelArrayList.add(navigationModel);

        navigationModel = new NavigationModel(R.drawable.support, "Support");
        navigationModelArrayList.add(navigationModel);

        navigationModel = new NavigationModel(R.drawable.report, "Report a Bug");
        navigationModelArrayList.add(navigationModel);

    }
    @Override
    protected void onPause() {
        super.onPause();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
        }
    }
}
