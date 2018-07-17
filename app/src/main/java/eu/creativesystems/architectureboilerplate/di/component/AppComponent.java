package eu.creativesystems.architectureboilerplate.di.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import eu.creativesystems.architectureboilerplate.App;
import eu.creativesystems.architectureboilerplate.di.module.ActivityModule;
import eu.creativesystems.architectureboilerplate.di.module.AppModule;
import eu.creativesystems.architectureboilerplate.di.module.FragmentModule;

@Singleton
@Component(modules={ActivityModule.class, FragmentModule.class, AppModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(App app);
}
