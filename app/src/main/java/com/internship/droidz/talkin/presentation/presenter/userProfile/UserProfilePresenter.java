package com.internship.droidz.talkin.presentation.presenter.userProfile;


import com.internship.droidz.talkin.model.UserProfileModel;
import com.internship.droidz.talkin.presentation.view.userProfile.UserProfileView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class UserProfilePresenter extends MvpPresenter<UserProfileView> {

    UserProfileModel mModel;
    UserProfileView mView;

    public UserProfilePresenter() {

        mModel = new UserProfileModel();
        mView = getViewState();
    }
}
