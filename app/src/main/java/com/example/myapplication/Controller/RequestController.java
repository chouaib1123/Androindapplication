package com.example.myapplication.Controller;

import java.sql.Date;
import com.example.myapplication.DAO.RequestDao;
import com.example.myapplication.Extra.DeliveryOption;
import com.example.myapplication.Model.Request;

public class RequestController {
    private RequestDao requestsDao;

    public RequestController(RequestDao requestsDao) {
        this.requestsDao = requestsDao;
    }

    public Request getRequestById(int requestId) {
        return requestsDao.getRequestById(requestId);
    }

    public void submitRequest(int borrowingPeriod, DeliveryOption deliveryOption,
                              Date pickUpDate, int carId, int clientId) {
        requestsDao.insertRequest(borrowingPeriod, deliveryOption, pickUpDate, carId, clientId);
    }

    public void updateRequestDetails(int requestId, int borrowingPeriod,
                                     DeliveryOption deliveryOption, Date pickUpDate) {
        requestsDao.updateRequest(requestId, borrowingPeriod, deliveryOption, pickUpDate);
    }

    public void submitReturnDate(int requestId, Date returnDate) {
        requestsDao.insertReturnDate(requestId, returnDate);
    }

    public void cancelRequest(int requestId) {
        requestsDao.deleteRequest(requestId);
    }
}
