package eu.creativesystems.architectureboilerplate.ui.adapters;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import eu.creativesystems.architectureboilerplate.R;
import eu.creativesystems.architectureboilerplate.database.entity.Photo;

public class PhotoAdapter extends PagedListAdapter<Photo, PhotoAdapter.PhotoItemViewHolder> {

    public PhotoAdapter(){
        super(DIFF_CALLBACK);
    }

    @Override
    public PhotoItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.photo_item, parent, false);
        return new PhotoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoItemViewHolder holder, int position) {
        Photo photo = getItem(position);
        if (photo != null) {
            holder.bindToPhoto(photo);
        }
    }

    //region DiffCallback

    // This allow us to compare the objects in order to verify if it is new
    public static DiffUtil.ItemCallback<Photo> DIFF_CALLBACK = new DiffUtil.ItemCallback<Photo>() {
        @Override
        public boolean areItemsTheSame(@NonNull Photo oldItem, @NonNull Photo newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Photo oldItem, @NonNull Photo newItem) {
            return oldItem.equals(newItem);
        }
    };


    //endregion

    /**
     * ViewHolder to avoid add another file
     */
    static class PhotoItemViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImage;
        TextView photoTitle;
        View view;

        public PhotoItemViewHolder(View itemView){
            super(itemView);
            view = itemView;
            photoImage = (ImageView) itemView.findViewById(R.id.photo_image);
            photoTitle = (TextView) itemView.findViewById(R.id.photo_title);
        }

        public void bindToPhoto(Photo photo) {
            if(photo.getThumbnailUrl() != null && !photo.getThumbnailUrl().isEmpty()) {
                Glide.with(view.getContext())
                        .load(photo.getThumbnailUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(photoImage);
            }
            else {
                Glide.with(view.getContext())
                        .load(R.drawable.ic_launcher_background);   //Placeholder
            }
            photoTitle.setText(photo.getTitle());
        }
    }
}
