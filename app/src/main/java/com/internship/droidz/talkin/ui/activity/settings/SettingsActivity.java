package com.internship.droidz.talkin.ui.activity.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.presentation.view.settings.SettingsView;
import com.internship.droidz.talkin.presentation.presenter.settings.SettingsPresenter;


import com.arellomobile.mvp.presenter.InjectPresenter;

public class SettingsActivity extends MvpAppCompatActivity implements SettingsView {
    public static final String TAG = "SettingsActivity";
    @InjectPresenter
    SettingsPresenter mSettingsPresenter;

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);

        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }
}
