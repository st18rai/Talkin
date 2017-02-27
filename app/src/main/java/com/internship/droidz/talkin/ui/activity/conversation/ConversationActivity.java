package com.internship.droidz.talkin.ui.activity.conversation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.presentation.presenter.conversation.ConversationPresenter;
import com.internship.droidz.talkin.presentation.view.conversation.ConversationView;
import com.internship.droidz.talkin.ui.activity.editChat.EditChatActivity;

public class ConversationActivity extends MvpAppCompatActivity implements ConversationView {
    public static final String TAG = "ConversationActivity";
    @InjectPresenter
    ConversationPresenter mConversationPresenter;

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, ConversationActivity.class);

        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerConversation);

        ConversationAdapter adapter = new ConversationAdapter();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_conversation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {

            startActivity(new Intent(ConversationActivity.this, EditChatActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
