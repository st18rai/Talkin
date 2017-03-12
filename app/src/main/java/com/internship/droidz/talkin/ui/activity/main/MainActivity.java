package com.internship.droidz.talkin.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.presentation.presenter.main.MainPresenter;
import com.internship.droidz.talkin.presentation.view.main.MainView;
import com.internship.droidz.talkin.ui.activity.createChat.CreateChatActivity;
import com.internship.droidz.talkin.ui.activity.inviteFriends.InviteFriendsActivity;
import com.internship.droidz.talkin.ui.activity.login.LoginActivity;
import com.internship.droidz.talkin.ui.activity.settings.SettingsActivity;
import com.internship.droidz.talkin.ui.activity.userProfile.UserProfileActivity;
import com.internship.droidz.talkin.ui.activity.users.UsersActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends MvpAppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainView {
    public static final String TAG = "MainActivity";
    @InjectPresenter
    MainPresenter mMainPresenter;

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, MainActivity.class);

        return intent;
    }

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private ViewStub stub;

    Boolean chatsExist = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        stub = (ViewStub) findViewById(R.id.layout_stub);

        if (chatsExist) {
            stub.setLayoutResource(R.layout.chats_list_layout);
            View inflated = stub.inflate();

            viewPager = (CustomViewPager) findViewById(R.id.container);
            setupViewPager(viewPager);
            tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
        } else {
            stub.setLayoutResource(R.layout.app_bar_main_screen);
            View inflated = stub.inflate();

            inviteFriendsTextClick();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToCreateChat();
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        CircleImageView userPic = (CircleImageView) header.findViewById(R.id.imageViewDrawerUserPic);

        userPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_chat) {
            navigateToCreateChat();

        } else if (id == R.id.nav_users) {
            navigateToUsers();

        } else if (id == R.id.nav_friends) {
            navigateToInviteFriends();

        } else if (id == R.id.nav_settings) {
            navigateToSettings();

        } else if (id == R.id.nav_logout) {
            showLogOutDialog();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void inviteFriendsTextClick() {

        SpannableString spannableString = new SpannableString(getResources().getString(R.string.main_screen_text));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                navigateToInviteFriends();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };
        spannableString.setSpan(clickableSpan, 37, 51, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        TextView inviteFriendsTextView = (TextView) findViewById(R.id.textViewMain);
        inviteFriendsTextView.setText(spannableString);
        inviteFriendsTextView.setMovementMethod(LinkMovementMethod.getInstance());
        inviteFriendsTextView.setHighlightColor(Color.TRANSPARENT);
    }

    private void setupViewPager(CustomViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PublicChatFragment(), "PUBLIC");
        adapter.addFragment(new PrivateChatFragment(), "PRIVATE");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void navigateToCreateChat() {
        startActivity(new Intent(MainActivity.this, CreateChatActivity.class));
    }

    public void navigateToInviteFriends() {
        startActivity(new Intent(MainActivity.this, InviteFriendsActivity.class));
    }

    public void navigateToUsers() {
        startActivity(new Intent(MainActivity.this, UsersActivity.class));
    }

    public void navigateToSettings() {
        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
    }
    public void showLogOutDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to sign out?")
                .setPositiveButton("Yes", (dialog1, id) -> {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                })
                .setNegativeButton("No", (dialog12, id) ->{

                });
        builder.create();
        builder.show();
    }
}
