package com.imark.emailstalk.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.imark.emailstalk.HomeActivity;
import com.imark.emailstalk.R;
import com.imark.emailstalk.SettingActivity;

import java.util.List;

import APIResponse.SecondaryEmailObject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.ListViewHolder> {
    private List<SecondaryEmailObject> secondaryEmailObjectList;
    Activity activity;

    public EmailAdapter(List<SecondaryEmailObject> list, Activity activity) {
        this.secondaryEmailObjectList = list;
        this.activity = activity;
    }

    @Override
    public EmailAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_list_layout, parent, false);
        return new EmailAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmailAdapter.ListViewHolder holder, int position) {
        holder.textViewTitle.setText(secondaryEmailObjectList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return secondaryEmailObjectList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        @BindView(R.id.settingTitle)
        TextView textViewTitle;
        @BindView(R.id.arrow)
        TextView textViewArrow;

        public ListViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.settingTitle)
        void clickOnNavItems() {
            int position = getAdapterPosition();
            ((HomeActivity) context).setEmailClickAction(position);
        }
    }
}