package eu.creativesystems.architectureboilerplate.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import eu.creativesystems.architectureboilerplate.database.entity.Photo;
import eu.creativesystems.architectureboilerplate.repositories.PhotoRepository;

public class PhotoViewModel extends ViewModel{

    private LiveData<Photo> photo;
    private PhotoRepository photoRepo;

    @Inject
    public PhotoViewModel(PhotoRepository photoRepo) {
        this.photoRepo = photoRepo;
    }

    public void init(int id) {
        if (this.photo != null) {
            return;
        }
        photo = photoRepo.getPhoto(id);         // We ask the data to the Repo not to db directly!!!
    }

    public LiveData<Photo> getPhoto() {
        return this.photo;
    }
}
