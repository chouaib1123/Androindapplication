package com.example.myapplication.DAO;

import com.example.myapplication.Extra.FuelType;
import com.example.myapplication.Model.Car;

public interface CarDao {
    Car getCarById(int carId);
    void insertCar(byte[] carImage, String color, FuelType fuelType,
                   boolean isAutomatic, String matricula, String model,
                   double pricePerDay, int seatsNumber, int agencyId);

    void updateCar(int carId, byte[] carImage, String color, double pricePerDay);
    void deleteCar(int carId);
}
