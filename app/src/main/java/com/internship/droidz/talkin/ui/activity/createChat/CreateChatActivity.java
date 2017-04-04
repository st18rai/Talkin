package com.internship.droidz.talkin.ui.activity.createChat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.presentation.presenter.createChat.CreateChatPresenter;
import com.internship.droidz.talkin.presentation.view.createChat.CreateChatView;
import com.internship.droidz.talkin.ui.activity.inviteFriends.InviteFriendsAdapter;

import org.jivesoftware.smackx.muc.packet.MUCUser;

public class CreateChatActivity extends MvpAppCompatActivity implements CreateChatView {
    public static final String TAG = "CreateChatActivity";
    @InjectPresenter
    CreateChatPresenter mCreateChatPresenter;

    Button butCreateChat;
    RadioGroup rgPrivatePublic;
    EditText etChatName;

    CreateChatAdapter createChatAdapter;

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

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewCreateChat);

        CreateChatAdapter adapter = new CreateChatAdapter();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        butCreateChat = (Button) findViewById(R.id.buttonCreateChat);
        rgPrivatePublic = (RadioGroup) findViewById(R.id.rgPrivatePublic);
        etChatName = (EditText) findViewById(R.id.editTextCreateChatChatName);
        createChatAdapter = new CreateChatAdapter();

        butCreateChat.setOnClickListener((view) -> {
                createChat();
            });

        rgPrivatePublic.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonPublic) {
                adapter.setUsersEnabled();
            } else {
                adapter.setUsersDisabled();
            }
        });
        rgPrivatePublic.check(R.id.radioButtonPublic);

    }

    @Override
    public void createChat() {

        String chatName = etChatName.getText().toString();
        boolean isChatPublic = true;
        if (rgPrivatePublic.getCheckedRadioButtonId() == R.id.radioButtonPrivate) {
            isChatPublic = false;
        }

        mCreateChatPresenter.createChat(isChatPublic, chatName, createChatAdapter);
    }

    @Override
    public void onErrorEmptyChatName() {

    }

    @Override
    public void onErrorNoPartisipants() {

    }

}
