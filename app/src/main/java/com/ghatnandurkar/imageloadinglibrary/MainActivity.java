package com.ghatnandurkar.imageloadinglibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclervoew);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> list= new ArrayList<>();
        list.add("https://www.gstatic.com/webp/gallery/1.jpg");
        list.add("https://www.gstatic.com/webp/gallery/2.jpg");
        list.add("https://www.gstatic.com/webp/gallery/3.jpg");
        list.add("https://www.gstatic.com/webp/gallery/4.jpg");
        list.add("https://www.gstatic.com/webp/gallery/5.jpg");
        recyclerView.setAdapter(new TestAdapter(list));
    }
}
