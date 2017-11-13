package ru.nitrodenov.messenger.di.component;

import javax.inject.Singleton;

import dagger.Component;
import ru.nitrodenov.messenger.MessengerApplication;
import ru.nitrodenov.messenger.di.module.ApplicationModule;
import ru.nitrodenov.messenger.di.module.ChannelModule;
import ru.nitrodenov.messenger.di.module.ChannelsModule;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MessengerApplication application);

    ChannelsComponent plus(ChannelsModule channelsModule);

    ChannelComponent plus(ChannelModule channelModule);

}
