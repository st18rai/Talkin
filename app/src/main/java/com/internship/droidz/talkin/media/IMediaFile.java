package com.internship.droidz.talkin.media;

import java.io.File;
import java.io.IOException;

/**
 * Created by serg on 3/2/17.
 */

public interface IMediaFile {

    String getFileName();

    File getTemporaryFile() throws IOException;
}
