package com.example.myapplication.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.myapplication.Extra.FuelType;
import com.example.myapplication.Model.Car;
import com.example.myapplication.Util.DatabaseUtil;
import com.google.firebase.database.DatabaseReference;

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

    private Car extractCarFromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }
    
}
