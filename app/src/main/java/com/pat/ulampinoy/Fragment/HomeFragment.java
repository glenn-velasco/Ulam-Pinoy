package com.pat.ulampinoy.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pat.ulampinoy.Dish.Dish;
import com.pat.ulampinoy.Dish.DishAdapter;
import com.pat.ulampinoy.R;
import com.pat.ulampinoy.RetroProfit.ApiHandler;
import com.pat.ulampinoy.RetroProfit.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;

    private DishAdapter adapter;

    private List<Dish> dishList;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_home_categories);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dishList = new ArrayList<>();

        adapter = new DishAdapter(getContext(), dishList);

        recyclerView.setAdapter(adapter);

        fetchDishes();
    }

    private void fetchDishes() {

        ApiService apiService = ApiHandler.getApiService();
        Call<List<Dish>> call = apiService.getAllDishes();

        call.enqueue(new Callback<List<Dish>>() {
            @Override
            public void onResponse(Call<List<Dish>> call, Response<List<Dish>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    dishList.clear();

                    dishList.addAll(response.body());

                    adapter.notifyDataSetChanged();

                } else {

                    Toast.makeText(getContext(), "Server Error: " + response.code(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<Dish>> call, Throwable t) {

                Log.e("API_ERROR", t.getMessage());

                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}