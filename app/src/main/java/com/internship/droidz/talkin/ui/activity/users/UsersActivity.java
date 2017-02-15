package com.internship.droidz.talkin.ui.activity.users;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.presentation.view.users.UsersView;
import com.internship.droidz.talkin.presentation.presenter.users.UsersPresenter;


import com.arellomobile.mvp.presenter.InjectPresenter;

public class UsersActivity extends MvpAppCompatActivity implements UsersView {
    public static final String TAG = "UsersActivity";
    @InjectPresenter
    UsersPresenter mUsersPresenter;

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, UsersActivity.class);

        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }
}
