package com.example.bottomnavigationbardemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.bottomnavigationbardemo.adapter.MyAdapter;
import com.example.bottomnavigationbardemo.pojo.ExampleItem;

import java.util.ArrayList;

public class ArrayAdapterTest2 extends AppCompatActivity implements MyAdapter.CustomButtonListener{

    ArrayList<ExampleItem> exampleItemArrayList;
    ListView listView;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array_adapter_test2);

        exampleItemArrayList = new ArrayList<>();
        exampleItemArrayList.add(new ExampleItem("1"));
        exampleItemArrayList.add(new ExampleItem("2"));
        exampleItemArrayList.add(new ExampleItem("3"));
        exampleItemArrayList.add(new ExampleItem("4"));
        exampleItemArrayList.add(new ExampleItem("5"));
        exampleItemArrayList.add(new ExampleItem("6"));




        listView = findViewById(R.id.listViewTest);
        myAdapter = new MyAdapter(ArrayAdapterTest2.this,R.layout.example_item,exampleItemArrayList,this);
        //myAdapter.setCustomButtonListner(this);
        listView.setAdapter(myAdapter);
    }

    @Override
    public void onAddbtnClickListner(int position) {
        Log.d("TAG", "onClick: "+position);

    }

    @Override
    public void onDelbtnClickListner(int position) {
        Log.d("TAG", "onClick: "+position);

    }
}