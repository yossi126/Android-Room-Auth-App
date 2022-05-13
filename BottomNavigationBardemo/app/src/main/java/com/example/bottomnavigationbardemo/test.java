package com.example.bottomnavigationbardemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.bottomnavigationbardemo.adapter.RecyclerViewAdapter;

import com.example.bottomnavigationbardemo.pojo.ExampleItem;

import java.util.ArrayList;

public class test extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener{

    ArrayList<ExampleItem> exampleItemArrayList;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        exampleItemArrayList = new ArrayList<>();
        exampleItemArrayList.add(new ExampleItem("1"));
        exampleItemArrayList.add(new ExampleItem("2"));
        exampleItemArrayList.add(new ExampleItem("3"));
        exampleItemArrayList.add(new ExampleItem("4"));
        exampleItemArrayList.add(new ExampleItem("5"));
        exampleItemArrayList.add(new ExampleItem("6"));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(exampleItemArrayList,this);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    public void onItemClick(int position) {
        Log.d("TAG", "onItemClick: "+position);
    }

    @Override
    public void onDeleteClick(int position) {
        Log.d("TAG", "onItemClick: "+position);
    }

    @Override
    public void onAddClick(int position) {
        Log.d("TAG", "onItemClick: "+position);
    }
}