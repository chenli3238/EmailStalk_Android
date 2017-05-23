package com.imark.emailstalk.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.imark.emailstalk.Model.PreferenceModel;
import com.imark.emailstalk.PreferenceActivity;
import com.imark.emailstalk.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreferenceAdapter extends RecyclerView.Adapter<PreferenceAdapter.ListViewHolder> {
    private List<PreferenceModel> PreferenceModelList;
    Activity activity;

    public PreferenceAdapter(List<PreferenceModel> list, Activity activity) {
        this.PreferenceModelList = list;
        this.activity = activity;
    }

    @Override
    public PreferenceAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.preference_list_layout, parent, false);
        return new PreferenceAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PreferenceAdapter.ListViewHolder holder, int position) {
        holder.textViewHeading.setText(PreferenceModelList.get(position).getHeading());
        holder.textViewInfo.setText(PreferenceModelList.get(position).getInfo());
    }

    @Override
    public int getItemCount() {
        return PreferenceModelList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        @BindView(R.id.preferenceHeading)
        TextView textViewHeading;
        @BindView(R.id.preferenceInfo)
        TextView textViewInfo;
        @BindView(R.id.preferenceLayout)
        RelativeLayout relativeLayout;
        @BindView(R.id.prefIndicator)
        Button button;

        public ListViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.preferenceLayout)
        void ToggleButton() {
            int position = getAdapterPosition();
            ((PreferenceActivity) context).setAction(position,button);

        }
    }
}