package com.internship.droidz.talkin.ui.activity.login;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.internship.droidz.talkin.App;
import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.presentation.presenter.login.LoginPresenter;
import com.internship.droidz.talkin.presentation.view.login.LoginView;
import com.internship.droidz.talkin.ui.activity.main.MainActivity;
import com.internship.droidz.talkin.ui.activity.registration.RegistrationActivity;
import com.internship.droidz.talkin.utils.ProcessTimerReceiver;
import com.jakewharton.rxbinding.view.RxView;

import rx.Subscription;

public class LoginActivity extends MvpAppCompatActivity implements LoginView {

    public static final String TAG = "LoginActivity";
    // TODO: 2/20/17 [Code Review] This is a part of business logic, move to model layer
    int TIME_TO_SEND_NOTIFICATION = 15 * 60;

    @InjectPresenter
    LoginPresenter mLoginPresenter;

    EditText email;
    EditText password;
    TextView tvForgotPassword;
    Toolbar toolbar;
    AppCompatButton btnSignIn;
    AppCompatButton btnSignUp;

    public static Intent getIntent(final Context context) {

        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

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

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        signInButtonState();

        // TODO: 2/20/17 you do not need subscription entity here (and below)
        Subscription buttonSub = RxView.clicks(btnSignUp).subscribe((aVoid) -> {
            Log.i("rx login", email.getText().toString());
            Log.i("rx password", password.getText().toString());
            // TODO: 2/20/17 you have to call presenter method instead of direct call of this method
            navigateToRegistrationScreen();
        });

        Subscription tvSub = RxView.clicks(tvForgotPassword).subscribe((aVoid) -> forgotPassword());

        Subscription SubscrBtnSignIn = RxView.clicks(btnSignIn)
                .subscribe((aVoid) -> {
                    mLoginPresenter.signIn(email.getText().toString(), password.getText().toString());
                });

    }

    @Override
    protected void onStop() {

        super.onStop();

        // TODO: 2/20/17 [Code Review] this is a part of business logic, move to presenter/model layer
        checkAndStartTimer();
    }

    @Override
    protected void onStart() {

        super.onStop();

        // TODO: 2/20/17 [Code Review] this is a part of business logic, move to presenter/model layer
        stopTimer();
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
                // TODO: 2/20/17 [Code Review] this is a part of business logic, move to presenter/model layer
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
                // TODO: 2/20/17 [Code Review] this is a part of business logic, move to presenter/model layer
                if (TextUtils.isEmpty(email.getText().toString()))
                    btnSignIn.setEnabled(false);
                else
                    btnSignIn.setEnabled(true);
            }
        });
    }

    public void checkAndStartTimer() {

        if (!App.getApp().getBackgroundChecker().isAppInForeground()) {
            Log.i("TAG", "checkAndStartTimer: ");
            AlarmManager processTimer = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
            Intent intent = new Intent(this, ProcessTimerReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            processTimer.set(AlarmManager.RTC, System.currentTimeMillis() + TIME_TO_SEND_NOTIFICATION * 1000, pendingIntent);
        }
    }

    public void stopTimer() {

        Log.i("TAG", "stopTimer: ");
        Intent intent = new Intent(this, ProcessTimerReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
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
    public void navigationToMainScreen() {

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoginError() {

        Toast.makeText(this, "Wrong email or password", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showNetworkError() {

        Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
    }
}
