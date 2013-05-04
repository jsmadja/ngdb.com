package com.ngdb.web.services.infrastructure;

import com.google.common.io.ByteStreams;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public abstract class PlaylistManager {

    protected String readContentOf(String sUrl) throws IOException {
        URL url = new URL(sUrl);
        InputStream in = url.openStream();
        byte[] bytes = ByteStreams.toByteArray(in);
        return new String(bytes);
    }

}
