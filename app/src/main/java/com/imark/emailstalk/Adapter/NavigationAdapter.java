package com.imark.emailstalk.Adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imark.emailstalk.HomeActivity;
import com.imark.emailstalk.Model.NavigationModel;
import com.imark.emailstalk.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        HomeActivity homeActivity = new HomeActivity();
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
            ((HomeActivity) context).setClickAction(position);
        }

    }
}