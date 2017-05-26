package com.imark.emailstalk.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imark.emailstalk.R;

import java.util.List;

import APIResponse.NotificationObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ListViewHolder> {
    private List<NotificationObject> notificationObjectList;
    Activity activity;

    public NotificationAdapter(List<NotificationObject> list, Activity activity) {
        this.notificationObjectList = list;
        this.activity = activity;
    }

    @Override
    public NotificationAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list_layout, parent, false);
        return new NotificationAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationAdapter.ListViewHolder holder, int position) {
        holder.textViewInfo.setText(notificationObjectList.get(position).getInfo());
        holder.textViewDate.setText(notificationObjectList.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return notificationObjectList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        @BindView(R.id.infoText)
        TextView textViewInfo;
        @BindView(R.id.infoStatus)
        TextView textViewDate;
        @BindView(R.id.cross)
        ImageView imageViewCross;
        @BindView(R.id.notificationLayout)
        LinearLayout linearLayoutNoti;

        public ListViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.cross)
        void cross() {
            final int position = getAdapterPosition();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.notification_delete_text)
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            notificationObjectList.remove(position);
                            notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle(R.string.app_name);
            alert.show();
        }

        @OnClick(R.id.notificationLayout)
        void Click() {
            int position = getAdapterPosition();

        }
    }
}
