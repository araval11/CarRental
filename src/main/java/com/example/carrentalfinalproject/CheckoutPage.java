package com.example.carrentalfinalproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CheckoutPage extends AppCompatActivity {

    private Car selectedCar;
    public Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_page); // Use the same layout as the fragment
        confirmButton = findViewById(R.id.confirm_button);
        // Get the passed car data
        if (getIntent() != null && getIntent().hasExtra("carData")) {
            selectedCar = (Car) getIntent().getSerializableExtra("carData");
        }

        // Initialize UI elements
        TextView carDetailsTextView = findViewById(R.id.car_details_text_view);
        TextView billingTextView = findViewById(R.id.billing_text_view);

        // Display car details
        if (selectedCar != null) {
            carDetailsTextView.setText("Brand: " + selectedCar.getBrand() + "\n" +
                    "Model: " + selectedCar.getModel() + "\n" +
                    "Year: " + selectedCar.getYear() + "\n" +
                    "Color: " + selectedCar.getColor() + "\n" +
                    "Price: $" + selectedCar.getPrice() + "/day");

            // Calculate billing (you can modify this as needed)
            int rentalDays = 7; // Example: 7 days rental
            double totalPrice = rentalDays * selectedCar.getPrice();
            billingTextView.setText("Total Price: $" + totalPrice + "\nRental Duration: " + rentalDays + " days");
        }
        confirmButton.setOnClickListener(v -> {
            // Handle the confirmation logic here...

            // Navigate to a confirmation page or show a success message
            Toast.makeText(CheckoutPage.this, "Booking confirmed!", Toast.LENGTH_SHORT).show();

            // You can also navigate to another activity or close this one
            // Example: Closing the activity after confirmation
            finish(); // This will close the current activity
        });
    }
}
