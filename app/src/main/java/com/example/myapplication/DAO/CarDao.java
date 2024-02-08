package com.example.myapplication.DAO;

import com.example.myapplication.Extra.FuelType;
import com.example.myapplication.Model.Car;

public interface CarDao {
    Car getCarByMatricula(String carMatricula);
    void insertCar(String color, String fuelType,
                   String isAutomatic, String matricula, String model,
                   String pricePerDay, String seatsNumber, String agencyUsername);

    void updateCar(String carMatricula, byte[] carImage, String color, double pricePerDay);
    void deleteCar(String carMatricula);
}
