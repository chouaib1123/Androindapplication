package com.example.myapplication.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.myapplication.Extra.FuelType;
import com.example.myapplication.Model.Car;

public class CarDaoImp implements CarDao {

    @Override
    public Car getCarByMatricula(String carMatricula) {

        return null;
    }

    @Override
    public void insertCar(byte[] carImage, String color, FuelType fuelType, boolean isAutomatic, String matricula, String model, double pricePerDay, int seatsNumber, String agencyUsername) {

    }

    @Override
    public void updateCar(String carMatricula, byte[] carImage, String color, double pricePerDay) {

    }

    @Override
    public void deleteCar(String carMatricula) {

    }

    private Car extractCarFromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }
    
}
