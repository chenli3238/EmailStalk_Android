package com.imark.emailstalk.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imark.emailstalk.AccountsActivity;
import com.imark.emailstalk.FAQ;
import com.imark.emailstalk.Home;
import com.imark.emailstalk.HowItWorksActivity;
import com.imark.emailstalk.LoginActivity;
import com.imark.emailstalk.Model.NavigationModel;
import com.imark.emailstalk.PreferenceActivity;
import com.imark.emailstalk.R;
import com.imark.emailstalk.ReportaBugActivity;
import com.imark.emailstalk.SettingActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ListViewHolder> {
    private List<NavigationModel> navList;
    private Activity context;
    public DrawerLayout drawerLayout;


    public NavigationAdapter(List<NavigationModel> list, Activity activity) {
        this.navList = list;
        this.context = activity;
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
        Home home = new Home();

        public ListViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this, itemView);
            NavigationAdapter = new NavigationAdapter(drawerLayout);
        }

        @OnClick(R.id.navTitle)
        void clickOnNavItems() {
            int position = getAdapterPosition();
            switch (position) {
                case 0:
                    context.startActivity(new Intent(context, Home.class));
                    break;
                case 1:
                    context.startActivity(new Intent(context, AccountsActivity.class));
                    break;
                case 2:
                    context.startActivity(new Intent(context, HowItWorksActivity.class));
                    break;
                case 3:
                    Intent intentArr = new Intent(context, PreferenceActivity.class);
                    context.startActivity(intentArr);
                    break;
                case 4:
                    Intent intentNet = new Intent(context, FAQ.class);
                    context.startActivity(intentNet);
                    break;
                case 5:
//                    context.startActivity(new Intent(context, AccountsActivity.class));
                    break;
                case 6:
                    context.startActivity(new Intent(context, ReportaBugActivity.class));
                    break;
                case 7:
                    context.startActivity(new Intent(context, SettingActivity.class));
                    break;
                case 8:
                    buttonLogout();
                    break;
            }
        }

        void buttonLogout() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.logout_text)
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            boolean flag =home.callUnRegisterToken();
                            if(flag){
                                Intent intent = new Intent(context, LoginActivity.class);
                                context.startActivity(intent);
                                SharedPreferences.Editor editorLogin = context.getSharedPreferences("UserLogin", MODE_PRIVATE).edit();
                                editorLogin.clear();
                                editorLogin.apply();
                                SharedPreferences.Editor editor = context.getSharedPreferences("Theme", MODE_PRIVATE).edit();
                                editor.clear();
                                editor.apply();
                            }else {
                                dialog.cancel();
                            }

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
            alert.setTitle("Email Stalk");
            alert.show();
        }
    }}