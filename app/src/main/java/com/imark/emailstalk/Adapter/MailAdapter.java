package com.imark.emailstalk.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imark.emailstalk.R;


public class MailAdapter extends RecyclerView.Adapter<MailAdapter.MailHolder> {
    @Override
    public MailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);
        return null;
    }

    @Override
    public void onBindViewHolder(MailHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MailHolder extends RecyclerView.ViewHolder {
        public MailHolder(View itemView) {
            super(itemView);
        }
    }
}
