package com.imark.emailstalk.Adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imark.emailstalk.HomeActivity;
import com.imark.emailstalk.R;

import java.util.List;

import APIResponse.ToCcResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ToCCAdapter extends RecyclerView.Adapter<ToCCAdapter.ListViewHolder> {
    private List<ToCcResponse> toCcResponseList;
    Activity activity;

    public ToCCAdapter(List<ToCcResponse> list, Activity activity) {
        this.toCcResponseList = list;
        this.activity = activity;
    }

    @Override
    public ToCCAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tocc_list_layout, parent, false);
        return new ToCCAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ToCCAdapter.ListViewHolder holder, int position) {
        String emailOpen = toCcResponseList.get(position).getEmailOpen();
        String userName = toCcResponseList.get(position).getUserName();
        String email = toCcResponseList.get(position).getEmailID();
        String lastEmailOpen = toCcResponseList.get(position).getLastEmailOpen();
        if (userName.equals("")) {
            userName = email;
        }
        if (emailOpen.equals("0")) {
            holder.textViewTitle.setText(userName);
            holder.textViewDateTime.setText(emailOpen + " times " + lastEmailOpen);
        } else {
            holder.textViewTitle.setText(userName);
            holder.textViewDateTime.setText(emailOpen + " times after " + lastEmailOpen);
        }
    }

    @Override
    public int getItemCount() {
        return toCcResponseList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        @BindView(R.id.emailTitle)
        TextView textViewTitle;
        @BindView(R.id.dateTime)
        TextView textViewDateTime;

        public ListViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }
    }
}