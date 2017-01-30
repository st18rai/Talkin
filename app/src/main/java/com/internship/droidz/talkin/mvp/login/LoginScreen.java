package com.internship.droidz.talkin.mvp.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.mvp.registration.RegistrationScreen;
import com.internship.droidz.talkin.web.ApiService;
import com.jakewharton.rxbinding.view.RxView;

import rx.Subscription;
import rx.functions.Action1;

public class LoginScreen extends AppCompatActivity  implements LoginContract.LoginView{

    EditText email;
    EditText password;
    AppCompatButton btnSignIn;
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
        signInButtonState();


        AppCompatButton btnSignUp = (AppCompatButton) findViewById(R.id.signUpButton);
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
}
