package com.example.bottomnavigationbardemo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bottomnavigationbardemo.R;
import com.example.bottomnavigationbardemo.pojo.ExampleItem;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends ArrayAdapter<ExampleItem> {
    
    ArrayList<ExampleItem> exampleItemArrayList;
    CustomButtonListener customListner;

    public MyAdapter(@NonNull Context context, int resource, @NonNull List<ExampleItem> objects, CustomButtonListener customButtonListener) {
        super(context, resource, objects);
        exampleItemArrayList = (ArrayList<ExampleItem>) objects;
        customListner = customButtonListener;
    }

    public interface CustomButtonListener {
        void onAddbtnClickListner(int position);
        void onDelbtnClickListner(int position);
    }

//    public void setCustomButtonListner(CustomButtonListener listener) {
//        this.customListner = listener;
//    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //ExampleItem exampleItem = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,parent,false);
        }
        TextView textView = convertView.findViewById(R.id.textViewRV);
        Button addbtn = convertView.findViewById(R.id.addBtn);
        Button delbtn = convertView.findViewById(R.id.deleteBtn);

        textView.setText(exampleItemArrayList.get(position).getText());
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("TAG", "onClick: "+exampleItemArrayList.get(position).getText());
                customListner.onAddbtnClickListner(position);
            }
        });
        delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("TAG", "onClick: "+exampleItemArrayList.get(position));
                customListner.onDelbtnClickListner(position);

            }
        });
        return convertView;
    }




}
