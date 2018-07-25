package eu.creativesystems.architectureboilerplate.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.Date;
import java.util.List;

import eu.creativesystems.architectureboilerplate.database.entity.Photo;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Data Access Objet for Photo Entity
 */

@Dao
public interface PhotoDao {

    @Insert(onConflict = REPLACE)
    void insert(Photo photo);

    @Insert(onConflict = REPLACE)
    void insert(List<Photo> photos);

    @Query("SELECT * FROM photo WHERE id = :id")
    LiveData<Photo> load(int id);

    @Query("SELECT * FROM photo WHERE id = :id AND lastRefresh > :lastRefreshMax LIMIT 1")
    Photo hasPhoto(int id, Date lastRefreshMax);

    @Query("SELECT COUNT(*) FROM photo WHERE lastRefresh > :lastRefreshMax")
    int countOutdatedRows(Date lastRefreshMax);

    @Query("SELECT COUNT(*) FROM photo")
    int countAllRows();

    @Query("SELECT * FROM photo ORDER BY id ASC")
    DataSource.Factory<Integer, Photo> allPhotos();
}
