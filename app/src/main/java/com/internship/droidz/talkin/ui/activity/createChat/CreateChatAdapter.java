package com.internship.droidz.talkin.ui.activity.createChat;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.data.web.response.user.UserSearchResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by st18r on 18.02.2017.
 */

public class CreateChatAdapter extends RecyclerView.Adapter<CreateChatAdapter.ViewHolder> {

    private String userName;
    private int[] imageIds;
    private List<Integer> mSelectedUsersIdList;
    private List<UserSearchResponse.User> mUsersList;

    public CreateChatAdapter() {

        this.mSelectedUsersIdList = new ArrayList<>();
    }

    public void setUsersEnabled() {
    }

    public void setUsersDisabled() {
    }

    @Override
    public CreateChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_members_list, parent, false);
        return new CreateChatAdapter.ViewHolder(linearLayout);

    }

    @Override
    public void onBindViewHolder(CreateChatAdapter.ViewHolder holder, int position) {

        UserSearchResponse.User currentUser = mUsersList.get(position);

        holder.checkBox.setEnabled(true); //TODO enable depends on public/private
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            //currentUser.setSelected() //TODO implement select flag to user
            if (isChecked) {
                mSelectedUsersIdList.add(currentUser.getId());
                holder.tvName.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.black));
            } else {
                holder.tvName.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.gray));
                int removeIndex = mSelectedUsersIdList.indexOf(currentUser.getId());
                if (removeIndex != -1) {
                    mSelectedUsersIdList.remove(removeIndex);
                }
            }
        });

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivUserPic;
        TextView tvName;
        CheckBox checkBox;

        public ViewHolder(LinearLayout v) {

            super(v);
            ivUserPic = (ImageView) v.findViewById(R.id.imageViewMembersListItem);
            tvName = (TextView) v.findViewById(R.id.textViewMembersListItem);
            checkBox = (CheckBox) v.findViewById(R.id.checkBoxMembersListItem);
        }
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public List<Integer> getSelectedUsersIdList() {

        return mSelectedUsersIdList;
    }
}
