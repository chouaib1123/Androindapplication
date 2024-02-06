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
        return null;
    }

    @Override
    public void insertRequest(int borrowingPeriod, DeliveryOption deliveryOption, Date pickUpDate, String carMatricula, String clientUsername) {

    }

    @Override
    public void updateRequest(int requestId, int borrowingPeriod, DeliveryOption deliveryOption, Date pickUpDate) {

    }

    @Override
    public void insertReturnDate(int requestId, Date returnDate) {

    }

    @Override
    public void deleteRequest(int requestId) {

    }

    private Request extractRequestFromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }
    
}
