package ru.nitrodenov.messenger.di.module;

import android.os.Bundle;

import javax.annotation.Nullable;

import dagger.Module;
import dagger.Provides;
import ru.nitrodenov.messenger.ImageCache;
import ru.nitrodenov.messenger.async.AsyncHandler;
import ru.nitrodenov.messenger.di.scope.ChannelsScope;
import ru.nitrodenov.messenger.http.HttpConnection;
import ru.nitrodenov.messenger.module.channels.interactor.ChannelsDataInteractor;
import ru.nitrodenov.messenger.module.channels.interactor.ChannelsDataInteractorImpl;
import ru.nitrodenov.messenger.module.channels.presenter.ChannelsPresenter;
import ru.nitrodenov.messenger.module.channels.presenter.ChannelsPresenterImpl;
import ru.nitrodenov.messenger.module.common.interactor.MultiImageLoaderInteractor;
import ru.nitrodenov.messenger.module.common.interactor.MultiImageLoaderInteractorImpl;

@Module
public class ChannelsModule {

    @Nullable
    private final Bundle state;

    public ChannelsModule(@Nullable Bundle state) {
        this.state = state;
    }

    @Provides
    @ChannelsScope
    ChannelsDataInteractor provideChannelsDataInteractor(AsyncHandler asyncHandler,
                                                         HttpConnection httpConnection) {
        return new ChannelsDataInteractorImpl(asyncHandler, httpConnection);
    }

    @Provides
    @ChannelsScope
    MultiImageLoaderInteractor provideMultiImageLoaderInteractor(AsyncHandler asyncHandler,
                                                                 HttpConnection httpConnection,
                                                                 ImageCache imageCache) {
        return new MultiImageLoaderInteractorImpl(asyncHandler, httpConnection, imageCache);
    }

    @Provides
    @ChannelsScope
    ChannelsPresenter provideChannelsPresenter(ChannelsDataInteractor channelsDataInteractor,
                                               MultiImageLoaderInteractor imageLoaderInteractor) {
        return new ChannelsPresenterImpl(channelsDataInteractor, imageLoaderInteractor, state);
    }

}
