package eu.creativesystems.architectureboilerplate.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import eu.creativesystems.architectureboilerplate.fragments.PhotoFragment;
import eu.creativesystems.architectureboilerplate.fragments.PhotoListFragment;


@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract PhotoFragment contributePhotoFragment();

    @ContributesAndroidInjector
    abstract PhotoListFragment contributePhotoListFragment();

}
