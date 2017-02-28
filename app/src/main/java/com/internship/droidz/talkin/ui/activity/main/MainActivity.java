package com.internship.droidz.talkin.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.internship.droidz.talkin.App;
import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.data.CacheSharedPreference;
import com.internship.droidz.talkin.data.web.ApiRetrofit;
import com.internship.droidz.talkin.data.web.response.user.UserSearchResponse;
import com.internship.droidz.talkin.presentation.presenter.main.MainPresenter;
import com.internship.droidz.talkin.presentation.view.main.MainView;
import com.internship.droidz.talkin.repository.UserRepository;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends MvpAppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainView {
    public static final String TAG = "MainActivity";
    @InjectPresenter
    MainPresenter mMainPresenter;

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, MainActivity.class);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

               CacheSharedPreference cache= CacheSharedPreference.getInstance(App.getApp().getApplicationContext());
                UserRepository repo = new UserRepository(ApiRetrofit.getRetrofitApi());
                repo.searchUser("full name")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<UserSearchResponse>() {
                            @Override
                            public void onCompleted() {
                                Log.i("good","vse good");
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (e instanceof HttpException) {
                                    try {
                                        Log.i("retrofit search,",((HttpException) e).response().errorBody().string());
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                                else {
                                    Log.i("error_srch_user","error: "+e.getMessage());
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onNext(UserSearchResponse userSearchResponse) {
                                   for(UserSearchResponse.Item u:userSearchResponse.getItem())
                                   {
                                       Log.i("search_user",u.getUser().getEmail());
                                   }
                            }
                        });


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
