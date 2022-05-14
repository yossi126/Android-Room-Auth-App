package com.example.authapp.atapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.R;
import com.example.authapp.entity.Media;
import com.example.authapp.util.DataViewModel;

import java.util.List;

public class FavoriteAdapter extends  RecyclerView.Adapter<FavoriteAdapter.ViewHolder>{

    private List<Media> mediaList;
    private OnItemClickListener onItemClickListener;
    private DataViewModel dataViewModel;
    private SharedPreferences sharedPreferences;
    private Context context;
    private int userId;

    public FavoriteAdapter(List<Media> mediaList,OnItemClickListener onItemClickListener, DataViewModel dataViewModel, Context context) {
        this.mediaList = mediaList;
        this.onItemClickListener = onItemClickListener;
        //
        this.dataViewModel = dataViewModel;
        this.sharedPreferences = context.getSharedPreferences("UserPref",MODE_PRIVATE);
        this.context = context;
        this.userId = sharedPreferences.getInt("UserId",0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_item_favorite_adapter,parent,false);
       ViewHolder viewHolder = new ViewHolder(view,onItemClickListener);
       return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        Media media = mediaList.get(position);
        holder.textView.setText(String.valueOf(media.getMediaId()));
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;
        public ImageView deleteImageView;
        OnItemClickListener onItemClickListener;
        public ViewHolder(@NonNull View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            textView = itemView.findViewById(R.id.MediaNameTextView);
            deleteImageView = itemView.findViewById(R.id.deleteImageView);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);

            deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onDeleteClick(mediaList.get(getAdapterPosition()).getMediaId());
                }
            });
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(mediaList.get(getAdapterPosition()).getMediaPath());
        }
    }

    public interface OnItemClickListener {

        void onItemClick(String uri);
        void onDeleteClick(int mediaId);
    }
}
