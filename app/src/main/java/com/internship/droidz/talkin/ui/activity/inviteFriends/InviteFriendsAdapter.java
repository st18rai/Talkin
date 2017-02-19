package com.internship.droidz.talkin.ui.activity.inviteFriends;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.internship.droidz.talkin.R;

/**
 * Created by st18r on 19.02.2017.
 */

public class InviteFriendsAdapter extends RecyclerView.Adapter<InviteFriendsAdapter.ViewHolder> {

    private String userName;
    private String userEmail;
    private int[] imageIds;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout linearLayout;

        public ViewHolder(LinearLayout v) {
            super(v);
            linearLayout = v;
        }
    }

    @Override
    public InviteFriendsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_invite_friends, parent, false);
        return new InviteFriendsAdapter.ViewHolder(linearLayout);

    }

    @Override
    public void onBindViewHolder(InviteFriendsAdapter.ViewHolder holder, int position) {

        LinearLayout linearLayout = holder.linearLayout;

        ImageView imageView = (ImageView) linearLayout.findViewById(R.id.imageViewInviteFriendsItem);
        TextView textViewName = (TextView) linearLayout.findViewById(R.id.textViewNameInviteFriendsItem);
        TextView textViewEmail = (TextView) linearLayout.findViewById(R.id.textViewEmailInviteFriendsItem);
        CheckBox checkBox = (CheckBox) linearLayout.findViewById(R.id.checkBoxInviteFriendsItem);
    }

    @Override
    public int getItemCount() {
        return 8;
    }
}
