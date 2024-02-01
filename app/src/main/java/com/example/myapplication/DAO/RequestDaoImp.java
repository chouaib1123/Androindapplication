package com.example.myapplication.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.myapplication.Extra.DeliveryOption;
import com.example.myapplication.Extra.State;
import com.example.myapplication.Model.Request;
import com.example.myapplication.Util.ConnectionPool;

public class RequestDaoImp implements RequestDao {

    @Override
    public Request getRequestById(int requestId) {
        String query = "call GetRequestById(?)";
        try (Connection connection = ConnectionPool.getConnectionFromPool();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, requestId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractRequestFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insertRequest(int borrowingPeriod, DeliveryOption deliveryOption, Date pickUpDate, int carId, int clientId) {
        String query = "call InsertRequest(?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionPool.getConnectionFromPool();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, borrowingPeriod);
            preparedStatement.setString(2, deliveryOption.toString());
            preparedStatement.setDate(3, pickUpDate);
            preparedStatement.setInt(4, carId);
            preparedStatement.setInt(5, clientId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println(affectedRows + " rows have been affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateRequest(int requestId, int borrowingPeriod, DeliveryOption deliveryOption, Date pickUpDate) {
        String query = "call updateRequest(?, ?, ?, ?)";
        try (Connection connection = ConnectionPool.getConnectionFromPool();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, requestId);
            preparedStatement.setInt(2, borrowingPeriod);
            preparedStatement.setString(3, deliveryOption.toString());
            preparedStatement.setDate(4, pickUpDate);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println(affectedRows + " rows have been affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertReturnDate(int requestId, Date returnDate) {
        String query = "call insertReturnDate(?, ?)";
        try (Connection connection = ConnectionPool.getConnectionFromPool();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                 preparedStatement.setInt(1, requestId);
                 preparedStatement.setDate(2, returnDate);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println(affectedRows + " rows have been affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRequest(int requestId) {
        String query = "call deleteRequest(?)";
        try (Connection connection = ConnectionPool.getConnectionFromPool();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, requestId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println(affectedRows + " rows have been affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Request extractRequestFromResultSet(ResultSet resultSet) throws SQLException {
        int requestId = resultSet.getInt("requests_id");
        int borrowingPeriod = resultSet.getInt("borrowing_period");
        DeliveryOption deliveryOption = DeliveryOption.valueOf(resultSet.getString("delivery_option"));
        Date pickUpDate = resultSet.getDate("pick_up_date");
        State requestState = State.valueOf(resultSet.getString("request_state"));
        Date returnDate = resultSet.getDate("return_date");
        int actualBorrowingPeriod = resultSet.getInt("actual_borrowing_period");
        int carId = resultSet.getInt("car_id");
        int clientId = resultSet.getInt("client_id");

        return new Request(requestId, borrowingPeriod, deliveryOption, pickUpDate, requestState, returnDate, actualBorrowingPeriod, carId, clientId);
    }
    
}
