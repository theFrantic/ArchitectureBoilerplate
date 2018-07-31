package eu.creativesystems.architectureboilerplate.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import javax.inject.Inject;

import eu.creativesystems.architectureboilerplate.database.entity.Photo;
import eu.creativesystems.architectureboilerplate.repositories.PhotoRepository;

public class PhotoListViewModel extends ViewModel {

    private LiveData<PagedList<Photo>> pagedListPhoto;
    private PhotoRepository photoRepo;

    @Inject
    public PhotoListViewModel(PhotoRepository photoRepo) { this.photoRepo = photoRepo; }

    public void init() {
        if (this.pagedListPhoto != null) {
            return;
        }
        pagedListPhoto = photoRepo.getAllPhotos();
    }

    public LiveData<PagedList<Photo>> getPhotos() { return this.pagedListPhoto; }
}
