package com.internship.droidz.talkin.presentation.presenter.main;


import com.internship.droidz.talkin.model.MainModel;
import com.internship.droidz.talkin.presentation.view.main.MainView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    MainModel mModel;
    MainView mView;

    public MainPresenter() {
        mModel = new MainModel();
        mView = getViewState();;
    }
}
