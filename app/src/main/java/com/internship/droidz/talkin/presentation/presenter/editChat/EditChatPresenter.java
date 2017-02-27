package com.internship.droidz.talkin.presentation.presenter.editChat;


import com.internship.droidz.talkin.model.EditChatModel;
import com.internship.droidz.talkin.presentation.view.editChat.EditChatView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class EditChatPresenter extends MvpPresenter<EditChatView> {

    EditChatModel mModel;
    EditChatView mView;

    public EditChatPresenter() {

        mModel = new EditChatModel();
        mView = getViewState();
    }
}
