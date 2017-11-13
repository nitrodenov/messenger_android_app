package ru.nitrodenov.messenger.di.module;

import android.os.Bundle;
import android.support.annotation.NonNull;

import javax.annotation.Nullable;

import dagger.Module;
import dagger.Provides;
import ru.nitrodenov.messenger.ImageCache;
import ru.nitrodenov.messenger.async.AsyncHandler;
import ru.nitrodenov.messenger.di.scope.ChannelScope;
import ru.nitrodenov.messenger.http.HttpConnection;
import ru.nitrodenov.messenger.module.channel.entity.ChannelToolbarData;
import ru.nitrodenov.messenger.module.channel.interactor.ChannelDataInteractor;
import ru.nitrodenov.messenger.module.channel.interactor.ChannelDataInteractorImpl;
import ru.nitrodenov.messenger.module.common.interactor.ImageLoaderInteractor;
import ru.nitrodenov.messenger.module.common.interactor.ImageLoaderInteractorImpl;
import ru.nitrodenov.messenger.module.channel.presenter.ChannelPresenter;
import ru.nitrodenov.messenger.module.channel.presenter.ChannelPresenterImpl;
import ru.nitrodenov.messenger.module.common.interactor.MultiImageLoaderInteractor;
import ru.nitrodenov.messenger.module.common.interactor.MultiImageLoaderInteractorImpl;

@Module
public class ChannelModule {

    @Nullable
    private final Bundle state;
    @NonNull
    private final ChannelToolbarData channelToolbarData;

    public ChannelModule(@Nullable Bundle state, @NonNull ChannelToolbarData channelToolbarData) {
        this.state = state;
        this.channelToolbarData = channelToolbarData;
    }

    @Provides
    @ChannelScope
    ChannelDataInteractor provideChannelDataInteractor(AsyncHandler asyncHandler) {
        return new ChannelDataInteractorImpl(asyncHandler);
    }

    @Provides
    @ChannelScope
    MultiImageLoaderInteractor provideMultiImageLoaderInteractor(AsyncHandler asyncHandler,
                                                                 HttpConnection httpConnection,
                                                                 ImageCache imageCache) {
        return new MultiImageLoaderInteractorImpl(asyncHandler, httpConnection, imageCache);
    }

    @Provides
    @ChannelScope
    ImageLoaderInteractor provideImageLoaderInteractor(AsyncHandler asyncHandler,
                                                       HttpConnection httpConnection,
                                                       ImageCache imageCache) {
        return new ImageLoaderInteractorImpl(asyncHandler, httpConnection, imageCache);
    }

    @Provides
    @ChannelScope
    ChannelPresenter provideChannelPresenter(ChannelDataInteractor channelDataInteractor,
                                             ImageLoaderInteractor imageLoaderInteractor,
                                             MultiImageLoaderInteractor multiImageLoaderInteractor) {
        return new ChannelPresenterImpl(channelDataInteractor, imageLoaderInteractor, multiImageLoaderInteractor, channelToolbarData, state);
    }
}
