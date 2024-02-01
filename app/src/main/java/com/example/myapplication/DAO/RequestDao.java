package com.example.myapplication.DAO;

import java.sql.Date;
import com.example.myapplication.Extra.DeliveryOption;
import com.example.myapplication.Model.Request;

public interface RequestDao {
    Request getRequestById(int requestId);
    void insertRequest(int borrowingPeriod, DeliveryOption deliveryOption,
                       Date pickUpDate, int carId, int clientId);

    void updateRequest(int requestId, int borrowingPeriod, DeliveryOption deliveryOption,
                       Date pickUpDate);
    
    void insertReturnDate(int requestId, Date returnDate);
    void deleteRequest(int requestId);
}
