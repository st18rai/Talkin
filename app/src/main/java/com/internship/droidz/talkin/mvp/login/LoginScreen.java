package com.internship.droidz.talkin.mvp.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;

import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.web.ApiService;
import com.jakewharton.rxbinding.view.RxView;

import rx.Subscription;
import rx.functions.Action1;

public class LoginScreen extends AppCompatActivity  implements LoginContract.LoginView{

    EditText email;
    EditText password;
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


        AppCompatButton btnSignUp = (AppCompatButton) findViewById(R.id.signUpButton);
        Subscription buttonSub = RxView.clicks(btnSignUp).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Log.i("rx login",email.getText().toString());
                Log.i("rx password",password.getText().toString());


            }
        });
    }




}
