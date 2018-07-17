package eu.creativesystems.architectureboilerplate.repositories;

import android.arch.lifecycle.LiveData;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import eu.creativesystems.architectureboilerplate.App;
import eu.creativesystems.architectureboilerplate.api.PhotoWebservice;
import eu.creativesystems.architectureboilerplate.database.dao.PhotoDao;
import eu.creativesystems.architectureboilerplate.database.entity.Photo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Data Repository for Photos on the Application
 */
@Singleton
public class PhotoRepository {

    private static int REFRESH_TIMEOUT_IN_MINUTES = 1;

    private final PhotoWebservice webservice;
    private final PhotoDao photoDao;
    private final Executor executor;

    @Inject
    public PhotoRepository(PhotoWebservice webservice, PhotoDao photoDao, Executor executor) {
        this.webservice = webservice;
        this.photoDao = photoDao;
        this.executor = executor;
    }

    public LiveData<Photo> getPhoto(int id) {
        refreshPhoto(id);
        return photoDao.load(id);
    }

    //region Private Methods

    /**
     * Method to verify if we need to get the data from the webservice or just from database
     * @param id
     */
    private void refreshPhoto(final int id) {
        executor.execute(() -> {
            // Check if photo was fetched recently
            boolean photoExists = (photoDao.hasPhoto(id, getMaxRefreshTime(new Date())) != null);
            // If photo have to be updated
            if(!photoExists) {
                webservice.getPhoto(id).enqueue(new Callback<Photo>() {
                    @Override
                    public void onResponse(Call<Photo> call, final Response<Photo> response) {
                        Log.e("ArchitectureBoilerplate", "Data refreshed from Network");
                        Toast.makeText(App.context, "Data refreshed from network !", Toast.LENGTH_LONG).show();
                        executor.execute(() -> {
                            Photo photo = response.body();
                            photo.setLastRefresh(new Date());
                            photoDao.save(photo);
                        });
                    }

                    @Override
                    public void onFailure(Call<Photo> call, Throwable t) {
                        Toast.makeText(App.context, "There was an error refreshing data from network", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    /**
     * Calculates the refresh time based on the REFRESH_TIMEOUT_IN_MINUTES
     * @param currentDate
     * @return
     */
    private Date getMaxRefreshTime(Date currentDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MINUTE, - REFRESH_TIMEOUT_IN_MINUTES);
        return cal.getTime();
    }

    //endregion
}
