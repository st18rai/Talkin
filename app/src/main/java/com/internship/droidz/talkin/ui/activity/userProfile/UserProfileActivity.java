package com.internship.droidz.talkin.ui.activity.userProfile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.presentation.presenter.userProfile.UserProfilePresenter;
import com.internship.droidz.talkin.presentation.view.userProfile.UserProfileView;

public class UserProfileActivity extends MvpAppCompatActivity implements UserProfileView {
    public static final String TAG = "UserProfileActivity";
    @InjectPresenter
    UserProfilePresenter mUserProfilePresenter;

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, UserProfileActivity.class);

        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }
}
