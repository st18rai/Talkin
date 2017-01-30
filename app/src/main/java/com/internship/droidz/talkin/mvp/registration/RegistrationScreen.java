package com.internship.droidz.talkin.mvp.registration;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.internship.droidz.talkin.R;

import ru.tinkoff.decoro.watchers.FormatWatcher;

public class RegistrationScreen extends AppCompatActivity implements RegistrationContract.RegistrationView {

    private final String TAG = "RegistrationScreen";
    private final int REQUEST_IMAGE_CAPTURE = 0;
    private final int REQUEST_IMAGE_EXT = 1;

    private RegistrationContract.RegistrationPresenter presenter;

    ImageView userPicImageView;
    EditText phoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        userPicImageView = (ImageView) findViewById(R.id.userPicImageView);
        phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        presenter = new RegistrationPresenter(new RegistrationModel(), this);
        presenter.setFormatWatcher();
    }

    public void takePictureFromSource(View view) {
        CharSequence sourcesOfImage[] = new CharSequence[] {"Device Camera", "Photo Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose source:")
                 .setNegativeButton("BACK", (DialogInterface dialog, int which) -> {})
                 .setItems(sourcesOfImage, (DialogInterface dialog, int which) -> {
                    if (which == 0) {
                        startActivityForResult(presenter.getCameraPictureIntent(getPackageManager(), this), REQUEST_IMAGE_CAPTURE);
                    }
                    if (which == 1) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, REQUEST_IMAGE_EXT);
                    }
                 })
                .show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent returnedData) {
        super.onActivityResult(requestCode, resultCode, returnedData);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE : {//get image from camera
                    presenter.checkImageAndSetToView();
                    presenter.addPicToGallery(this);
                    break;
                }
                case REQUEST_IMAGE_EXT : { //if get from gallery
                    presenter.setUserPicUri(returnedData.getData());
                    presenter.checkImageAndSetToView();
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
    public void showAlertMaxSizeOfImage() {
        Toast.makeText(this, "File is bigger than 1MB", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showAlertFailedToLoad() {
        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
    }


}
