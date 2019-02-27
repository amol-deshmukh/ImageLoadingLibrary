package com.ghatnandurkar.imageloadinglibrary;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by
 *
 * @author Amol Deshmukh
 * @since 27/02/19
 */
public class DownloadRunnable implements Runnable {


    private final ImageTask imagetask;

    public DownloadRunnable(ImageTask imageTask) {
        this.imagetask = imageTask;
    }

    @Override
    public void run() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            URL url = new URL(imagetask.getUrl());
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            byte[] data = new byte[16384];
            int mRead = -1;
            while ((mRead = inputStream.read(data, 0, data.length)) != -1) {
                byteArrayOutputStream.write(data, 0, mRead);
            }
            imagetask.setBuffer(byteArrayOutputStream.toByteArray());
            imagetask.handle(ImageManager.State.DOWNLOAD_COMPLETE);

        } catch (Exception e) {


        }


    }
}
