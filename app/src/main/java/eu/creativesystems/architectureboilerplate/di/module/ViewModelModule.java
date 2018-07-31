package eu.creativesystems.architectureboilerplate.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import eu.creativesystems.architectureboilerplate.di.key.ViewModelKey;
import eu.creativesystems.architectureboilerplate.view_models.FactoryViewModel;
import eu.creativesystems.architectureboilerplate.view_models.PhotoListViewModel;
import eu.creativesystems.architectureboilerplate.view_models.PhotoViewModel;


@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PhotoViewModel.class)
    abstract ViewModel bindPhotoViewModel(PhotoViewModel repoViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PhotoListViewModel.class)
    abstract ViewModel bindPhotoListViewModel(PhotoListViewModel repoViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);
}
