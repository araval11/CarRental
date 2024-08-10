package com.example.carrentalfinalproject;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrentalfinalproject.R;
import com.example.carrentalfinalproject.Store;
import com.example.carrentalfinalproject.StoreAdapter;
import com.example.carrentalfinalproject.booking_fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class location_fragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private RecyclerView storeRecyclerView;
    private List<Store> storeList;
    private Car selectedCar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        // Get the passed car data
        if (getArguments() != null) {
            selectedCar = (Car) getArguments().getSerializable("carData"); // Use getParcelable if Car implements Parcelable
        }

        // Initialize the map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Initialize the RecyclerView
        storeRecyclerView = view.findViewById(R.id.store_list);
        storeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Load stores and set up the RecyclerView
        loadStores();

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        loadMapMarkers();
    }

    private void loadStores() {
        // Load store data (you might fetch this from a server or a local database)
        storeList = new ArrayList<>();
        storeList.add(new Store("Store 1", 37.7749, -122.4194)); // Sample data
        storeList.add(new Store("Store 2", 34.0522, -118.2437)); // Sample data

        // Set up adapter
        StoreAdapter adapter = new StoreAdapter(storeList, getContext(), this::onStoreSelected);
        storeRecyclerView.setAdapter(adapter);
    }

    private void loadMapMarkers() {
        if (mMap != null) {
            for (Store store : storeList) {
                LatLng location = new LatLng(store.getLatitude(), store.getLongitude());
                mMap.addMarker(new MarkerOptions().position(location).title(store.getName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
            }
        }
    }

    private void onStoreSelected(Store store) {
        // Handle the store selection
        // Navigate to the booking page with the selected store
        Bundle bundle = new Bundle();
        bundle.putSerializable("selectedStore", store);
        bundle.putSerializable("carData", selectedCar);

        booking_fragment bookingFragment = new booking_fragment();
        bookingFragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, bookingFragment)
                .addToBackStack(null)
                .commit();
    }
}
