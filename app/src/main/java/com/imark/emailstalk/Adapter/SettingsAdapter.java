package com.imark.emailstalk.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.imark.emailstalk.Model.PreferenceModel;
import com.imark.emailstalk.R;
import com.imark.emailstalk.SettingActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ListViewHolder> {
    private List<PreferenceModel> preferenceModelList;
    Activity activity;

    public SettingsAdapter(List<PreferenceModel> list, Activity activity) {
        this.preferenceModelList = list;
        this.activity = activity;
    }

    @Override
    public SettingsAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_list_layout, parent, false);
        return new SettingsAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SettingsAdapter.ListViewHolder holder, int position) {
        holder.textViewTitle.setText(preferenceModelList.get(position).getHeading());
        if (position == preferenceModelList.size() - 3) {
            holder.notificationSwitch.setVisibility(View.VISIBLE);
            holder.textViewArrow.setVisibility(View.INVISIBLE);
            holder.buttonPrefIndicator.setVisibility(View.GONE);
        }
        if(position == preferenceModelList.size()-1 || position == preferenceModelList.size()-2){
            holder.buttonPrefIndicator.setVisibility(View.VISIBLE);
            holder.notificationSwitch.setVisibility(View.GONE);
            holder.textViewArrow.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return preferenceModelList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        @BindView(R.id.settingTitle)
        TextView textViewTitle;
        @BindView(R.id.pushNotificationSwitch)
        Switch notificationSwitch;
        @BindView(R.id.arrow)
        TextView textViewArrow;
        @BindView(R.id.prefIndicator)
        Button buttonPrefIndicator;

        public ListViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.settingTitle)
        void clickOnNavItems() {
            int position = getAdapterPosition();
            ((SettingActivity) context).setClickAction(position,notificationSwitch);
        }
    }
}