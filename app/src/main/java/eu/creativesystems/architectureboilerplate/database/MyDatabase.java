package eu.creativesystems.architectureboilerplate.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import eu.creativesystems.architectureboilerplate.database.converter.DateConverter;
import eu.creativesystems.architectureboilerplate.database.dao.PhotoDao;
import eu.creativesystems.architectureboilerplate.database.entity.Photo;

@Database(entities = {Photo.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class MyDatabase extends RoomDatabase {

    // SINGLETON
    private static volatile MyDatabase INSTANCE;

    // DAO
    public abstract PhotoDao photoDao();
}
