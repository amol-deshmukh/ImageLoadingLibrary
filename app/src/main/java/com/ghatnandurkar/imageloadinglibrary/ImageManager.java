package com.ghatnandurkar.imageloadinglibrary;

import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by
 *
 * @author Amol Deshmukh
 * @since 27/02/19
 */
public class ImageManager {

    private ThreadPoolExecutor downloadPoolExecutor;
    private ThreadPoolExecutor decodePoolExecutor;
    private static ImageManager instance;
    private BlockingDeque<Runnable> downloadQueue;
    private BlockingDeque<Runnable> decodeQueue;
    private LruCache<URL, byte[]> lruCache;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            ImageTask imageTask = (ImageTask) msg.obj;
            ImageView imageView = imageTask.getWeakReference().get();
            if (imageView != null) {
                imageView.setImageBitmap(imageTask.getBitmap());
            }
        }
    };

    private ImageManager() {
        downloadQueue = new LinkedBlockingDeque<>();
        decodeQueue = new LinkedBlockingDeque<>();
        lruCache = new LruCache<>(1024 * 1024 * 10);
        downloadPoolExecutor = new ThreadPoolExecutor(4, 4, 10, TimeUnit.SECONDS, downloadQueue);
        decodePoolExecutor = new ThreadPoolExecutor(4, 4, 10, TimeUnit.SECONDS, decodeQueue);
    }

    public static ImageManager getInstance() {
        if (instance == null) {
            synchronized (ImageManager.class) {
                instance = new ImageManager();
            }
        }
        return instance;


    }

    public void handle(State state, ImageTask imageTask) {
        switch (state) {

            case DOWNLOAD_COMPLETE:
                try {
                    lruCache.put(new URL(imageTask.getUrl()), imageTask.getBuffer());

                    decodePoolExecutor.execute(imageTask.getDecodeRunnable());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                break;
            case DECODE_COMPLETE:
                Message message = handler.obtainMessage(1, imageTask);
                message.sendToTarget();
                ;

                break;
        }
    }

    public enum State {
        DOWNLOAD_COMPLETE,
        DECODE_COMPLETE;
    }

    public void loadImage(String url, ImageView imageView) {


        ImageTask imageTask = new ImageTask(url, imageView, this);

        try {
            if (lruCache.get(new URL(imageTask.getUrl())) != null) {
                decodePoolExecutor.execute(imageTask.getDecodeRunnable());
            } else {
                downloadPoolExecutor.execute(imageTask.getDownloadRunnable());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

}
