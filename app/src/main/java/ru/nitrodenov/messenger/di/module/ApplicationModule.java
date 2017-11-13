package ru.nitrodenov.messenger.di.module;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.LruCache;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.nitrodenov.messenger.ImageCache;
import ru.nitrodenov.messenger.ImageCacheImpl;
import ru.nitrodenov.messenger.MessengerApplication;
import ru.nitrodenov.messenger.async.AsyncHandler;
import ru.nitrodenov.messenger.async.AsyncHandlerImpl;
import ru.nitrodenov.messenger.http.HttpConnection;
import ru.nitrodenov.messenger.http.HttpConnectionImpl;

import static java.lang.Runtime.getRuntime;

@Module
public class ApplicationModule {

    private final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

    // Use 1/8th of the available memory for this memory cache.
    private final int cacheSize = maxMemory / 8;
    private static final String IMAGE_DIRECTORY_NAME = "imageDir";

    @NonNull
    private final MessengerApplication application;

    public ApplicationModule(@NonNull MessengerApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    AsyncHandler provideAsyncHandler(ExecutorService executorService) {
        return new AsyncHandlerImpl(executorService);
    }

    @Provides
    @Singleton
    ExecutorService provideExecutorService() {
        return Executors.newFixedThreadPool(getRuntime().availableProcessors() + 1);
    }

    @Provides
    @Singleton
    ImageCache provideImageCache(LruCache<String, Bitmap> inMemoryCache, File directory) {
        return new ImageCacheImpl(inMemoryCache, directory);
    }

    @Provides
    @Singleton
    LruCache<String, Bitmap> provideInMemoryCache() {
        return new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    @Provides
    @Singleton
    File provideImagesDirectory(Application application) {
        return new ContextWrapper(application).getDir(IMAGE_DIRECTORY_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    HttpConnection provideHttpConnection() {
        return new HttpConnectionImpl();
    }
}
