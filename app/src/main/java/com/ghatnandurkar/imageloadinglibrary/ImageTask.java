package com.ghatnandurkar.imageloadinglibrary;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;

/**
 * Created by
 *
 * @author Amol Deshmukh
 * @since 27/02/19
 */
public class ImageTask {
    private String url;
    private WeakReference<ImageView> weakReference;
    private ImageManager imageManager;
    private Bitmap bitmap;
    private byte[] buffer;
    private DownloadRunnable downloadRunnable;
    private DecodeRunnable decodeRunnable;


    public ImageTask(String url, ImageView imageView, ImageManager imageManager) {
        this.url = url;
        weakReference = new WeakReference<ImageView>(imageView);
        this.imageManager = imageManager;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WeakReference<ImageView> getWeakReference() {
        return weakReference;
    }

    public void setWeakReference(WeakReference<ImageView> weakReference) {
        this.weakReference = weakReference;
    }

    public ImageManager getImageManager() {
        return imageManager;
    }

    public void setImageManager(ImageManager imageManager) {
        this.imageManager = imageManager;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    public DownloadRunnable getDownloadRunnable() {
        return new DownloadRunnable(this);
    }


    public DecodeRunnable getDecodeRunnable() {
        return new DecodeRunnable(this);
    }


    public void handle(ImageManager.State state) {
        imageManager.handle(state,this);
    }
}
