package com.ghatnandurkar.imageloadinglibrary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by
 *
 * @author Amol Deshmukh
 * @since 27/02/19
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyHolder> {
    List<String> urls;

    public TestAdapter(List<String> url) {
        this.urls = url;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        ImageManager imageManager = ImageManager.getInstance();
        imageManager.loadImage(urls.get(position), holder.imageView);


    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
