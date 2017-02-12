package com.internship.droidz.talkin.presentation.presenter.main;


import com.internship.droidz.talkin.model.MainModel;
import com.internship.droidz.talkin.presentation.view.main.MainView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    MainModel model;
    MainView view = getViewState();

    public MainPresenter(MainModel model, MainView view) {
        this.model = model;
        this.view = view;
    }
}
