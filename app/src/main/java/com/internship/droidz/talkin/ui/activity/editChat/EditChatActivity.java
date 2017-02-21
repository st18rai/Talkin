package com.internship.droidz.talkin.ui.activity.editChat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.presentation.presenter.editChat.EditChatPresenter;
import com.internship.droidz.talkin.presentation.view.editChat.EditChatView;

public class EditChatActivity extends MvpAppCompatActivity implements EditChatView {
    public static final String TAG = "EditChatActivity";
    @InjectPresenter
    EditChatPresenter mEditChatPresenter;

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, EditChatActivity.class);

        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewEditChat);

        EditChatAdapter adapter = new EditChatAdapter();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }
}
