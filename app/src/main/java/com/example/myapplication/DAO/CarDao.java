package com.example.myapplication.DAO;

import com.example.myapplication.Extra.FuelType;
import com.example.myapplication.Model.Car;

import java.util.List;

public interface CarDao {
    Car getCarByMatricula(String carMatricula);
    void insertCar(String color, String fuelType,
                   String isAutomatic, String matricula, String model,
                   String pricePerDay, String seatsNumber, String agencyUsername);

    void updateCar(String carMatricula,  String color, String pricePerDay , String agencyUsername);
    void deleteCar(String carMatricula , String agencyUsername);
    void retrievePostedCars(String agencyUsername, CarDaoImp.CarRetrievalListener listener);
    void retrieveAllCars(CarDaoImp.CarRetrievalListener listener);
}
