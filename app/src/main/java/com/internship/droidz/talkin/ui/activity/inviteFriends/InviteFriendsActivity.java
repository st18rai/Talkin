package com.internship.droidz.talkin.ui.activity.inviteFriends;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.presentation.presenter.inviteFriends.InviteFriendsPresenter;
import com.internship.droidz.talkin.presentation.view.inviteFriends.InviteFriendsView;

public class InviteFriendsActivity extends MvpAppCompatActivity implements InviteFriendsView {
    public static final String TAG = "InviteFriendsActivity";
    @InjectPresenter
    InviteFriendsPresenter mInviteFriendsPresenter;

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, InviteFriendsActivity.class);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewInviteFriends);

        InviteFriendsAdapter adapter = new InviteFriendsAdapter();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }
}
