package com.example.myapplication.Controller;

import java.sql.Date;
import com.example.myapplication.DAO.RequestDao;
import com.example.myapplication.DAO.RequestDaoImp;
import com.example.myapplication.Extra.DeliveryOption;
import com.example.myapplication.Model.Request;

public class RequestController {
    private final RequestDao requestsDao;

    public RequestController() {
        this.requestsDao = new RequestDaoImp();
    }

    public Request getRequestById(int requestId) {
        return requestsDao.getRequestById(requestId);
    }

    public void submitRequest(int borrowingPeriod, DeliveryOption deliveryOption,
                              Date pickUpDate, String carMatricula, String clientUsername) {
        requestsDao.insertRequest(borrowingPeriod, deliveryOption, pickUpDate, carMatricula, clientUsername);
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
