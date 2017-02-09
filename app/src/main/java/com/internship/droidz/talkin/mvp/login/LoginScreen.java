package com.internship.droidz.talkin.mvp.login;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.mvp.main.MainScreen;
import com.internship.droidz.talkin.mvp.registration.RegistrationScreen;
import com.jakewharton.rxbinding.view.RxView;

import rx.Subscription;

public class LoginScreen extends AppCompatActivity implements LoginContract.LoginView {

    EditText email;
    EditText password;
    TextView tvForgotPassword;
    Toolbar toolbar;
    AppCompatButton btnSignIn;
    AppCompatButton btnSignUp;
    LoginContract.LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        email = (EditText) findViewById(R.id.emailEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        tvForgotPassword = (TextView) findViewById(R.id.forgotPasswordTextView);
        btnSignIn = (AppCompatButton) findViewById(R.id.signInButton);
        btnSignUp = (AppCompatButton) findViewById(R.id.signUpButton);
        presenter = new LoginPresenterImpl(new LoginModelImpl(), this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        signInButtonState();

        Subscription buttonSub = RxView.clicks(btnSignUp).subscribe((aVoid) -> {
            Log.i("rx login", email.getText().toString());
            Log.i("rx password", password.getText().toString());
            navigateToRegistrationScreen();
        });

        Subscription tvSub = RxView.clicks(tvForgotPassword).subscribe((aVoid) -> forgotPassword());

        Subscription SubscrBtnSignIn = RxView.clicks(btnSignIn)
                .subscribe((aVoid) -> {
                    presenter.signIn(email.getText().toString(), password.getText().toString());
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
        FragmentManager fragmentManager = this.getFragmentManager();
        ForgotPasswordDialog forgotPasswordDialog = new ForgotPasswordDialog();
        forgotPasswordDialog.show(fragmentManager, "forgot password dialog");
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

    @Override
    public void showLoginError() {
        Toast.makeText(this,"Wrong email or password",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showNetworkError() {
        Toast.makeText(this,"No internet connection",Toast.LENGTH_LONG).show();
    }
}
