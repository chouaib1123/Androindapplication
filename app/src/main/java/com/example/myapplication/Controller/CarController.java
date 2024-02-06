package com.example.myapplication.Controller;

import com.example.myapplication.DAO.CarDao;
import com.example.myapplication.Extra.FuelType;
import com.example.myapplication.Model.Car;

public class CarController {
    private final CarDao carDao;

    public CarController(CarDao carDao) {
        this.carDao = carDao;
    }

    public Car getCarByMatricula(String carMatricula) {
        return carDao.getCarByMatricula(carMatricula);
    }

    public void addCar(byte[] carImage, String color, FuelType fuelType, boolean isAutomatic, String matricula, String model,
                       double pricePerDay, int seatsNumber, String agencyUsername) {
        carDao.insertCar(carImage, color, fuelType, isAutomatic, matricula, model,
                          pricePerDay, seatsNumber, agencyUsername);
    }

    public void updateCarDetails(String carMatricula, byte[] carImage, String color, double pricePerDay) {
        carDao.updateCar(carMatricula, carImage, color, pricePerDay);
    }

    public void removeCar(String carMatricula) {
        carDao.deleteCar(carMatricula);
    }
}
