package com.internship.droidz.talkin.presentation.presenter.chats;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.droidz.talkin.model.ChatsModel;
import com.internship.droidz.talkin.presentation.view.chats.ChatsView;

/**
 * Created by Koroqe on 19-Mar-17.
 */

@InjectViewState
public class ChatsPresenter extends MvpPresenter<ChatsView> {

    ChatsModel mModel;

    public ChatsPresenter(ChatsModel mModel) {

        this.mModel = mModel;
    }

    public void showConnectionProblemTitle() {
        
        getViewState().showConnectionProblemTitle();
    }
}
