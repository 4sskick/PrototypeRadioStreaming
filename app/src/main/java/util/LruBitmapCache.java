package util;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Administrator on 6/24/2015.
 */
public class LruBitmapCache extends LruCache <String,Bitmap> implements ImageLoader.ImageCache{

    public static int getDefaultLruChaceSize(){
        final int maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);
        final int chaceSize = maxMemory / 8;

        return chaceSize;
    }

    public LruBitmapCache(){
        this(getDefaultLruChaceSize());
    }

    public LruBitmapCache(int sizeInkiloByte){
        super(sizeInkiloByte);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
