package eu.creativesystems.architectureboilerplate.fragments;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.support.AndroidSupportInjection;
import eu.creativesystems.architectureboilerplate.R;
import eu.creativesystems.architectureboilerplate.ui.adapters.PhotoAdapter;
import eu.creativesystems.architectureboilerplate.view_models.PhotoListViewModel;

public class PhotoListFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private PhotoListViewModel viewModel;

    //@BindView(R.id.photo_list)
    RecyclerView photoList;

    public PhotoListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_list, container, false);
        photoList = view.findViewById(R.id.photo_list);
        photoList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        photoList.setAdapter(null);     // to avoid memory leaks
    }

    //region Configuration

    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }

    private void configureViewModel(){
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PhotoListViewModel.class);
        viewModel.init();
        final PhotoAdapter photoAdapter = new PhotoAdapter();

        // Observe the paged list
        viewModel.getPhotos().observe(this, pagedList -> {
            Toast.makeText(this.getContext(), "Page " + pagedList.size(), Toast.LENGTH_SHORT).show();
            Log.i("Paging ", "Page " + pagedList.size());
            photoAdapter.submitList(pagedList);
        });

        photoList.setAdapter(photoAdapter);
    }

    //endregion
}
