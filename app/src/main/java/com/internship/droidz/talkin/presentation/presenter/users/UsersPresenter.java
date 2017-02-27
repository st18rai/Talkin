package com.internship.droidz.talkin.presentation.presenter.users;


import com.internship.droidz.talkin.model.UsersModel;
import com.internship.droidz.talkin.presentation.view.users.UsersView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class UsersPresenter extends MvpPresenter<UsersView> {

    UsersModel mModel;
    UsersView mView;

    public UsersPresenter() {

        mModel = new UsersModel();
        mView = getViewState();
    }

}
