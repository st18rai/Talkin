package com.internship.droidz.talkin.ui.activity.users;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.internship.droidz.talkin.R;

/**
 * Created by st18r on 19.02.2017.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private String userName;
    private int[] imageIds;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout linearLayout;

        public ViewHolder(LinearLayout v) {
            super(v);
            linearLayout = v;
        }
    }

    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_users, parent, false);
        return new UsersAdapter.ViewHolder(linearLayout);

    }

    @Override
    public void onBindViewHolder(UsersAdapter.ViewHolder holder, int position) {

        LinearLayout linearLayout = holder.linearLayout;

        ImageView imageView = (ImageView) linearLayout.findViewById(R.id.imageViewUsersItem);
        TextView textView = (TextView) linearLayout.findViewById(R.id.textViewUsersItem);
    }

    @Override
    public int getItemCount() {
        return 12;
    }
}
