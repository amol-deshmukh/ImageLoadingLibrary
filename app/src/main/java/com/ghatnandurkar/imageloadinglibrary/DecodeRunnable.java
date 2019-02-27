package com.ghatnandurkar.imageloadinglibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by
 *
 * @author Amol Deshmukh
 * @since 27/02/19
 */
public class DecodeRunnable implements Runnable {
    private ImageTask imageTask;
    public DecodeRunnable(ImageTask imageTask) {
        this.imageTask=imageTask;
    }

    @Override
    public void run() {

        byte[] bitmapData=imageTask.getBuffer();
        Bitmap bitmap= BitmapFactory.decodeByteArray(bitmapData,0,bitmapData.length);
        imageTask.setBitmap(bitmap);
        imageTask.handle(ImageManager.State.DECODE_COMPLETE);
    }
}
