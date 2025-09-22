package com.example.myproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class APiCarAdapter extends RecyclerView.Adapter<APiCarAdapter.CarViewHolder> {

    private List<ApiAllCar> carList;
    private Context context;

    public APiCarAdapter(Context context, List<ApiAllCar> carList) {
        this.context = context;
        this.carList = carList;
    }

    // Add this missing method
    public void updateCarList(List<ApiAllCar> newCarList) {
        this.carList = newCarList;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate your CARD layout (not activity)
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        ApiAllCar car = carList.get(position);

        // ✅ set values
        holder.tvCarName.setText(car.getCarName());
        holder.tvCarModel.setText(car.getVariant());
        holder.tvPrice.setText("₹ " + car.getPrice());

        // optional if you added these IDs in XML
        if (holder.tvYear != null) holder.tvYear.setText(String.valueOf(car.getYearOfManufacture()));
        if (holder.tvFuel != null) holder.tvFuel.setText(car.getFuelType());
        if (holder.tvLocation != null) holder.tvLocation.setText(car.getLocation());
        if (holder.tvKm != null) holder.tvKm.setText(car.getKilometersDriven() + " km");
        if (holder.tvOwner != null) holder.tvOwner.setText(car.getNoOfOwners() + " Owner");

        holder.ivCar.setImageResource(R.drawable.bmw);

        holder.btnViewDetails.setOnClickListener(v -> {
            openCarDetails(
                    car.getCarName(),
                    car.getVariant(),
                    "₹ " + car.getPrice(),
                    String.valueOf(car.getYearOfManufacture()),
                    car.getKilometersDriven() + " km",
                    car.getFuelType(),
                    car.getNoOfOwners(),
                    car.getLocation()
            );
        });
    }

    @Override
    public int getItemCount() {
        return carList != null ? carList.size() : 0;
    }

    static class CarViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCar;
        TextView tvPrice, tvCarName, tvCarModel, tvYear, tvFuel, tvLocation, tvKm, tvOwner;
        Button btnViewDetails;

        CarViewHolder(View itemView) {
            super(itemView);
            ivCar = itemView.findViewById(R.id.ivCar);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvCarName = itemView.findViewById(R.id.tvCarName);
            tvCarModel = itemView.findViewById(R.id.tvCarModel);
            // Optional (only if added in XML)
            tvYear = itemView.findViewById(R.id.tvYear);
            tvFuel = itemView.findViewById(R.id.tvFuel);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvKm = itemView.findViewById(R.id.tvKm);
            tvOwner = itemView.findViewById(R.id.tvOwner);

            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
        }
    }

    private void openCarDetails(String name, String model, String price, String year,
                                String km, String fuel, String owner, String location) {
        Intent intent = new Intent(context, cardetails.class);
        intent.putExtra("carName", name);
        intent.putExtra("carModel", model);
        intent.putExtra("carPrice", price);
        intent.putExtra("carYear", year);
        intent.putExtra("carKm", km);
        intent.putExtra("carFuel", fuel);
        intent.putExtra("carOwner", owner);
        intent.putExtra("carLocation", location);

        // Use context to start Activity
        context.startActivity(intent);
    }
}