package eu.creativesystems.architectureboilerplate.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.creativesystems.architectureboilerplate.api.PhotoWebservice;
import eu.creativesystems.architectureboilerplate.database.MyDatabase;
import eu.creativesystems.architectureboilerplate.database.dao.PhotoDao;
import eu.creativesystems.architectureboilerplate.repositories.PhotoRepository;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



@Module(includes = ViewModelModule.class)
public class AppModule {

    //region Database Injection

    @Provides
    @Singleton
    MyDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application,
                MyDatabase.class, "MyDatabase.db")
                .build();
    }

    @Provides
    @Singleton
    PhotoDao providePhotoDao(MyDatabase database) { return database.photoDao(); }

    //endregion

    //region Repository Injection

    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    PhotoRepository providePhotoRepository(PhotoWebservice webservice, PhotoDao photoDao, Executor executor) {
        return new PhotoRepository(webservice, photoDao, executor);
    }

    //endregion

    //region Network Injection

    private static String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @Provides
    Gson provideGson() { return new GsonBuilder().create(); }

    /**
     * Configurates retrofit
     * @param gson
     * @return
     */
    @Provides
    Retrofit provideRetrofit(Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    PhotoWebservice provideApiWebservice(Retrofit restAdapter) {
        return restAdapter.create(PhotoWebservice.class);
    }

    //endregion
}
