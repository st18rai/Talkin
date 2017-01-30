package com.internship.droidz.talkin.mvp.login;

import android.content.Intent;
import android.database.Observable;
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
import com.internship.droidz.talkin.data.model.SessionModel;
import com.internship.droidz.talkin.data.web.ApiRetrofit;
import com.internship.droidz.talkin.data.web.WebUtils;
import com.internship.droidz.talkin.data.web.requests.SessionRequest;
import com.internship.droidz.talkin.data.web.requests.SessionWithAuthRequest;
import com.internship.droidz.talkin.data.web.requests.UserRequestModel;
import com.internship.droidz.talkin.mvp.registration.RegistrationScreen;
import com.jakewharton.rxbinding.view.RxView;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
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

                int nonce= WebUtils.getNonce();
                long timestamp = System.currentTimeMillis()/1000l;
                ApiRetrofit.getRetrofitApi().getUserService()
                        .getSession(new SessionRequest(ApiRetrofit.APP_ID,ApiRetrofit.APP_AUTH_KEY,
                               String.valueOf(nonce),String.valueOf(timestamp),WebUtils.calcSignature(nonce,timestamp)))

                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                      //  .subscribe(sessionModel -> {})
                        .subscribe(new Subscriber<SessionModel>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                    Log.i("error","message: "+e.getMessage());
                                if (e instanceof HttpException) {
                                    try
                                    {
                                        Log.i("retrofit error,",((HttpException) e).response().errorBody().string());
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onNext(SessionModel sessionModel) {
                                int nonce= WebUtils.getNonce();
                                long timestamp = System.currentTimeMillis()/1000l;
                                SessionWithAuthRequest request=  new SessionWithAuthRequest(
                                        new UserRequestModel(email.getText().toString(), password.getText().toString()),
                                        ApiRetrofit.APP_ID,
                                        ApiRetrofit.APP_AUTH_KEY,
                                        String.valueOf(nonce),
                                        String.valueOf(timestamp),
                                        WebUtils.calcSignature(nonce, timestamp,
                                                email.getText().toString(),
                                                password.getText().toString()));

                                        ApiRetrofit.getRetrofitApi().getUserService()
                                            .getSessionWithAuth(request,sessionModel.getSession().getToken())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Subscriber<SessionModel>() {
                                            @Override
                                            public void onCompleted() {
                                               Log.i("debug","logged in");
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                if (e instanceof HttpException) {
                                                    try
                                                    {
                                                        Log.i("retrofit error,",((HttpException) e).response().errorBody().string());
                                                    } catch (IOException e1) {
                                                        e1.printStackTrace();
                                                    }
                                                }
                                                else
                                                {
                                                    Log.i("error","some error");
                                                }

                                            }

                                            @Override
                                            public void onNext(SessionModel sessionModel) {
                                                Log.i("user",String.valueOf(sessionModel.getSession().getUser_id()));
                                                Log.i("token",String.valueOf(sessionModel.getSession().getToken()));
                                            }
                                        });
                            }
                        });
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
