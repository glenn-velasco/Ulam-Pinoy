package com.pat.ulampinoy.Dish;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.pat.ulampinoy.R;

public class DishDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_dish_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Dish dish = (Dish) getIntent().getSerializableExtra("DISH_DATA");

        if (dish == null) return;

        ImageView ivImage = findViewById(R.id.iv_detail_image);

        TextView tvName = findViewById(R.id.tv_detail_name);

        TextView tvDesc = findViewById(R.id.tv_detail_description);

        TextView tvIngredients = findViewById(R.id.tv_detail_ingredients);

        tvName.setText(dish.getName());

        tvDesc.setText(dish.getDescription());

        StringBuilder ingredients = new StringBuilder();

        if (dish.getMeats() != null) {

            for (String meat : dish.getMeats()) ingredients.append("• ").append(meat).append("\n");

        }

        if (dish.getVegetables() != null) {

            for (String veg : dish.getVegetables()) ingredients.append("• ").append(veg).append("\n");

        }

        tvIngredients.setText(ingredients.toString());

        Glide.with(this)
                .load(dish.getPrimaryImageUrl())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(ivImage);

        ImageButton btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(v -> {

            finish();

        });

    }

}