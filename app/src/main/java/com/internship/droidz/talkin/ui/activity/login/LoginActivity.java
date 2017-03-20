package com.internship.droidz.talkin.ui.activity.login;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.data.web.ApiRetrofit;
import com.internship.droidz.talkin.presentation.presenter.login.LoginPresenter;
import com.internship.droidz.talkin.presentation.view.login.LoginView;
import com.internship.droidz.talkin.repository.SessionRepository;
import com.internship.droidz.talkin.ui.activity.main.MainActivity;
import com.internship.droidz.talkin.ui.activity.registration.RegistrationActivity;

public class LoginActivity extends MvpAppCompatActivity implements LoginView {

    public static final String TAG = "LoginActivity";

    @InjectPresenter
    LoginPresenter mLoginPresenter;

    private SessionRepository repository;
    private EditText email;
    private EditText password;
    private AppCompatButton btnSignIn;
    private ProgressBar progressBar;

    public static Intent getIntent(final Context context) {

        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        getWindow().setBackgroundDrawable(null);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tvForgotPassword = (TextView) findViewById(R.id.forgotPasswordTextView);
        AppCompatButton btnSignUp = (AppCompatButton) findViewById(R.id.signUpButton);

        email = (EditText) findViewById(R.id.emailEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        btnSignIn = (AppCompatButton) findViewById(R.id.signInButton);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_login);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        repository = new SessionRepository(ApiRetrofit.getRetrofitApi());

        signInButtonState();

        btnSignUp.setOnClickListener(view -> {
        //    Log.i("login", email.getText().toString());
        //    Log.i("password", password.getText().toString());
            mLoginPresenter.navigateToRegistrationScreen();
        });

        tvForgotPassword.setOnClickListener(view -> mLoginPresenter.showForgotPasswordDialog());

        btnSignIn.setOnClickListener(view -> mLoginPresenter.signIn(email.getText().toString(), password.getText().toString(), repository));
    }

    @Override
    protected void onStop() {

        super.onStop();

        mLoginPresenter.checkAndStartTimer();
    }

    @Override
    protected void onStart() {

        super.onStart();

        mLoginPresenter.stopTimer();
    }

    @Override
    public void signInButtonState() {

        mLoginPresenter.disableButton();
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mLoginPresenter.disableButtonIfEmailEmpty(email.getText().toString());
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
                mLoginPresenter.enableButtonIfEmailEntered(email.getText().toString());
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

        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToMainScreen() {

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showLoginError() {

        Toast.makeText(this, "Wrong email or password", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showNetworkError() {

        Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void disableButton() {
        btnSignIn.setEnabled(false);
        btnSignIn.setBackgroundResource(R.drawable.signin_button_state);
    }

    @Override
    public void enableButton() {
        btnSignIn.setEnabled(true);
        btnSignIn.setBackgroundResource(R.drawable.signup_button_state);
    }
}
