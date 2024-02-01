package com.example.myapplication.Controller;

import com.example.myapplication.DAO.CarDao;
import com.example.myapplication.Extra.FuelType;
import com.example.myapplication.Model.Car;

public class CarController {
    private CarDao carDao;

    public CarController(CarDao carDao) {
        this.carDao = carDao;
    }

    public Car getCarById(int carId) {
        return carDao.getCarById(carId);
    }

    public void addCar(byte[] carImage, String color, FuelType fuelType,
                       boolean isAutomatic, String matricula, String model,
                       double pricePerDay, int seatsNumber, int agencyId) {
        carDao.insertCar(carImage, color, fuelType, isAutomatic, matricula, model,
                          pricePerDay, seatsNumber, agencyId);
    }

    public void updateCarDetails(int carId, byte[] carImage, String color, double pricePerDay) {
        carDao.updateCar(carId, carImage, color, pricePerDay);
    }

    public void removeCar(int carId) {
        carDao.deleteCar(carId);
    }
}
