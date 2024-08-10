package com.example.carrentalfinalproject;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class vehicle_category_fragment extends Fragment {

    private RecyclerView recyclerView;
    private CarAdapter adapter;
    private Button search;
    private EditText searchEditText;
    private List<Car> carList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vehicle_category_fragment, container, false);

        // Initialize UI components
        search = view.findViewById(R.id.search);
        searchEditText = view.findViewById(R.id.searchBar);
        recyclerView = view.findViewById(R.id.recyclerView);

        // Parse JSON data and set up RecyclerView
        carList = parseJsonData();
        adapter = new CarAdapter(getActivity(), carList, "vehicle");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Set up search functionality
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchEditText.getText().toString().trim().toLowerCase();
                List<Car> filteredList = new ArrayList<>();
                for (Car car : carList) {
                    if (car.getBrand().toLowerCase().contains(searchText)) {
                        filteredList.add(car);
                    }
                }
                adapter.filterList(filteredList);
            }
        });

        return view;
    }

    private List<Car> parseJsonData() {
        List<Car> carList = new ArrayList<>();

        // JSON data
        String jsonData = "[{\"id\":1,\"brand\":\"Toyota\",\"model\":\"Corolla\",\"year\":2022,\"color\":\"Silver\",\"price\":150,\"img\":2131165413}," +
                "{\"id\":2,\"brand\":\"Honda\",\"model\":\"Civic\",\"year\":2021,\"color\":\"Red\",\"price\":270,\"img\":2131165414}," +
                "{\"id\":3,\"brand\":\"Ford\",\"model\":\"Mustang\",\"year\":2023,\"color\":\"Black\",\"price\":350,\"img\":2131165415}," +
                "{\"id\":4,\"brand\":\"Chevrolet\",\"model\":\"Camaro\",\"year\":2022,\"color\":\"Yellow\",\"price\":380,\"img\":2131165416}," +
                "{\"id\":5,\"brand\":\"BMW\",\"model\":\"X5\",\"year\":2020,\"color\":\"White\",\"price\":450,\"img\":2131165417}," +
                "{\"id\":6,\"brand\":\"Toyota\",\"model\":\"Camry\",\"year\":2022,\"color\":\"white\",\"price\":150,\"img\":2131165420}," +
                "{\"id\":7,\"brand\":\"Honda\",\"model\":\"Amaze\",\"year\":2022,\"color\":\"Coffe\",\"price\":170,\"img\":2131165419}," +
                "{\"id\":8,\"brand\":\"Ford\",\"model\":\"GT-40\",\"year\":2022,\"color\":\"Silver\",\"price\":350,\"img\":2131165421}," +
                "{\"id\":9,\"brand\":\"Chevrolet\",\"model\":\"Malibu\",\"year\":2022,\"color\":\"Blue\",\"price\":180,\"img\":2131165422}]";

        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject carObject = jsonArray.getJSONObject(i);

                int id = carObject.getInt("id");
                String brand = carObject.getString("brand");
                String model = carObject.getString("model");
                int year = carObject.getInt("year");
                String color = carObject.getString("color");
                int price = carObject.getInt("price");
                int img = carObject.getInt("img");
                String desc = "Cars are vehicles used for transportation, typically with four wheels, powered by internal combustion engines or electric motors. They come in various types " +
                        "such as sedans, SUVs, trucks, and sports cars, offering different features, sizes, and capabilities to meet diverse needs and preferences. Cars are essential for mobility and convenience in modern life.";

                Car car = new Car(id, brand, model, year, color, price, img, desc);
                carList.add(car);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return carList;
    }
}
