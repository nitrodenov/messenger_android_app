package ru.nitrodenov.messenger.di.component;

import dagger.Subcomponent;
import ru.nitrodenov.messenger.di.module.ChannelModule;
import ru.nitrodenov.messenger.di.scope.ChannelScope;
import ru.nitrodenov.messenger.module.channel.ChannelFragment;

@ChannelScope
@Subcomponent(modules = ChannelModule.class)
public interface ChannelComponent {

    void inject(ChannelFragment fragment);

}
