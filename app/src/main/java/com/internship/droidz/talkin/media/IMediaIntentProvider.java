package com.internship.droidz.talkin.media;

import android.content.Intent;

/**
 * Created by serg on 3/2/17.
 */

public interface IMediaIntentProvider {

    Intent getMediaScanIntent(String currentPhotoPath);
}
