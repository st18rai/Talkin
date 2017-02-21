package com.internship.droidz.talkin.presentation.presenter.conversation;


import com.internship.droidz.talkin.model.ConversationModel;
import com.internship.droidz.talkin.presentation.view.conversation.ConversationView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class ConversationPresenter extends MvpPresenter<ConversationView> {

    ConversationModel mModel;
    ConversationView mView;

    public ConversationPresenter() {

        mModel = new ConversationModel();
        mView = getViewState();
    }
}
