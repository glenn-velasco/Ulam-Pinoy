package com.pat.ulampinoy.Dish;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pat.ulampinoy.Dish.Dish;
import com.pat.ulampinoy.R;

import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {

    private Context context;

    private List<Dish> dishList;

    public DishAdapter(Context context, List<Dish> dishList) {

        this.context = context;

        this.dishList = dishList;

    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);

        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {

        Dish dish = dishList.get(position);

        if (dish.getName() != null) {

            holder.tvName.setText(dish.getName());

        } else {

            holder.tvName.setText("Unknown Dish");

        }

        String rawUrl = "";

        List<String> images = dish.getImages();

        if (images != null && !images.isEmpty()) {

            rawUrl = images.get(0);

            rawUrl = convertToRawUrl(rawUrl);
        }

        Glide.with(context)
                .load(rawUrl) // Load the clean String, not the List!
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery)
                .centerCrop()
                .into(holder.ivImage);

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(context, DishDetailActivity.class);

            intent.putExtra("DISH_DATA", dish);

            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {

        return dishList.size();
    }

    public static class DishViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;

        ImageView ivImage;

        public DishViewHolder(@NonNull View itemView) {

            super(itemView);

            tvName = itemView.findViewById(R.id.tv_category_name);

            ivImage = itemView.findViewById(R.id.iv_category_image);
        }
    }

    private String convertToRawUrl(String url) {

        if (url.contains("github.com") && url.contains("/blob/")) {

            return url.replace("github.com", "raw.githubusercontent.com")
                    .replace("/blob/", "/")
                    .replaceAll("\\?.*$", "");
        }

        return url;
    }
}