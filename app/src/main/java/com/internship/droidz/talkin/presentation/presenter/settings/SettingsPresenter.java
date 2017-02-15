package com.internship.droidz.talkin.presentation.presenter.settings;


import com.internship.droidz.talkin.model.SettingsModel;
import com.internship.droidz.talkin.presentation.view.settings.SettingsView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class SettingsPresenter extends MvpPresenter<SettingsView> {

    SettingsModel mModel;
    SettingsView mView;

    public SettingsPresenter() {

        mModel = new SettingsModel();
        mView = getViewState();
    }
}
