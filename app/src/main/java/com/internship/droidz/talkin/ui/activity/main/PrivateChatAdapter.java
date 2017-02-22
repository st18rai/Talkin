package com.internship.droidz.talkin.ui.activity.main;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.internship.droidz.talkin.R;

/**
 * Created by st18r on 16.02.2017.
 */

public class PrivateChatAdapter extends RecyclerView.Adapter<PrivateChatAdapter.ViewHolder> {

    private String chatName;
    private String userName;
    private String lastMessage;
    private String lastTime;
    private int[] imageIds;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    @Override
    public PrivateChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_tabs, parent, false);
        return new PrivateChatAdapter.ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(PrivateChatAdapter.ViewHolder holder, int position) {

        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.imageViewTabsLastUser);
        TextView chatName = (TextView) cardView.findViewById(R.id.textViewTabsChatName);
        TextView userName = (TextView) cardView.findViewById(R.id.textViewTabsUserName);
        TextView lastMessage = (TextView) cardView.findViewById(R.id.textViewTabsLastMessage);
        TextView lastTime = (TextView) cardView.findViewById(R.id.textViewTabsLastTime);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
