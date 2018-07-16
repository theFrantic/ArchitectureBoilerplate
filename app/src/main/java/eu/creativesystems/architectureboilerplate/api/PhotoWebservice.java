package eu.creativesystems.architectureboilerplate.api;

import eu.creativesystems.architectureboilerplate.database.entity.Photo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PhotoWebservice {

    @GET("photos/{id}")
    Call<Photo> getPhoto(@Path("id") int id);
}
