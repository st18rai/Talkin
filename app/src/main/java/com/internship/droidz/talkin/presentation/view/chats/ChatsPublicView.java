package com.internship.droidz.talkin.presentation.view.chats;

import com.arellomobile.mvp.MvpView;

/**
 * Created by Koroqe on 13-Feb-17.
 */

public interface ChatsPublicView extends MvpView {

    void showConnectionProblemTitle();

    void addBadgeUnreadMessages();

    void showAdditionalMenu();

    void showDeleteChatDialog();

    void showDeleteChatFailureAlert();

    void navigateToConversation();

}
