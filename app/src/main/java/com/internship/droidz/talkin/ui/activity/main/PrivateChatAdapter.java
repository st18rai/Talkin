package com.internship.droidz.talkin.ui.activity.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.internship.droidz.talkin.R;

import java.util.ArrayList;

/**
 * Created by st18r on 16.02.2017.
 */

public class PrivateChatAdapter extends RecyclerView.Adapter<PrivateChatAdapter.ViewHolder> {

    private ArrayList<String> chatName;
    private ArrayList<String> userName;
    private ArrayList<String> lastMessage;
    private ArrayList<String> lastTime;
    private ArrayList<Integer> imageIds;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private SwipeLayout swipeLayout;

        public ViewHolder(SwipeLayout v) {
            super(v);
            swipeLayout = v;
        }
    }

    public PrivateChatAdapter(ArrayList<String> chatName, ArrayList<String> userName, ArrayList<String> lastMessage, ArrayList<String> lastTime, ArrayList<Integer> imageIds) {
        this.chatName = chatName;
        this.userName = userName;
        this.lastMessage = lastMessage;
        this.lastTime = lastTime;
        this.imageIds = imageIds;
    }

    @Override
    public PrivateChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        SwipeLayout swipeLayout = (SwipeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_tabs, parent, false);
        return new PrivateChatAdapter.ViewHolder(swipeLayout);
    }

    @Override
    public void onBindViewHolder(PrivateChatAdapter.ViewHolder holder, int position) {

        SwipeLayout swipeLayout = holder.swipeLayout;
        ImageView lastUserIV = (ImageView) swipeLayout.findViewById(R.id.imageViewTabsLastUser);
        TextView chatNameTV = (TextView) swipeLayout.findViewById(R.id.textViewTabsChatName);
        TextView userNameTV = (TextView) swipeLayout.findViewById(R.id.textViewTabsUserName);
        TextView lastMessageTV = (TextView) swipeLayout.findViewById(R.id.textViewTabsLastMessage);
        TextView lastTimeTV = (TextView) swipeLayout.findViewById(R.id.textViewTabsLastTime);
        ImageView deleteButton = (ImageView) swipeLayout.findViewById(R.id.imageViewDelete);

        chatNameTV.setText(chatName.get(position));
        userNameTV.setText(userName.get(position));
        lastMessageTV.setText(lastMessage.get(position));
        lastTimeTV.setText(lastTime.get(position));
        lastUserIV.setImageResource(imageIds.get(position));

        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, swipeLayout.findViewById(R.id.bottom_wrapper));

        deleteButton.setOnClickListener(view -> {
            // Delete item
            removeItemAt(holder.getAdapterPosition());
            Toast.makeText(view.getContext(), "Chat deleted", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return chatName.size();
    }

    private void removeItemAt(int position) {
        chatName.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, chatName.size());
    }
}