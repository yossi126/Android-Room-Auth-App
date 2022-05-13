package com.example.bottomnavigationbardemo.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigationbardemo.R;
import com.example.bottomnavigationbardemo.pojo.ExampleItem;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.ViewHolder>{

    private ArrayList<ExampleItem> exampleItemArrayList;
    private OnItemClickListener onItemClickListener;

    public RecyclerViewAdapter(ArrayList<ExampleItem> exampleItemArrayList, OnItemClickListener onItemClickListener) {
        this.exampleItemArrayList = exampleItemArrayList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,onItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExampleItem currentObj = exampleItemArrayList.get(position);
        holder.textView.setText(currentObj.getText());
    }

    @Override
    public int getItemCount() {
        return exampleItemArrayList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onAddClick(int position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;
        public Button addBtn;
        public Button deleteBtn;
        public ImageView imageView;
        public OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewRV);
            addBtn = itemView.findViewById(R.id.addBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            imageView = itemView.findViewById(R.id.imageView);
            this.onItemClickListener = onItemClickL 2  [JR    /. fistener;
            itemView.setOnClickListener(this);

            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onDeleteClick(getAdapterPosition());
                    //addBtn.setText("ADDED");
                    //addBtn.setActivated(false);
                    addBtn.setEnabled(false);

                }
            });
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onDeleteClick(getAdapterPosition());
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageView.setImageResource(R.drawable.ic_baseline_delete_red);
                }
            });
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }
}
