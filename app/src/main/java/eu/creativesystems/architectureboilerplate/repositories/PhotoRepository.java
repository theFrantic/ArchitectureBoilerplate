package eu.creativesystems.architectureboilerplate.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

    private static int PAGE_SIZE = 10;          // for paging
    private static int PREFETCH_DISTANCE = 5;   // for paging

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

    /**
     * Method to return paged list of Photos
     * @return
     */
    public LiveData<PagedList<Photo>> getAllPhotos() {
        refreshAllPhotos();

        PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setPrefetchDistance(PREFETCH_DISTANCE)
                .setPageSize(PAGE_SIZE)
                .build();

        return (new LivePagedListBuilder(photoDao.allPhotos(), pagedListConfig)).build();
    }

    //region Private Methods

    private void refreshAllPhotos() {
        executor.execute(() -> {
            // Check if there is outdated rows
            int totalQty = photoDao.countAllRows();
            int outdatedQty = photoDao.countOutdatedRows(getMaxRefreshTime(new Date()));
            boolean hasOutdatedRows = (totalQty == 0) || (outdatedQty != 0);      // Table not empty and exists outdated data
            // If data is outdated
            if(hasOutdatedRows) {
                webservice.getAllPhotos().enqueue(new Callback<List<Photo>>() {
                    @Override
                    public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                        Log.i("ArchitectureBoilerplate", "Data refreshed from Network");
                        Toast.makeText(App.context, "Data refreshed from network !", Toast.LENGTH_LONG).show();
                        executor.execute(() -> {
                            List<Photo> photos = response.body();
                            for (Photo photo: photos) {
                                photo.setLastRefresh(new Date());
                                photoDao.insert(photo);       // TODO: Use bulk insert
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<List<Photo>> call, Throwable t) {
                        Log.e("ArchitectureBoilerplate", String.format("There was an error refreshing data from network: %s", t.getMessage()));
                        Toast.makeText(App.context, "There was an error refreshing data from network", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

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
                        Log.i("ArchitectureBoilerplate", "Data refreshed from Network");
                        Toast.makeText(App.context, "Data refreshed from network !", Toast.LENGTH_LONG).show();
                        executor.execute(() -> {
                            Photo photo = response.body();
                            photo.setLastRefresh(new Date());
                            photoDao.insert(photo);
                        });
                    }

                    @Override
                    public void onFailure(Call<Photo> call, Throwable t) {
                        Log.e("ArchitectureBoilerplate", String.format("There was an error refreshing data from network: %s", t.getMessage()));
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
