package com.example.carrentalfinalproject;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrentalfinalproject.Car;
import com.example.carrentalfinalproject.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    public static Context context;
    private List<Car> carList;
    private static String type;

    public CarAdapter(Context context, List<Car> carList, String type) {
        this.context = context;
        this.carList = carList;
        this.type = type;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.bind(car);

        holder.bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("booking")) {
                    cancelBookedCar(car);
                } else if (type.equals("vehicle")) {
                    Log.i("addBooked Clicked","Clicking...");
                    addBookedCar(car);
                }
            }
        });



        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start CarDescriptionActivity and pass car information
                Intent intent = new Intent(context, CarDescriptionActivity.class);
                intent.putExtra("brand", car.getBrand());
                intent.putExtra("model", car.getModel());
                intent.putExtra("year", car.getYear());
                intent.putExtra("color", car.getColor());
                intent.putExtra("price", car.getPrice());
                intent.putExtra("img", car.getimg());
                intent.putExtra("desc",car.getDesc());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {

        private TextView brandTextView;
        private TextView modelTextView;
        private TextView yearTextView;
        private TextView colorTextView;
        private TextView priceTextView;

        private ImageView imageView;
        private Button bookButton;
        private Button shareButton;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            brandTextView = itemView.findViewById(R.id.brand_text_view);
            modelTextView = itemView.findViewById(R.id.model_text_view);
            yearTextView = itemView.findViewById(R.id.year_text_view);
            colorTextView = itemView.findViewById(R.id.color_text_view);
            priceTextView = itemView.findViewById(R.id.price_text_view);
            imageView = itemView.findViewById(R.id.car_image_view);
            bookButton = itemView.findViewById(R.id.book_button);
            shareButton = itemView.findViewById(R.id.share_button);
        }

        public void bind(Car car) {
            brandTextView.setText(car.getBrand());
            modelTextView.setText(car.getModel());
            yearTextView.setText(String.valueOf(car.getYear()));
            colorTextView.setText(car.getColor());
            try{
                imageView.setImageResource(car.getimg());
            }catch (Exception e){
                Log.i("Exception of Car Adapter Image", String.valueOf(e));
            }
            priceTextView.setText("$" + car.getPrice()+"/day");
            if (type.equals("booking")) {
                bookButton.setText("Cancel");
            } else if (type.equals("Vehicle")) {
                bookButton.setText("Book Now");
            }

            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareCar(car);
                }
            });
        }

        private void shareCar(Car car) {
            String message = "Check out this car:\n" +
                    "Brand: " + car.getBrand() + "\n" +
                    "Model: " + car.getModel() + "\n" +
                    "Year: " + car.getYear() + "\n" +
                    "Color: " + car.getColor() + "\n" +
                    "Price: $" + car.getPrice() + "/day";

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, message);
            context.startActivity(Intent.createChooser(shareIntent, "Share Car Details"));
        }


    }



    public void filterList(List<Car> filteredList) {
        carList = filteredList;
        notifyDataSetChanged();
    }
    private void addBookedCar(Car car) {

        Log.i("addBooked Clicked","Clicked...");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("bookedCars", "");
        Type type = new TypeToken<List<Car>>() {}.getType();
        List<Car> bookedCarsList = gson.fromJson(json, type);

        if (bookedCarsList == null) {
            bookedCarsList = new ArrayList<>();
        }

        // Add the new car to the list
        bookedCarsList.add(car);
        Log.i("addBooked Clicked",bookedCarsList.toString());

        // Create a new instance of the LocationFragment
        location_fragment locationFragment = new location_fragment();
        // Create a bundle to pass the Car object
        Bundle bundle = new Bundle();
        bundle.putSerializable("carData", car);  // Use putParcelable if Car implements Parcelable
        // Set the arguments for the fragment
        locationFragment.setArguments(bundle);
        // Assuming you're using a FragmentManager and a container to replace the fragment
        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.map, locationFragment) // Ensure this ID matches your container
                .addToBackStack(null)
                .commit();

        // Save the updated list back to SharedPreferences
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        String updatedJson = gson.toJson(bookedCarsList);
//        editor.putString("bookedCars", updatedJson);
//        editor.apply();
//        Log.i("addBooked Clicked",updatedJson);
//
//        // Notify the user and refresh the RecyclerView
//        Toast.makeText(context, "Car booked successfully", Toast.LENGTH_SHORT).show();
//        notifyDataSetChanged();
    }


    public void cancelBookedCar(Car car) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("bookedCars", "");
        Type type = new TypeToken<List<Car>>() {}.getType();
        List<Car> bookedCarsList = gson.fromJson(json, type);

        if (bookedCarsList != null) {
            // Log the current list size before removal
            Log.d("CancelBookedCar", "Before removal: " + bookedCarsList.size()+bookedCarsList);
            Log.d("CancelBookedCar", "Removed car: " + car.toString());
            // Check if the car object exists in the list
            if (bookedCarsList.contains(car)) {
                bookedCarsList.remove(car);

                // Log the removed car object
                Log.d("CancelBookedCar", "Removed car: " + car.toString());

                // Update SharedPreferences with the modified list
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String updatedJson = gson.toJson(bookedCarsList);
                editor.putString("bookedCars", updatedJson);
                editor.apply();

                // Toast message for successful cancellation
                Toast.makeText(context, "Canceled successfully. It will be removed from the list after 24 hrs", Toast.LENGTH_SHORT).show();

                // Refresh RecyclerView
                notifyDataSetChanged();
            } else {
                // Log a message if the car object is not found in the list
                Log.d("CancelBookedCar", "Car not found in bookedCarsList");
            }

            // Log the current list size after removal
            Log.d("CancelBookedCar", "After removal: " + bookedCarsList.size()+bookedCarsList);
        } else {
            // Log a message if bookedCarsList is null
            Log.d("CancelBookedCar", "bookedCarsList is null");
        }
    }
}
