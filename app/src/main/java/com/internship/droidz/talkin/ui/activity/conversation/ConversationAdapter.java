package com.internship.droidz.talkin.ui.activity.conversation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.internship.droidz.talkin.R;

/**
 * Created by st18r on 21.02.2017.
 */

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ViewHolder> {

    private String message;
    private String name;
    private String time;
    private int[] imageIds;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout relativeLayout;

        public ViewHolder(RelativeLayout v) {
            super(v);
            relativeLayout = v;
        }
    }

    @Override
    public ConversationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RelativeLayout rl = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_conversation_right, parent, false);
        return new ViewHolder(rl);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        RelativeLayout relativeLayout = holder.relativeLayout;
        ImageView userPic = (ImageView) relativeLayout.findViewById(R.id.imageViewUserConversation);
        TextView message = (TextView) relativeLayout.findViewById(R.id.textViewMessageConversation);
        TextView name = (TextView) relativeLayout.findViewById(R.id.textViewNameConversation);
        TextView time = (TextView) relativeLayout.findViewById(R.id.textViewTimeConversation);
    }

    @Override
    public int getItemCount() {
        return 9;
    }
}
