package com.example.myapplication.Controller;

import com.example.myapplication.DAO.CarDao;
import com.example.myapplication.DAO.CarDaoImp;
import com.example.myapplication.Extra.FuelType;
import com.example.myapplication.Model.Car;

import java.util.List;

public class CarController {
    private final CarDao carDao;

    public CarController() {
        this.carDao = new CarDaoImp();
    }

    public void retrievePostedCars(String agencyUsername, CarDaoImp.CarRetrievalListener listener) {
        carDao.retrievePostedCars(agencyUsername, listener);
    }

    public void retrieveAllCars(CarDaoImp.CarRetrievalListener listener) {
        carDao.retrieveAllCars(listener);
    }

    public Car getCarByMatricula(String carMatricula) {
        return carDao.getCarByMatricula(carMatricula);
    }

    public void addCar(String color, String fuelType, String isAutomatic, String matricula, String model,
                       String pricePerDay, String seatsNumber, String agencyUsername) {
        carDao.insertCar(color, fuelType, isAutomatic, matricula, model,
                          pricePerDay, seatsNumber, agencyUsername);
    }

    public void updateCarDetails(String carMatricula,  String color, String pricePerDay,String agencyUsername) {
        carDao.updateCar(carMatricula, color, pricePerDay,agencyUsername);
    }

    public void removeCar(String carMatricula) {
        carDao.deleteCar(carMatricula);
    }
}
