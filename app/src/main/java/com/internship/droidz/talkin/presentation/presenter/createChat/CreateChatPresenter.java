package com.internship.droidz.talkin.presentation.presenter.createChat;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.droidz.talkin.model.CreateChatModel;
import com.internship.droidz.talkin.presentation.view.createChat.CreateChatView;

@InjectViewState
public class CreateChatPresenter extends MvpPresenter<CreateChatView> {

    CreateChatModel mModel;
    CreateChatView mView;

    public CreateChatPresenter() {

        mModel = new CreateChatModel();
        mView = getViewState();
    }
}
