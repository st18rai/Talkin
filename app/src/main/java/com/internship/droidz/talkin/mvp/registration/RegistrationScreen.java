package com.internship.droidz.talkin.mvp.registration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.internship.droidz.talkin.R;

public class RegistrationScreen extends AppCompatActivity implements RegistrationContract.RegistrationView {

    EditText email;
    EditText password;
    EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        email = (EditText) findViewById(R.id.emailEditTextReg);
        password = (EditText) findViewById(R.id.passwordEditTextReg);
        confirmPassword = (EditText) findViewById(R.id.confirmPasswordEditText);

        checkEmail();
        checkPasswordLength();
        comparePasswords();
    }

    @Override
    public void comparePasswords() {

        confirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (!focus && !TextUtils.equals(password.getText().toString(), confirmPassword.getText().toString())) {
                    confirmPassword.setError(getResources().getString(R.string.compare_passwords_toast));
                    Toast toast = Toast.makeText(getApplication(), R.string.compare_passwords_toast, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private boolean isValidPasswordLength(String password) {
        if (password != null && password.length() > 6) {
            return true;
        }
        return false;
    }

    @Override
    public void checkEmail() {

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (!focus && !isValidEmail(email.getText().toString())) {
                    email.setError(getResources().getString(R.string.invalid_email_toast));
                    Toast toast = Toast.makeText(getApplication(), R.string.invalid_email_toast, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    @Override
    public void checkPasswordLength() {

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (!focus && !isValidPasswordLength(password.getText().toString())) {
                    password.setError(getResources().getString(R.string.invalid_password_length_toast));
                    Toast toast = Toast.makeText(getApplication(), R.string.invalid_password_length_toast, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }
}
