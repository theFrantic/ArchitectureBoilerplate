package eu.creativesystems.architectureboilerplate.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import eu.creativesystems.architectureboilerplate.activities.MainActivity;


@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract MainActivity contributeMainActivity();
}
