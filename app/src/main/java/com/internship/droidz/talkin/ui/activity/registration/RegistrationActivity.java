package com.internship.droidz.talkin.ui.activity.registration;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.data.web.ApiRetrofit;
import com.internship.droidz.talkin.media.PhotoFile;
import com.internship.droidz.talkin.presentation.presenter.registration.RegistrationPresenter;
import com.internship.droidz.talkin.presentation.view.registration.RegistrationView;
import com.internship.droidz.talkin.repository.ContentRepository;
import com.internship.droidz.talkin.repository.SessionRepository;
import com.internship.droidz.talkin.ui.activity.main.MainActivity;
import com.internship.droidz.talkin.utils.Validator;

import ru.tinkoff.decoro.watchers.FormatWatcher;

public class RegistrationActivity extends MvpAppCompatActivity implements RegistrationView {

    public static final String TAG = "RegistrationActivity";
    private final int REQUEST_IMAGE_CAPTURE = 0;
    private final int REQUEST_IMAGE_EXT = 1;

    @InjectPresenter
    RegistrationPresenter mRegistrationPresenter;

    private EditText email, password, confirmPassword, phoneEditText, fullName, website;
    private ImageView userPicImageView;
    private LoginButton linkFacebookButtonReg;
    private AppCompatButton linkFacebookButtonView;
    private TextInputLayout tilEmail, tilPassword, tilConfirmPassword;
    private SessionRepository sessionRepository;
    private ContentRepository contentRepository;
    private ProgressBar progressBar;
    CallbackManager mCallbackManager;

    public static Intent getIntent(final Context context) {

        return new Intent(context, RegistrationActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_registration_screen);
        getWindow().setBackgroundDrawable(null);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppCompatButton signUpButtonReg = (AppCompatButton) findViewById(R.id.signUpButtonReg);

        userPicImageView = (ImageView) findViewById(R.id.userPicImageView);
        phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        fullName = (EditText) findViewById(R.id.nameEditText);
        website = (EditText) findViewById(R.id.siteEditText);
        linkFacebookButtonReg = (LoginButton) findViewById(R.id.linkFacebookButtonReg);
        linkFacebookButtonView = (AppCompatButton) findViewById(R.id.linkFacebookButtonView);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        mRegistrationPresenter.setFormatWatcher();
        sessionRepository = new SessionRepository(ApiRetrofit.getRetrofitApi());
        contentRepository = new ContentRepository(ApiRetrofit.getRetrofitApi());
        email = (EditText) findViewById(R.id.emailEditTextReg);
        password = (EditText) findViewById(R.id.passwordEditTextReg);
        confirmPassword = (EditText) findViewById(R.id.confirmPasswordEditText);
        tilEmail = (TextInputLayout) findViewById(R.id.til_emailAddress);
        tilPassword = (TextInputLayout) findViewById(R.id.til_textPassword);
        tilConfirmPassword = (TextInputLayout) findViewById(R.id.til_confirmTextPassword);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_registration);

        mRegistrationPresenter.checkEmail();
        mRegistrationPresenter.checkPassword();
        mRegistrationPresenter.checkAndComparePasswords();

        signUpButtonReg.setOnClickListener(view -> mRegistrationPresenter.signUp(
                sessionRepository,
                contentRepository,
                email.getText().toString(),
                password.getText().toString(),
                fullName.getText().toString(),
                phoneEditText.getText().toString().replaceAll("[\\n\\-\\(\\)\\s]", ""), // TODO: 2/20/17 [Code Review] this is a part of business logic, move to presenter/model layer
                website.getText().toString()));

        linkFacebookButtonView.setOnClickListener(view -> {

            mRegistrationPresenter.linkFacebook(linkFacebookButtonReg);
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent returnedData) {

        super.onActivityResult(requestCode, resultCode, returnedData);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE: {
                    mRegistrationPresenter.setupUserPicFromCamera();
                    break;
                }
                case REQUEST_IMAGE_EXT: {
                    mRegistrationPresenter.setupUserPicFromGallery(returnedData);
                    break;
                }
            }
            if (FacebookSdk.isFacebookRequestCode(requestCode)) {
                mCallbackManager.onActivityResult(requestCode, resultCode, returnedData);
            }
        }
    }

    @Override
    public void setImageUriToView(Uri uri) {

        userPicImageView.setImageURI(uri);
    }

    @Override
    public void setPhoneMask(FormatWatcher formatWatcher) {

        formatWatcher.installOn(phoneEditText);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void startCameraForCapture() {

        startActivityForResult(mRegistrationPresenter.getCameraPictureIntent(new PhotoFile(), getPackageManager()), REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void startGalleryForCapture() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_IMAGE_EXT);
    }

    @Override
    public void showAlertMaxSizeOfImage() {

        Toast.makeText(this, R.string.alert_max_size_of_image, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showAlertFailedToLoad() {

        Toast.makeText(this, R.string.alert_failed_to_load, Toast.LENGTH_LONG).show();
    }

    public void onClickUserPicView(View view) {

        askPermissionWriteExternal();
        showDialogChooseSource();
    }

    @Override
    public void checkAndComparePasswords() {

        confirmPassword.setOnFocusChangeListener((view, focus) -> {
            if (!focus && !TextUtils.equals(password.getText().toString(), confirmPassword.getText().toString())) {
                tilConfirmPassword.setError(getResources().getString(R.string.compare_passwords_toast));
            } else
                tilConfirmPassword.setError(null);
        });
    }

    @Override
    public void navigateToMainScreen() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    @TargetApi(23)
    public void askPermissionWriteExternal() {

        if (mRegistrationPresenter.shouldAskPermission()) {
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            int REQUEST_PERMISSION_WRITE_EXTERNAL = 200;
            requestPermissions(perms, REQUEST_PERMISSION_WRITE_EXTERNAL);
        }
    }

    @Override
    public void setFacebookLoginButtonAsLinked() {

        linkFacebookButtonView.setText(R.string.button_facebook_linked);
    }

    @Override
    public void showRegistrationError() {

        Toast.makeText(this, R.string.registration_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showNetworkError() {

        Toast.makeText(this, R.string.network_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (permsRequestCode) {
            case 200:
                boolean writeAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
    }

    @Override
    public void checkEmail() {

        email.setOnFocusChangeListener((view, focus) -> {
            if (!focus && !Validator.isValidEmail(email.getText().toString())) {
                tilEmail.setError(getResources().getString(R.string.invalid_email_toast));
            } else
                tilEmail.setError(null);
        });
    }

    @Override
    public void checkPassword() {

        password.setOnFocusChangeListener((view, focus) -> {
            String input = password.getText().toString();
            if (!focus && !Validator.isValidPasswordLength(input)) {
                tilPassword.setError(getResources().getString(R.string.invalid_password_length_toast));
            } else {
                tilPassword.setError(null);
                if (!focus && !Validator.checkPasswordStrength(input)) {
                    tilPassword.setError(getResources().getString(R.string.password_is_weak_toast));
                } else
                    tilPassword.setError(null);
            }
        });
    }

    @Override
    public void showDialogChooseSource() {

        CharSequence sourcesOfImage[] = new CharSequence[]{"Device Camera", "Photo Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose source:")
                .setNegativeButton("BACK", (DialogInterface dialog, int which) -> {
                })
                .setItems(sourcesOfImage, (DialogInterface dialog, int which) -> {
                    if (which == 0) {
                        startCameraForCapture();
                    }
                    if (which == 1) {
                        startGalleryForCapture();
                    }
                })
                .show();
    }

    @Override
    public void showInvalidRegistrationDataError() {

        Toast.makeText(this, R.string.invalid_registration_data_error, Toast.LENGTH_LONG).show();
    }
}