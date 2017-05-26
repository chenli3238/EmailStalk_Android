package com.imark.emailstalk.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imark.emailstalk.AllMailFragment;
import com.imark.emailstalk.R;
import com.imark.emailstalk.ReadFragment;
import com.imark.emailstalk.UnReadFragment;

import java.util.ArrayList;
import java.util.List;

import APIResponse.EmailObject;
import APIResponse.ToCcResponse;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MailAdapter extends RecyclerView.Adapter<MailAdapter.MailHolder> {
    Context context;
    Fragment fragment;
    List<EmailObject> commonRowArray;

    public MailAdapter(Fragment fragment, List<EmailObject> commonRowArray) {
        this.fragment = fragment;
        this.commonRowArray = commonRowArray;
    }

    @Override
    public MailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new MailHolder(view);
    }

    @Override
    public void onBindViewHolder(MailHolder holder, int position) {
        EmailObject emailObject = commonRowArray.get(position);
        holder.emailTitleTextView.setText(emailObject.getEmailTitle());
        holder.datetextView.setText(emailObject.getDateTime());
        holder.readStatusTextView.setText("Read " + emailObject.getEmailRead());
        List<ToCcResponse> toCcResponseList = emailObject.getToResponses();
        String toNames;
        List<String> ids = new ArrayList<String>();
        for (int i = 0; i < toCcResponseList.size(); i++) {
            toNames = toCcResponseList.get(i).getUserName();
            ids.add(toNames);
        }
        holder.reciverNameTextView.setText("To: "+android.text.TextUtils.join(",", ids));
        if (fragment instanceof ReadFragment) {
            holder.eyeImag.setSelected(true);
            holder.emailTitleTextView.setTextColor(Color.BLACK);
        } else if (fragment instanceof AllMailFragment) {
            int read = emailObject.getIsRead();
            if (read == 1) {
                holder.eyeImag.setSelected(true);
                holder.emailTitleTextView.setTextColor(Color.BLACK);
            } else {
                holder.emailTitleTextView.setTextColor(Color.GRAY);
            }
        } else if (fragment instanceof UnReadFragment) {
            holder.emailTitleTextView.setTextColor(Color.GRAY);
        }
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
            ButterKnife.bind(this, itemView);
            if (fragment instanceof ReadFragment) {
                emailTitleTextView.setTextColor(R.color.black_font);
            }
        }
    }
}
