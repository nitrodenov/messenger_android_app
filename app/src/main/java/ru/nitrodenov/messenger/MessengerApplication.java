package ru.nitrodenov.messenger;

import android.app.Application;

import ru.nitrodenov.messenger.di.component.ApplicationComponent;
import ru.nitrodenov.messenger.di.component.DaggerApplicationComponent;
import ru.nitrodenov.messenger.di.module.ApplicationModule;

public class MessengerApplication extends Application {

    protected ApplicationComponent applicationComponent;

    private static MessengerApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }

    public static MessengerApplication getInstance() {
        return instance;
    }

    public ApplicationComponent getComponent(){
        return applicationComponent;
    }
}
