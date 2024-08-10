package com.example.carrentalfinalproject;

import static com.example.carrentalfinalproject.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class user_view_activity extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;
    public vehicle_category_fragment vehicleCategoryFragment;

    public booking_fragment bookingFragment;
    public account_fragment accountFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);

        initComponents();
        setFragment(vehicleCategoryFragment);
        clickListener();
    }

    private void initComponents(){
        bottomNavigationView = findViewById(R.id.bottom_nav);
        vehicleCategoryFragment = new vehicle_category_fragment();
        bookingFragment= new booking_fragment();

        accountFragment = new account_fragment();
    }

    public void clickListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.nav_vehicle) {
                    setFragment(vehicleCategoryFragment);
                    return true;
                } else if (id == R.id.nav_booking) {
                    setFragment(bookingFragment);
                    return true;
                } else if (id == R.id.nav_account) {
                    setFragment(accountFragment);
                    return true;
                }
                return false;
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment);
        fragmentTransaction.commit();
    }
}