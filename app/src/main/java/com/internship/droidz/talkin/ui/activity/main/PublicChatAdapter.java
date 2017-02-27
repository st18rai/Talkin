package com.internship.droidz.talkin.ui.activity.main;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
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

        private SwipeLayout swipeLayout;

        public ViewHolder(SwipeLayout v) {
            super(v);
            swipeLayout = v;
        }
    }

    @Override
    public PublicChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        SwipeLayout swipeLayout = (SwipeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_tabs, parent, false);
        return new ViewHolder(swipeLayout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SwipeLayout swipeLayout = holder.swipeLayout;
        ImageView imageView = (ImageView) swipeLayout.findViewById(R.id.imageViewTabsLastUser);
        TextView chatName = (TextView) swipeLayout.findViewById(R.id.textViewTabsChatName);
        TextView userName = (TextView) swipeLayout.findViewById(R.id.textViewTabsUserName);
        TextView lastMessage = (TextView) swipeLayout.findViewById(R.id.textViewTabsLastMessage);
        TextView lastTime = (TextView) swipeLayout.findViewById(R.id.textViewTabsLastTime);
        ImageView deleteButton = (ImageView) swipeLayout.findViewById(R.id.imageViewDelete);

        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, swipeLayout.findViewById(R.id.bottom_wrapper));
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete item
            }
        });
    }

    @Override
    public int getItemCount() {
        return 9;
    }
}
