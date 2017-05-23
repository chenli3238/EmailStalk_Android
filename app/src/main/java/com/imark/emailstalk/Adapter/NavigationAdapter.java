package com.imark.emailstalk.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.imark.emailstalk.AccountsActivity;
import com.imark.emailstalk.FAQ;
import com.imark.emailstalk.Home;
import com.imark.emailstalk.HowItWorksActivity;
import com.imark.emailstalk.Infrastructure.AppCommon;
import com.imark.emailstalk.LoginActivity;
import com.imark.emailstalk.Model.NavigationModel;
import com.imark.emailstalk.PreferenceActivity;
import com.imark.emailstalk.R;
import com.imark.emailstalk.ReportaBugActivity;
import com.imark.emailstalk.SettingActivity;

import java.util.List;

import API.EmailStalkService;
import API.ServiceGenerator;
import APIEntity.TokenEntity;
import APIResponse.UpdateDeviceTokenResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ListViewHolder> {
    private List<NavigationModel> navList;
    public Activity activity;
    public DrawerLayout drawerLayout;


    public NavigationAdapter(List<NavigationModel> list, Activity activity) {
        this.navList = list;
        this.activity = activity;
    }


    public NavigationAdapter(DrawerLayout drawer) {
        this.drawerLayout = drawer;
    }

    @Override
    public NavigationAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_drawer_list_layout, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NavigationAdapter.ListViewHolder holder, int position) {
        holder.navImage.setImageResource(navList.get(position).getNavImage());
        holder.navTitle.setText(navList.get(position).getNavTitle());
    }

    @Override
    public int getItemCount() {
        return navList.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        @BindView(R.id.navImage)
        ImageView navImage;
        @BindView(R.id.navTitle)
        TextView navTitle;
        NavigationAdapter NavigationAdapter;
        DrawerLayout drawerLayout;
        Home home = new Home();
        Activity activity;

        public ListViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this, itemView);
            NavigationAdapter = new NavigationAdapter(drawerLayout);
        }

        @OnClick(R.id.navTitle)
        void clickOnNavItems() {
            int position = getAdapterPosition();
            ((Home) context).setClickAction(position);
        }

    }
}