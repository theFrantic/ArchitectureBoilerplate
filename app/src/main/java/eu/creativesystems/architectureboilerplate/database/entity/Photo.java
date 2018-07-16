package eu.creativesystems.architectureboilerplate.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Entity class to model the database data
 */

@Entity
public class Photo {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("albumId")
    @Expose
    private int albumid;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("url")
    @Expose
    private String url;

   @SerializedName("thumbnailUrl")
   @Expose
   private String thumbnailUrl;

   private Date lastRefresh;

    //region Contructors

    public Photo() {}

    public Photo(@NonNull int id,
                 int albumid,
                 String title,
                 String url,
                 String thumbnailUrl,
                 Date lastRefresh) {
        this.id = id;
        this.albumid = albumid;
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
        this.lastRefresh = lastRefresh;
    }

    //endregion

    //region Getters

    public int getAlbumid() {
        return albumid;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Date getLastRefresh() {
        return lastRefresh;
    }

    //endregion

    //region Setters

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public void setAlbumid(int albumid) {
        this.albumid = albumid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setLastRefresh(Date lastRefresh) {
        this.lastRefresh = lastRefresh;
    }

    //endregion
}
