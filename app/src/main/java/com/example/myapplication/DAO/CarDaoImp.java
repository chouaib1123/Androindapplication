package com.example.myapplication.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.Extra.FuelType;
import com.example.myapplication.Model.Car;
import com.example.myapplication.Util.DatabaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class CarDaoImp implements CarDao {

    @Override
    public Car getCarByMatricula(String carMatricula) {

        return null;
    }

    @Override
    public void insertCar(String color, String fuelType, String isAutomatic, String matricula, String model, String pricePerDay,
                          String seatsNumber, String agencyUsername) {
        DatabaseReference identifier = DatabaseUtil.connect().child("Agency").child(agencyUsername).child("Cars").child(matricula);

        identifier.child("matricula").setValue(matricula);
        identifier.child("color").setValue(color);
        identifier.child("fuelType").setValue(fuelType);
        identifier.child("isAutomatic").setValue(isAutomatic);
        identifier.child("model").setValue(model);
        identifier.child("pricePerDay").setValue(pricePerDay);
        identifier.child("seatsNumber").setValue(seatsNumber);
    }

    @Override
    public void updateCar(String carMatricula, byte[] carImage, String color, double pricePerDay) {

    }

    @Override
    public void deleteCar(String carMatricula) {

    }

    public interface CarRetrievalListener {
        void onCarsRetrieved(List<Car> cars);
        void onError(DatabaseError databaseError);
    }

    @Override
    public void retrievePostedCars(String agencyUsername, CarRetrievalListener listener) {
        DatabaseReference postedCarsRef = DatabaseUtil.connect().child("Agency").child(agencyUsername).child("Cars");
        postedCarsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Car> cars = new ArrayList<>();
                for (DataSnapshot carSnapshot : dataSnapshot.getChildren()) {
                    // Read car data
                    String model = carSnapshot.child("model").getValue(String.class);
                    String price = carSnapshot.child("pricePerDay").getValue(String.class);
                    String matricula = carSnapshot.child("matricula").getValue(String.class);
                    String color = carSnapshot.child("color").getValue(String.class);
                    String fuelType = carSnapshot.child("fuelType").getValue(String.class);
                    String isAutomatic = carSnapshot.child("isAutomatic").getValue(String.class);
                    String seatsNumber = carSnapshot.child("seatsNumber").getValue(String.class);

                    Car car = new Car(color, FuelType.valueOf(fuelType), Boolean.parseBoolean(isAutomatic), matricula, model, Double.parseDouble(price), Integer.parseInt(seatsNumber), agencyUsername);
                    cars.add(car);
                }
                listener.onCarsRetrieved(cars);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onError(databaseError);
            }
        });
    }
}
