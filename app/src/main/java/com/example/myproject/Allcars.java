package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Type;
import java.util.List;



public class Allcars extends Fragment {

    EditText etSearch;
    TextView tabAll, tabSedan, tabSUV, tabHatch, tabElectric, tabLuxury;
    RecyclerView recyclerCars;
    APiCarAdapter apiCarAdapter;
    List<ApiAllCar> carList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_allcars, container, false);

        etSearch = view.findViewById(R.id.etSearch);
        recyclerCars = view.findViewById(R.id.recyclerCars);
        recyclerCars.setLayoutManager(new LinearLayoutManager(getContext()));

        tabAll = view.findViewById(R.id.tabAll);
        tabSedan = view.findViewById(R.id.tabSedan);
        tabSUV = view.findViewById(R.id.tabSUV);
        tabHatch = view.findViewById(R.id.tabHatch);
        tabElectric = view.findViewById(R.id.tabElectric);
        tabLuxury = view.findViewById(R.id.tabLuxury);

        loginapi.getAllCars(new loginapi.LoginCallback() {
            @Override
            public void onResult( boolean success, String response) {
                if (success) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<ApiAllCar>>(){}.getType();
                            carList = gson.fromJson(response, listType);

                            apiCarAdapter = new APiCarAdapter(getContext(), carList);
                            recyclerCars.setAdapter(apiCarAdapter);

                        } catch (Exception e) {
                            Log.e("CarAPI", "Parsing error: " + e.getMessage());
                        }
                    });
                } else {
                    Log.d("CarAPI", "API failed: " + response);
                }
            }
        });

        setTabClick(tabAll);
        setTabClick(tabSedan);
        setTabClick(tabSUV);
        setTabClick(tabHatch);
        setTabClick(tabElectric);
        setTabClick(tabLuxury);

        return view;
    }

    private void setTabClick(TextView tab) {
        tab.setOnClickListener(v ->
                Toast.makeText(requireContext(), tab.getText().toString() + " clicked", Toast.LENGTH_SHORT).show());
    }
}

