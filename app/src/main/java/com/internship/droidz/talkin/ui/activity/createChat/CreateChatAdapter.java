package com.internship.droidz.talkin.ui.activity.createChat;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.internship.droidz.talkin.R;

/**
 * Created by st18r on 18.02.2017.
 */

public class CreateChatAdapter extends RecyclerView.Adapter<CreateChatAdapter.ViewHolder> {

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
    public CreateChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_members_list, parent, false);
        return new CreateChatAdapter.ViewHolder(linearLayout);

    }

    @Override
    public void onBindViewHolder(CreateChatAdapter.ViewHolder holder, int position) {

        LinearLayout linearLayout = holder.linearLayout;

        ImageView imageView = (ImageView) linearLayout.findViewById(R.id.imageViewMembersListItem);
        TextView textView = (TextView) linearLayout.findViewById(R.id.textViewMembersListItem);
        CheckBox checkBox = (CheckBox) linearLayout.findViewById(R.id.checkBoxMembersListItem);
    }

    @Override
    public int getItemCount() {
        return 8;
    }
}
