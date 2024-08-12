package com.example.carrentalfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity  {

    private GoogleMap mMap;
    private RecyclerView storeRecyclerView;
    private List<Store> storeList;
    private Car selectedCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location); // Ensure you have this layout file

        // Initialize the RecyclerView
        storeRecyclerView = findViewById(R.id.store_list);
        storeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load stores and set up the RecyclerView
        loadStores();
    }


    private void loadStores() {
        // Load store data (you might fetch this from a server or a local database)
        storeList = new ArrayList<>();
        storeList.add(new Store("Rexdale","123 Car Rental, M01234")); // Sample data
        storeList.add(new Store("Weston Road","123 Car Rental, M01234"));
        storeList.add(new Store("Etobicoke","123 Car Rental, M01234")); // Sample data
        storeList.add(new Store("ottawa","123 Car Rental, M01234"));
        storeList.add(new Store("NorthYork","123 Car Rental, M01234")); // Sample data
        storeList.add(new Store("Scarbourough","123 Car Rental, M01234"));
        storeList.add(new Store("winipeg","123 Car Rental, M01234")); // Sample data
        storeList.add(new Store("orilliea","123 Car Rental, M01234"));
        storeList.add(new Store("Barie","123 Car Rental, M01234")); // Sample data
        storeList.add(new Store("London","123 Car Rental, M01234"));
        storeList.add(new Store("Kitchner","123 Car Rental, M01234")); // Sample data
        storeList.add(new Store("Eglinton","123 Car Rental, M01234"));// Sample data

        // Set up adapter
        StoreAdapter adapter = new StoreAdapter(storeList, this, this::onStoreSelected);
        storeRecyclerView.setAdapter(adapter);
    }

    private void onStoreSelected(Store store) {
        // Handle the store selection
        // Navigate to the booking page with the selected store
        Intent intent = new Intent(LocationActivity.this, user_view_activity.class);
       // intent.putExtra("selectedStore", store);
       startActivity(intent);

    }
}
