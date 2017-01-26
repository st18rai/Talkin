package com.internship.droidz.talkin.mvp.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.data.web.ApiRetrofit;
import com.internship.droidz.talkin.data.web.requests.SessionRequest;
import com.internship.droidz.talkin.data.web.requests.SessionWithAuthRequest;
import com.internship.droidz.talkin.mvp.registration.RegistrationScreen;
import com.jakewharton.rxbinding.view.RxView;

import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class LoginScreen extends AppCompatActivity  implements LoginContract.LoginView{

    EditText email;
    EditText password;
    AppCompatButton btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        email = (EditText) findViewById(R.id.emailEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        btnSignIn = (AppCompatButton) findViewById(R.id.signInButton);

        signInButtonState();


        AppCompatButton btnSignUp = (AppCompatButton) findViewById(R.id.signUpButton);
        Subscription SubscrBtnSignUp = RxView.clicks(btnSignUp).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Log.i("rx login",email.getText().toString());
                Log.i("rx password",password.getText().toString());
                navigateToRegistrationScreen();

            }
        });

        Subscription SubscrBtnSignIn = RxView.clicks(btnSignIn).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                SessionRequest request = new SessionRequest();
                Log.i("rx login",email.getText().toString());
                Log.i("rx password",password.getText().toString());
                ApiRetrofit.getRetrofitApi().getUserService()
                        .getSession(new SessionRequest())
                       // .requestLogin(new SessionWithAuthRequest(email.getText().toString(),password.getText().toString()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(sessionModel -> {
                            Log.i("dbg",sessionModel.toString());
                            Log.i("works", String.valueOf(sessionModel.getSession().getUser_id()));
                        })
                        .doOnError(throwable -> Log.i("not works","Error: "+throwable.getMessage()))
                        .subscribe();


            }
        });
    }

    @Override
    public void signInButtonState() {

        btnSignIn.setEnabled(false);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(email.getText().toString()))
                    btnSignIn.setEnabled(false);
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(email.getText().toString()))
                    btnSignIn.setEnabled(false);
                else
                    btnSignIn.setEnabled(true);
            }
        });
    }

    @Override
    public void navigateToRegistrationScreen() {

        Intent intent = new Intent(LoginScreen.this, RegistrationScreen.class);
        startActivity(intent);
    }
}
