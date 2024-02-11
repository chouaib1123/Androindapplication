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
import com.example.myapplication.Util.DatabaseUtil;
import com.google.firebase.database.DatabaseReference;

public class RequestDaoImp implements RequestDao {

    @Override
    public Request getRequestById(int requestId) {
        return null;
    }

    @Override
    public void insertRequest(String borrowingPeriod, String deliveryOption, String pickUpDate, String matricula, String clientUsername) {
        DatabaseReference identifier = DatabaseUtil.connect().child("Request").child(clientUsername).child(matricula);

        identifier.child("matricula").setValue(matricula);
        identifier.child("borrowingPeriod").setValue(borrowingPeriod);
        identifier.child("deliveryOption").setValue(deliveryOption);
        identifier.child("pickUpDate").setValue(pickUpDate);
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
