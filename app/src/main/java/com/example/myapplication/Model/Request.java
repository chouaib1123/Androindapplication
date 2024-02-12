package com.example.myapplication.Model;

import java.sql.Date;
import java.util.Objects;

import com.example.myapplication.Extra.State;
import com.example.myapplication.Extra.DeliveryOption;

public class Request {
    private String requestTitle;
    private int borrowingPeriod;
    private DeliveryOption deliveryOption;
    private Date pickUpDate;
    private State requestState;
    private Date returnDate;
    private int actualBorrowingPeriod;
    private String carMatricula;
    private String clientUsername;
    private String firstName ;
    private String lastName ;
    private String phoneNumber ;

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    private String carModel ;
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }



    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    public String getAgencyUsername() {
        return agencyUsername;
    }

    public void setAgencyUsername(String agencyUsername) {
        this.agencyUsername = agencyUsername;
    }

    private String agencyUsername;
    
    public Request(String requestTitle, int borrowingPeriod, DeliveryOption deliveryOption, Date pickUpDate,
                   State requestState, Date returnDate, int actualBorrowingPeriod, String carMatricula, String clientUsername, String agencyUsername) {
        this.requestTitle = requestTitle;
        this.borrowingPeriod = borrowingPeriod;
        this.deliveryOption = deliveryOption;
        this.pickUpDate = pickUpDate;
        this.requestState = requestState;
        this.returnDate = returnDate;
        this.actualBorrowingPeriod = actualBorrowingPeriod;
        this.carMatricula = carMatricula;
        this.clientUsername = clientUsername;
        this.agencyUsername = agencyUsername;
    }

    public Request(String requestTitle, int borrowingPeriod, DeliveryOption deliveryOption, Date pickUpDate,
                   State requestState, String carMatricula, String clientUsername) {
        this.requestTitle = requestTitle;
        this.borrowingPeriod = borrowingPeriod;
        this.deliveryOption = deliveryOption;
        this.pickUpDate = pickUpDate;
        this.requestState = requestState;
        this.carMatricula = carMatricula;
        this.clientUsername = clientUsername;
    }

    public Request(String requestTitle, String borrowingPeriod, String deliveryOption, String pickUpDate,
                   String requestState, String carMatricula, String clientUsername, String agencyUsername , String firstName , String lastName, String phoneNumber , String carModel) {
        this.requestTitle = requestTitle;
        this.borrowingPeriod = Integer.parseInt(borrowingPeriod);
        if(deliveryOption.toUpperCase().equals("IN PERSON")) deliveryOption = "IN_PERSON";
        this.deliveryOption = DeliveryOption.valueOf(deliveryOption);
        this.pickUpDate = Date.valueOf(pickUpDate);
        this.requestState = State.valueOf(requestState);
        this.carMatricula = carMatricula;
        this.clientUsername = clientUsername;
        this.agencyUsername = agencyUsername;
        this.firstName = firstName ;
        this.lastName = lastName ;
        this.phoneNumber = phoneNumber ;
        this.carModel = carModel ;
    }
    public Request(String requestTitle, String borrowingPeriod, String deliveryOption, String pickUpDate,
                   String requestState, String carMatricula, String clientUsername, String agencyUsername,String carModel ) {
        this.requestTitle = requestTitle;
        this.borrowingPeriod = Integer.parseInt(borrowingPeriod);
        if(deliveryOption.toUpperCase().equals("IN PERSON")) deliveryOption = "IN_PERSON";
        this.deliveryOption = DeliveryOption.valueOf(deliveryOption);
        this.pickUpDate = Date.valueOf(pickUpDate);
        this.requestState = State.valueOf(requestState);
        this.carMatricula = carMatricula;
        this.clientUsername = clientUsername;
        this.agencyUsername = agencyUsername;
        this.carModel = carModel ;

    }
    public Request() {

    }

    public String getRequestTitle() {
        return requestTitle;
    }

    public void setRequestTitle(String requestTitle) {
        this.requestTitle = requestTitle;
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

    public String getCarMatricula() {
        return carMatricula;
    }

    public void setCarMatricula(String carMatricula) {
        this.carMatricula = carMatricula;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    @Override
    public String toString() {
        return "Request [requestId=" + requestTitle + ", borrowingPeriod=" + borrowingPeriod + ", deliveryOption="
                + deliveryOption + ", pickUpDate=" + pickUpDate + ", requestState=" + requestState + ", returnDate="
                + returnDate + ", actualBorrowingPeriod=" + actualBorrowingPeriod + ", carId=" + carMatricula + ", clientId="
                + clientUsername + "]";
    }
}
