package com.internship.droidz.talkin.presentation.presenter.inviteFriends;


import com.internship.droidz.talkin.model.InviteFriendsModel;
import com.internship.droidz.talkin.presentation.view.inviteFriends.InviteFriendsView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class InviteFriendsPresenter extends MvpPresenter<InviteFriendsView> {

    InviteFriendsModel mModel;
    InviteFriendsView mView;

    public InviteFriendsPresenter() {

        mModel = new InviteFriendsModel();
        mView = getViewState();
    }

}
