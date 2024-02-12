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

    public void submitRequest(String borrowingPeriod, String deliveryOption,
                              String pickUpDate, String carMatricula, String clientUsername, String requestState, String agencyUsername ,String firstName , String lastName, String phoneNumber , String carModel) {
        requestsDao.insertRequest(borrowingPeriod, deliveryOption, pickUpDate, carMatricula, clientUsername, requestState, agencyUsername , firstName , lastName ,  phoneNumber , carModel);
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

    public void retrieveClientRequests(String clientUsername, RequestDaoImp.RequestRetrievalListener listener) {
        requestsDao.retrieveClientRequests(clientUsername, listener);
    }

    public void retrieveAgencyRequests(String agencyUsername, RequestDaoImp.RequestRetrievalListener listener) {
        requestsDao.retrieveAgencyRequests(agencyUsername, listener);
    }
    public void updateRequestState(String requestTitle , String requestState , String agencyUsername , String clientUsernam){
        requestsDao.updateRequestState(requestTitle,requestState ,  agencyUsername ,  clientUsernam);
    }

}
