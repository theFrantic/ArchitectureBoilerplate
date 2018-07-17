package eu.creativesystems.architectureboilerplate.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import eu.creativesystems.architectureboilerplate.R;
import eu.creativesystems.architectureboilerplate.fragments.PhotoFragment;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    private static int PHOTO_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureDagger();
        this.showFragment(savedInstanceState);
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    //region Show Fragment

    private void showFragment(Bundle savedInstanceState){
        if (savedInstanceState == null) {

            PhotoFragment fragment = new PhotoFragment();

            Bundle bundle = new Bundle();
            bundle.putInt(PhotoFragment.PHOTO_KEY, PHOTO_ID);
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, null)
                    .commit();
        }
    }

    //endregion

    //region Configure Dagger

    private void configureDagger(){
        AndroidInjection.inject(this);
    }

    //endregion
}
