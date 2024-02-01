package com.example.myapplication.Model;

import java.sql.Date;
import com.example.myapplication.Extra.State;
import com.example.myapplication.Extra.DeliveryOption;

public class Request {
    private int requestId;
    private int borrowingPeriod;
    private DeliveryOption deliveryOption;
    private Date pickUpDate;
    private State requestState;
    private Date returnDate;
    private int actualBorrowingPeriod;
    private int carId;
    private int clientId;
    
    public Request(int requestId, int borrowingPeriod, DeliveryOption deliveryOption, Date pickUpDate,
            State requestState, Date returnDate, int actualBorrowingPeriod, int carId, int clientId) {
        this.requestId = requestId;
        this.borrowingPeriod = borrowingPeriod;
        this.deliveryOption = deliveryOption;
        this.pickUpDate = pickUpDate;
        this.requestState = requestState;
        this.returnDate = returnDate;
        this.actualBorrowingPeriod = actualBorrowingPeriod;
        this.carId = carId;
        this.clientId = clientId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getBorrowingPeriod() {
        return borrowingPeriod;
    }

    public void setBorrowingPeriod(int borrowingPeriod) {
        this.borrowingPeriod = borrowingPeriod;
    }

    public DeliveryOption getDeliveryOption() {
        return deliveryOption;
    }

    public void setDeliveryOption(DeliveryOption deliveryOption) {
        this.deliveryOption = deliveryOption;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public State getRequestState() {
        return requestState;
    }

    public void setRequestState(State requestState) {
        this.requestState = requestState;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getActualBorrowingPeriod() {
        return actualBorrowingPeriod;
    }

    public void setActualBorrowingPeriod(int actualBorrowingPeriod) {
        this.actualBorrowingPeriod = actualBorrowingPeriod;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Request [requestId=" + requestId + ", borrowingPeriod=" + borrowingPeriod + ", deliveryOption="
                + deliveryOption + ", pickUpDate=" + pickUpDate + ", requestState=" + requestState + ", returnDate="
                + returnDate + ", actualBorrowingPeriod=" + actualBorrowingPeriod + ", carId=" + carId + ", clientId="
                + clientId + "]";
    }
}
