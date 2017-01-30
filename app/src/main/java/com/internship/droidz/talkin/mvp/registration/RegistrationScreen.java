package com.internship.droidz.talkin.mvp.registration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;

import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.data.model.SessionModel;
import com.internship.droidz.talkin.data.model.UserModel;
import com.internship.droidz.talkin.data.web.ApiRetrofit;
import com.internship.droidz.talkin.data.web.WebUtils;
import com.internship.droidz.talkin.data.web.requests.RegistrationRequest;
import com.internship.droidz.talkin.data.web.requests.SessionRequest;
import com.internship.droidz.talkin.data.web.requests.UserSignUpRequest;
import com.jakewharton.rxbinding.view.RxView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RegistrationScreen extends AppCompatActivity implements RegistrationContract.RegistrationView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        AppCompatButton signUpButtonReg = (AppCompatButton) findViewById(R.id.signUpButtonReg);
        EditText email = (EditText) findViewById(R.id.emailEditTextReg);
        EditText password = (EditText) findViewById(R.id.passwordEditTextReg);
        EditText passwordConfirm = (EditText) findViewById(R.id.confirmPasswordEditText);
        EditText fullName = (EditText) findViewById(R.id.nameEditText);
        EditText phone = (EditText) findViewById(R.id.phoneEditText);
        EditText website = (EditText) findViewById(R.id.siteEditText);




        Subscription SubscrBtnSignIn = RxView.clicks(signUpButtonReg).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                int nonce= WebUtils.getNonce();
                long timestamp = System.currentTimeMillis()/1000l;
                ApiRetrofit.getRetrofitApi().getUserService()
                        .getSession(new SessionRequest(ApiRetrofit.APP_ID,ApiRetrofit.APP_AUTH_KEY,
                                String.valueOf(nonce),String.valueOf(timestamp),WebUtils.calcSignature(nonce,timestamp)))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<SessionModel>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i("error reg", "msg: "+e.getMessage());
                            }

                            @Override
                            public void onNext(SessionModel sessionModel) {


                                UserSignUpRequest requestReg = new UserSignUpRequest(email.getText().toString(),
                                        password.getText().toString(),
                                        fullName.getText().toString(),
                                        phone.getText().toString(),
                                        website.getText().toString());
                                RegistrationRequest request = new RegistrationRequest(requestReg);
                                ApiRetrofit.getRetrofitApi().getUserService().requestSignUp(request,sessionModel.getSession().getToken())
                                         .subscribeOn(Schedulers.io())
                                         .observeOn(AndroidSchedulers.mainThread())
                                         .subscribe(new Subscriber<UserModel>() {
                                    @Override
                                    public void onCompleted() {
                                        Log.i("registration","registered");

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.i("registration","failed :( "+ e.getMessage());
                                    }

                                    @Override
                                    public void onNext(UserModel userModel) {
                                        Log.i("user_id",userModel.getUser().getId().toString());
                                    }
                                });
                            }
                        });
            }
        });


    }
}
