package com.internship.droidz.talkin.mvp.registration;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.internship.droidz.talkin.utils.Validator;

import java.io.IOException;

/**
 * Created by st18r on 20.01.2017.
 */


public class RegistrationPresenter implements RegistrationContract.RegistrationPresenter {

    private String TAG = "RegistrationPresenter";


    RegistrationModel model;
    RegistrationContract.RegistrationView view;
    Context context;

    private Validator validator = new Validator();

    public RegistrationPresenter(RegistrationModel model, RegistrationContract.RegistrationView view, Context context) {
        this.model = model;
        this.view = view;
        this.context = context;
    }

    @Override
    public void showDialogChooseSource() {
        CharSequence sourcesOfImage[] = new CharSequence[] {"Device Camera", "Photo Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose source:")
                .setNegativeButton("BACK", (DialogInterface dialog, int which) -> {})
                .setItems(sourcesOfImage, (DialogInterface dialog, int which) -> {
                    if (which == 0) {
                        view.startCameraForCapture();
                    }
                    if (which == 1) {
                        view.startGalleryForCapture();
                    }
                })
                .show();
    }

    @Override
    public Intent getCameraPictureIntent(PackageManager packageManager) {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(packageManager) != null) {
            try {
                model.userPicFile = model.createImageFile();
            } catch (IOException e) {
                Log.i(TAG, "Can't create file!", e);
            }
            if (model.userPicFile != null) {
                model.userPicFileUri = FileProvider.getUriForFile(context,
                        "com.internship.droidz.talkin.fileprovider",
                        model.userPicFile);
                model.getAllPermissionsToUserPicFile(context, pictureIntent);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, model.userPicFileUri);
            }
        }
        return pictureIntent;
    }

    @Override
    public void addPicToGallery() {
        model.addPicToGallery(context);
    }

    @Override
    public void setupUserPicFromCamera() {
        addPicToGallery();
        checkImageSizeAndSetToView();
    }

    @Override
    public void setupUserPicFromGallery(Intent intent) {
        setUserPicUri(intent.getData());
        checkImageSizeAndSetToView();
    }

    @Override
    public void checkImageSizeAndSetToView() {
        if (validator.checkUserPicUriSize(model.userPicFileUri)) {
            try {
                view.setImageUriToView(model.userPicFileUri);
            } catch (Exception e) {
                view.showAlertFailedToLoad();
            }
        } else {
            view.showAlertMaxSizeOfImage();
        }
    }

    @Override
    public void setUserPicUri(Uri uri) {
        model.userPicFileUri = uri;
    }

    @Override
    public void setFormatWatcher() {
        view.setPhoneMask(model.getFormatWatcher());
    }

}
