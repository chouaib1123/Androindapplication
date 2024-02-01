package com.example.myapplication.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.myapplication.Extra.FuelType;
import com.example.myapplication.Model.Car;
import com.example.myapplication.Util.ConnectionPool;

public class CarDaoImp implements CarDao {

    @Override
    public Car getCarById(int carId) {
        String query = "call GetCarById(?)";
        try (Connection connection = ConnectionPool.getConnectionFromPool();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, carId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractCarFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insertCar(byte[] carImage, String color, FuelType fuelType,
                         boolean isAutomatic, String matricula, String model,
                         double pricePerDay, int seatsNumber, int agencyId) {
        String query = "call InsertCar(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionPool.getConnectionFromPool();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBytes(1, carImage);
            preparedStatement.setString(2, color);
            preparedStatement.setString(3, fuelType.toString());
            preparedStatement.setBoolean(4, isAutomatic);
            preparedStatement.setString(5, matricula);
            preparedStatement.setString(6, model);
            preparedStatement.setDouble(7, pricePerDay);
            preparedStatement.setInt(8, seatsNumber);
            preparedStatement.setInt(9, agencyId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println(affectedRows + " rows have been affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCar(int carId, byte[] carImage, String color, double pricePerDay) {
        String query = "call updateCar(?, ?, ?, ?)";
        try (Connection connection = ConnectionPool.getConnectionFromPool();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBytes(1, carImage);
            preparedStatement.setString(2, color);
            preparedStatement.setDouble(3, pricePerDay);
            preparedStatement.setInt(4, carId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println(affectedRows + " rows have been affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCar(int carId) {
        String query = "call deleteCar(?)";
        try (Connection connection = ConnectionPool.getConnectionFromPool();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, carId);
            
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println(affectedRows + " rows have been affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Car extractCarFromResultSet(ResultSet resultSet) throws SQLException {
        int carId = resultSet.getInt("car_id");
        byte[] carImage = resultSet.getBytes("car_image");
        String color = resultSet.getString("color");
        FuelType fuelType = FuelType.valueOf(resultSet.getString("fuel_type"));
        boolean isAutomatic = resultSet.getBoolean("isAutomatic");
        String matricula = resultSet.getString("matricula");
        String model = resultSet.getString("model");
        double pricePerDay = resultSet.getDouble("price_per_day");
        int seatsNumber = resultSet.getInt("seats_number");
        int agencyId = resultSet.getInt("agency_id");

        return new Car(carId, carImage, color, fuelType,
                isAutomatic, matricula, model, pricePerDay, seatsNumber, agencyId);
    }
    
}
