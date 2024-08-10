package com.example.carrentalfinalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrentalfinalproject.Car;
import com.example.carrentalfinalproject.CarAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class booking_fragment extends Fragment {

    private RecyclerView recyclerView;
    private CarAdapter adapter;
    private List<Car> carList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.booking_fragment, container, false);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson = new Gson();
        String json = sharedPreferences.getString("bookedCars", "");
        Type type = new TypeToken<List<Car>>() {}.getType();
        carList = gson.fromJson(json, type);


        if (carList == null) {
            carList = new ArrayList<>();
        }


        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new CarAdapter(getActivity(), carList,"booking"); // Pass context and carList to the adapter
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }




}
