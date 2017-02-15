package com.internship.droidz.talkin.ui.activity.createChat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.presentation.presenter.createChat.CreateChatPresenter;
import com.internship.droidz.talkin.presentation.view.createChat.CreateChatView;

public class CreateChatActivity extends MvpAppCompatActivity implements CreateChatView {
    public static final String TAG = "CreateChatActivity";
    @InjectPresenter
    CreateChatPresenter mCreateChatPresenter;

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, CreateChatActivity.class);

        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }
}
