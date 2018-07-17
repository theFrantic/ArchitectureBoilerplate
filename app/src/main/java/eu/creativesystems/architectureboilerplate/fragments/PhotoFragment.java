package eu.creativesystems.architectureboilerplate.fragments;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import eu.creativesystems.architectureboilerplate.R;
import eu.creativesystems.architectureboilerplate.database.entity.Photo;
import eu.creativesystems.architectureboilerplate.view_models.PhotoViewModel;

public class PhotoFragment extends Fragment {

    public static final String PHOTO_KEY = "PHOTO_ID";   // to get the data from bundle

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private PhotoViewModel viewModel;

    @BindView(R.id.fragment_photo_title)
    TextView titleTextView;
    @BindView(R.id.fragment_photo_photo)
    ImageView photoImageView;

    public PhotoFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();
    }

    //region Configuration

    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }

    private void configureViewModel(){
        int photoId = getArguments().getInt(PHOTO_KEY);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PhotoViewModel.class);
        viewModel.init(photoId);
        viewModel.getPhoto().observe(this, photo -> updateUI(photo));
    }

    //endregion

    //region Update UI

    private void updateUI(@Nullable Photo photo){
        if (photo != null){
            Glide.with(this).load(photo.getUrl()).apply(RequestOptions.circleCropTransform()).into(photoImageView);
            this.titleTextView.setText(photo.getTitle());
        }
    }

    //endregion
}
