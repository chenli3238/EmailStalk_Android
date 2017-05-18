package com.imark.emailstalk.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imark.emailstalk.FAQ;
import com.imark.emailstalk.HowItWorksActivity;
import com.imark.emailstalk.Model.NavigationModel;
import com.imark.emailstalk.PreferenceActivity;
import com.imark.emailstalk.R;
import com.imark.emailstalk.ReportaBugActivity;
import com.imark.emailstalk.SettingActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ListViewHolder> {
    private List<NavigationModel> navList;
    private Activity context;
    public DrawerLayout drawerLayout;

    public NavigationAdapter(List<NavigationModel> list, Activity activity) {
        this.navList = list;
        this.context = activity;
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

        public ListViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this, itemView);
            NavigationAdapter = new NavigationAdapter(drawerLayout);
        }

        @OnClick(R.id.navTitle)
        void clickOnNavItems() {
            int position = getAdapterPosition();
            switch (position) {
                case 0:
             //       context.startActivity(new Intent(context, MainActivity.class));
                    break;
                case 1:
                    context.startActivity(new Intent(context, HowItWorksActivity.class));
                    break;
                case 2:
                    Intent intentArr = new Intent(context, PreferenceActivity.class);
                    context.startActivity(intentArr);
                    break;
                case 3:
                    Intent intentNet = new Intent(context, FAQ.class);
                    context.startActivity(intentNet);
                    break;
                case 4:
                    context.startActivity(new Intent(context, SettingActivity.class));
                    break;
                case 5:
                    context.startActivity(new Intent(context, ReportaBugActivity.class));
                    break;
            }
        }
    }}