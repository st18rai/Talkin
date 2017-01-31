package com.internship.droidz.talkin.mvp.registration;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.text.TextUtils;
import com.internship.droidz.talkin.R;

import com.internship.droidz.talkin.mvp.main.MainScreen;
import com.jakewharton.rxbinding.view.RxView;

import ru.tinkoff.decoro.watchers.FormatWatcher;
import rx.Subscription;
import rx.functions.Action1;


public class RegistrationScreen extends AppCompatActivity implements RegistrationContract.RegistrationView {

    private final String TAG = "RegistrationScreen";
    private final int REQUEST_IMAGE_CAPTURE = 0;
    private final int REQUEST_IMAGE_EXT = 1;

    private RegistrationContract.RegistrationPresenter presenter;

    EditText email;
    EditText password;
    EditText confirmPassword;
    ImageView userPicImageView;
    EditText phoneEditText;
    EditText fullName;
    EditText website;

    AppCompatButton signUpButtonReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        userPicImageView = (ImageView) findViewById(R.id.userPicImageView);
        phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        fullName = (EditText) findViewById(R.id.nameEditText);
        website = (EditText) findViewById(R.id.siteEditText);
        signUpButtonReg = (AppCompatButton) findViewById(R.id.signUpButtonReg);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        presenter = new RegistrationPresenter(new RegistrationModel(), this, this);
        presenter.setFormatWatcher();
        email = (EditText) findViewById(R.id.emailEditTextReg);
        password = (EditText) findViewById(R.id.passwordEditTextReg);
        confirmPassword = (EditText) findViewById(R.id.confirmPasswordEditText);

        checkEmail();
        checkPasswordLength();
        comparePasswords();

        Subscription buttonSub = RxView.clicks(signUpButtonReg).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                presenter.signUp(email.getText().toString(),
                        password.getText().toString(),
                        fullName.getText().toString(),
                        phoneEditText.getText().toString()
                                .replaceAll(" ","")
                                .replaceAll("-","")
                                .replaceAll("\\(","")
                                .replaceAll("\\)",""),
                        website.getText().toString());
            }

        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent returnedData) {
        super.onActivityResult(requestCode, resultCode, returnedData);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE : {//get image from camera
                    presenter.setupUserPicFromCamera();
                    break;
                }
                case REQUEST_IMAGE_EXT : { //if get from gallery
                    presenter.setupUserPicFromGallery(returnedData);
                    break;
                }
            }
        }
    }

    @Override
    public void setImageUriToView(Uri uri) { userPicImageView.setImageURI(uri); }

    @Override
    public void setPhoneMask(FormatWatcher formatWatcher) {
        formatWatcher.installOn(phoneEditText);
    }

    @Override
    public void startCameraForCapture() {
        startActivityForResult(presenter.getCameraPictureIntent(getPackageManager()), REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void startGalleryForCapture() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_IMAGE_EXT);
    }

    @Override
    public void showAlertMaxSizeOfImage() {
        Toast.makeText(this, "File is bigger than 1MB", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showAlertFailedToLoad() {
        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
    }

    public void userPicOnClick(View view) {
        presenter.showDialogChooseSource();
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

    @Override
    public void navigatetoMainScreen() {
        Intent intent = new Intent(RegistrationScreen.this, MainScreen.class);
        startActivity(intent);
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
