package com.internship.droidz.talkin.presentation.view.createChat;

import com.arellomobile.mvp.MvpView;

public interface CreateChatView extends MvpView {

    void createChat();

    void onErrorEmptyChatName();

    void onErrorNoPartisipants();
}
