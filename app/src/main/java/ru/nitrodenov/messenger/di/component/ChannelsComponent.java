package ru.nitrodenov.messenger.di.component;

import dagger.Subcomponent;
import ru.nitrodenov.messenger.module.channels.ChannelsFragment;
import ru.nitrodenov.messenger.di.module.ChannelsModule;
import ru.nitrodenov.messenger.di.scope.ChannelsScope;

@ChannelsScope
@Subcomponent(modules = ChannelsModule.class)
public interface ChannelsComponent {

    void inject(ChannelsFragment channelsFragment);

}
