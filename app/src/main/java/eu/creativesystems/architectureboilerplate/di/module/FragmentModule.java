package eu.creativesystems.architectureboilerplate.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import eu.creativesystems.architectureboilerplate.fragments.PhotoFragment;


@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract PhotoFragment contributeUserProfileFragment();
}
