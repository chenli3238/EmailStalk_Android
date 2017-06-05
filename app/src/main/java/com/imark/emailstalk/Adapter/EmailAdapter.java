package com.imark.emailstalk.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_list_layout, parent, false);
        return new EmailAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmailAdapter.ListViewHolder holder, int position) {
        holder.textViewTitle.setText(secondaryEmailObjectList.get(position).getEmail());
        if(activity instanceof SettingActivity){
            holder.textViewTitle.setTextColor(activity.getResources().getColor(R.color.black_text));
            holder.textViewArrow.setTextColor(activity.getResources().getColor(R.color.black_text));
        }
        if(activity instanceof HomeActivity) {
            if (position == secondaryEmailObjectList.size() - 1) {
                holder.relativeLayout.setVisibility(View.VISIBLE);
            } else {
                holder.relativeLayout.setVisibility(View.GONE);
            }
        }
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
        @BindView(R.id.addEmailLayout)
        RelativeLayout relativeLayout;


        public ListViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.settingTitle)
        void clickOnNavItems() {
            int position = getAdapterPosition();
            if (context instanceof HomeActivity) {
                ((HomeActivity) context).setEmailClickAction(position);
            }
            else {
                ((SettingActivity) activity).setEmailClickAction(position);
            }
        }
    }
}