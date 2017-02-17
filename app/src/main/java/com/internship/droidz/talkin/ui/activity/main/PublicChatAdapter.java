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

public class PublicChatAdapter extends RecyclerView.Adapter<PublicChatAdapter.ViewHolder> {

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
    public PublicChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.lastUserImageView);
        TextView chatName = (TextView) cardView.findViewById(R.id.chatNameTextView);
        TextView userName = (TextView) cardView.findViewById(R.id.userNameTextView);
        TextView lastMessage = (TextView) cardView.findViewById(R.id.lastMessageTextView);
        TextView lastTime = (TextView) cardView.findViewById(R.id.lastTimeTextView);
    }

    @Override
    public int getItemCount() {
        return 9;
    }
}
