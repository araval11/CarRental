package com.example.carrentalfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carrentalfinalproject.R;

public class CarDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_description);

        // Retrieve car information from Intent extras
        Intent intent = getIntent();
        if (intent != null) {
            String brand = intent.getStringExtra("brand");
            String model = intent.getStringExtra("model");
            int year = intent.getIntExtra("year", 0);
            String color = intent.getStringExtra("color");
            int price = intent.getIntExtra("price", 0);
            int imgResId = intent.getIntExtra("img", 0);
            String desc = intent.getStringExtra("desc");

            // Set car information to TextViews
            TextView brandTextView = findViewById(R.id.brand_text_view);
            brandTextView.setText(brand);

            TextView modelTextView = findViewById(R.id.model_text_view);
            modelTextView.setText(model);

            TextView yearTextView = findViewById(R.id.year_text_view);
            yearTextView.setText(String.valueOf(year));

            TextView colorTextView = findViewById(R.id.color_text_view);
            colorTextView.setText(color);

            TextView priceTextView = findViewById(R.id.price_text_view);
            priceTextView.setText("$" + price + "/day");

            // Set car image
            ImageView imageView = findViewById(R.id.car_image_view);
            imageView.setImageResource(imgResId);

            TextView descTextView = findViewById(R.id.description);
            descTextView.setText(desc);
        }
    }
}
