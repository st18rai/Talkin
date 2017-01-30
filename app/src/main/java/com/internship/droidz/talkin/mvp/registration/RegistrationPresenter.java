package com.internship.droidz.talkin.mvp.registration;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.internship.droidz.talkin.utils.Validator;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser;
import ru.tinkoff.decoro.slots.Slot;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

/**
 * Created by st18r on 20.01.2017.
 */

public class RegistrationPresenter implements RegistrationContract.RegistrationPresenter {

    private String TAG = "RegistrationPresenter";
    private static final String PHONE_MASK = "+38 (0__) ___-__-__";

    RegistrationModel model;
    RegistrationContract.RegistrationView view;
    FormatWatcher formatWatcher;
    private Validator validator = new Validator();


    public RegistrationPresenter(RegistrationModel model, RegistrationContract.RegistrationView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public File createImageFile(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        model.currentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    @Override
    public Intent getCameraPictureIntent(PackageManager packageManager, Context context) {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(packageManager) != null) {
            try {
                model.userPicFile = createImageFile(context);
            } catch (IOException e) {
                Log.i(TAG, "Can't create file!");
            }
            if (model.userPicFile != null) {
                model.userPicFileUri = FileProvider.getUriForFile(context,
                        "com.internship.droidz.talkin.fileprovider",
                        model.userPicFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, model.userPicFileUri);
            }
        }
        return pictureIntent;
    }

    @Override
    public void addPicToGallery(Context context) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        model.userPicFile = new File(model.currentPhotoPath);
        Uri contentUri = Uri.fromFile(model.userPicFile);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    @Override
    public void checkImageAndSetToView() {
        if (validator.checkUserPicUriSize(model.userPicFileUri)) {
            try {
                view.setImageUriToView(model.userPicFileUri);
            }
            catch (Exception e) {
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
        Slot[] slots = new UnderscoreDigitSlotsParser().parseSlots(PHONE_MASK);
        formatWatcher = new MaskFormatWatcher(
                MaskImpl.createTerminated(slots)
        );
        view.setPhoneMask(formatWatcher);
    }




}
