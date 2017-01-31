package com.internship.droidz.talkin.mvp.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.data.model.SessionModel;
import com.internship.droidz.talkin.data.web.ApiRetrofit;
import com.internship.droidz.talkin.data.web.WebUtils;
import com.internship.droidz.talkin.data.web.requests.SessionRequest;
import com.internship.droidz.talkin.data.web.requests.SessionWithAuthRequest;
import com.internship.droidz.talkin.data.web.requests.UserRequestModel;
import com.internship.droidz.talkin.mvp.main.MainScreen;
import com.internship.droidz.talkin.mvp.registration.RegistrationScreen;
import com.jakewharton.rxbinding.view.RxView;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class LoginScreen extends AppCompatActivity  implements LoginContract.LoginView{

    EditText email;
    EditText password;
    AppCompatButton btnSignIn;
    AppCompatButton btnSignUp;
    LoginContract.LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        presenter = new LoginPresenterImpl(new LoginModelImpl(), this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        email = (EditText) findViewById(R.id.emailEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        btnSignIn = (AppCompatButton) findViewById(R.id.signInButton);
        btnSignUp = (AppCompatButton) findViewById(R.id.signUpButton);
        signInButtonState();

        Subscription buttonSub = RxView.clicks(btnSignUp).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Log.i("rx login",email.getText().toString());
                Log.i("rx password",password.getText().toString());
                navigateToRegistrationScreen();
            }
        });

        TextView tvForgotPassword = (TextView) findViewById(R.id.forgotPasswordTextView);
        Subscription tvSub = RxView.clicks(tvForgotPassword).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                forgotPassword();
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
                                                        Toast.makeText(LoginScreen.this,"Wrong login or password",Toast.LENGTH_LONG).show();
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
                                                navigationToMainScreen();
                                            }
                                        });
                            }
                        });
            }
        });
    }



    @Override
    protected void onStop() {
        super.onStop();
        presenter.checkAndStartTimer(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStop();
        presenter.stopTimer(getApplicationContext());
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
    public void forgotPassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        LayoutInflater inflater = getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.forgot_password_dialog, null))
                .setTitle(R.string.forgot_password_dialog_title)
                .setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton(R.string.dialog_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.create();
        builder.show();
    }

    @Override
    public void navigateToRegistrationScreen() {
        Intent intent = new Intent(LoginScreen.this, RegistrationScreen.class);
        startActivity(intent);
    }

    @Override
    public void navigationToMainScreen() {
        Intent intent = new Intent(LoginScreen.this, MainScreen.class);
        startActivity(intent);
    }
}
