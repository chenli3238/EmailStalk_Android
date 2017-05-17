package com.imark.emailstalk.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imark.emailstalk.AllMail;
import com.imark.emailstalk.R;
import com.imark.emailstalk.Response.CommonRowResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MailAdapter extends RecyclerView.Adapter<MailAdapter.MailHolder> {
    Context context;
    ArrayList<CommonRowResponse> commonRowArray;
    public MailAdapter(Context context, ArrayList<CommonRowResponse> commonRowArray) {
       this.context = context;
        this.commonRowArray = commonRowArray;
    }

    @Override
    public MailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);
        return new MailHolder(view);
    }

    @Override
    public void onBindViewHolder(MailHolder holder, int position) {
        CommonRowResponse commonRowResponse = commonRowArray.get(position);
        holder.emailTitleTextView.setText(commonRowResponse.getEmailTitle());
        holder.datetextView.setText(commonRowResponse.getDate());
        holder.readStatusTextView.setText(commonRowResponse.getEmailStatus());
        holder.reciverNameTextView.setText(commonRowResponse.getReciverName());
    }

    @Override
    public int getItemCount() {
        return commonRowArray.size();
    }

    public class MailHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.eyeImag)
        ImageView eyeImag;

        @BindView(R.id.emailTitleTextView)
        TextView emailTitleTextView;

        @BindView(R.id.datetextView)
        TextView datetextView;

        @BindView(R.id.rightArrow)
        TextView rightArrow;

        @BindView(R.id.readStatusTextView)
        TextView readStatusTextView;

        @BindView(R.id.reciverNameTextView)
        TextView reciverNameTextView;

        public MailHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
