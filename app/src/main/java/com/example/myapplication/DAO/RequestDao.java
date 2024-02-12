package com.example.myapplication.DAO;

import java.sql.Date;
import com.example.myapplication.Extra.DeliveryOption;
import com.example.myapplication.Model.Request;

public interface RequestDao {
    Request getRequestById(int requestId);
    void insertRequest(String borrowingPeriod, String deliveryOption,
                       String pickUpDate, String carMatricula, String clientUsername,
                       String requestState, String agencyUsername);

    void updateRequest(int requestId, int borrowingPeriod, DeliveryOption deliveryOption,
                       Date pickUpDate);
    
    void insertReturnDate(int requestId, Date returnDate);
    void deleteRequest(int requestId);

    void retrieveClientRequests(String clientUsername, RequestDaoImp.RequestRetrievalListener listener);
}
