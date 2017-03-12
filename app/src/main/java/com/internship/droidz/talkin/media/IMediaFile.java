package com.internship.droidz.talkin.media;

import android.net.Uri;

import java.io.File;
import java.io.IOException;

/**
 * Created by serg on 3/2/17.
 */

public interface IMediaFile {

    String createFileName();

    File createFile() throws IOException;

    File getFile();

    Uri getUri();

    void setFile(File file);

}
