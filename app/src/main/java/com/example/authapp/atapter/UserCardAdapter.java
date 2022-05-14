package com.example.authapp.atapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.MediaActivity;
import com.example.authapp.R;
import com.example.authapp.entity.Media;
import com.example.authapp.util.DataViewModel;

import java.util.List;

public class UserCardAdapter extends  RecyclerView.Adapter<UserCardAdapter.ViewHolder>{

    private List<Media> mediaList;
    private OnItemClickListener onItemClickListener;
    private DataViewModel dataViewModel;
    private SharedPreferences sharedPreferences;
    private Context context;
    private int userId;

    public UserCardAdapter(List<Media> mediaList, OnItemClickListener onItemClickListener,DataViewModel dataViewModel,Context context) {
        this.mediaList = mediaList;
        this.onItemClickListener = onItemClickListener;
        //new idea
        this.dataViewModel = dataViewModel;
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("UserPref",MODE_PRIVATE);
        this.userId = sharedPreferences.getInt("UserId",0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,onItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Media media = mediaList.get(position);
            holder.textView.setText(String.valueOf(media.getMediaId()));

            dataViewModel.isExistsUsersMediaCrossRef(userId,mediaList.get(position).getMediaId()).observe((LifecycleOwner) this.context, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer.intValue() == 1){
                    holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_red);
                    holder.favoriteImageView.setEnabled(false);
                    Toast.makeText(context,"added to favorite",Toast.LENGTH_SHORT).show();
                }else{
                    holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_gray);
                    holder.favoriteImageView.setEnabled(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;
        public ImageView deleteImageView;
        public ImageView favoriteImageView;
        OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            textView = itemView.findViewById(R.id.MediaNameTextView);
            favoriteImageView = itemView.findViewById(R.id.addToFavoriteIv);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);

            favoriteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onAddClick(mediaList.get(getAdapterPosition()).getMediaId());
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
        void onAddClick(int position);
    }
}
