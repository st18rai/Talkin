package com.internship.droidz.talkin.presentation.presenter.createChat;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.droidz.talkin.data.web.response.chat.DialogModel;
import com.internship.droidz.talkin.model.CreateChatModel;
import com.internship.droidz.talkin.presentation.view.createChat.CreateChatView;
import com.internship.droidz.talkin.ui.activity.createChat.CreateChatAdapter;
import com.internship.droidz.talkin.ui.activity.inviteFriends.InviteFriendsAdapter;

import java.util.ArrayList;
import java.util.List;

@InjectViewState
public class CreateChatPresenter extends MvpPresenter<CreateChatView> {

    CreateChatModel mModel;

    public CreateChatPresenter() {

        mModel = new CreateChatModel();
    }

    public void createChat(boolean isChatPublic, String chatName, CreateChatAdapter createChatAdapter) {

        List<Integer> participants = createChatAdapter.getSelectedUsersIdList();

        DialogModel.DialogType dialogType;
        if (isChatPublic) {
            dialogType = DialogModel.DialogType.PUBLIC_GROUP;
        } else {
            if (participants.size() > 1) {
                dialogType = DialogModel.DialogType.GROUP;
            } else {
                dialogType = DialogModel.DialogType.PRIVATE;
            }
        }

        if (dialogType == DialogModel.DialogType.GROUP) {
            if (chatName.trim().isEmpty()) {
                getViewState().onErrorEmptyChatName();
                return;
            }
        }
        if (dialogType == DialogModel.DialogType.PRIVATE) {
            if (participants.size() < 1) {
                getViewState().onErrorNoPartisipants();
                return;
            }
        }
        //createChat
    }
}
