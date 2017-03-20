package com.internship.droidz.talkin.presentation.presenter.chats;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.internship.droidz.talkin.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Koroqe on 20-Mar-17.
 */

public class ChatsRecyclerAdapter extends RecyclerView.Adapter<ChatsRecyclerAdapter.CustomViewHolder> {


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        CircleImageView userPhoto;
        TextView chatName;
        TextView lastUserName;
        TextView lastMessage;
        TextView lastTime;

        public CustomViewHolder(View itemView) {

            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            userPhoto = (CircleImageView) itemView.findViewById(R.id.imageViewTabsLastUser);
            chatName = (TextView) itemView.findViewById(R.id.textViewTabsChatName);
            lastUserName = (TextView) itemView.findViewById(R.id.textViewTabsUserName);
            lastMessage = (TextView) itemView.findViewById(R.id.textViewTabsLastMessage);
            lastTime = (TextView) itemView.findViewById(R.id.textViewTabsLastTime);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
