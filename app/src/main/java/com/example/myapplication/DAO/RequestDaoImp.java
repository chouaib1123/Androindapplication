package com.example.myapplication.DAO;

import androidx.annotation.NonNull;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.Extra.DeliveryOption;
import com.example.myapplication.Extra.FuelType;
import com.example.myapplication.Extra.State;
import com.example.myapplication.Model.Car;
import com.example.myapplication.Model.Request;
import com.example.myapplication.Util.ConnectionPool;
import com.example.myapplication.Util.DatabaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class RequestDaoImp implements RequestDao {

    @Override
    public Request getRequestById(int requestId) {
        return null;
    }

    @Override
    public void insertRequest(String borrowingPeriod, String deliveryOption, String pickUpDate,
                              String matricula, String clientUsername, String requestState, String agencyUsername) {
        String currentDate = new java.sql.Date(System.currentTimeMillis()).toString();
        DatabaseReference identifier = DatabaseUtil.connect().child("Agency")
                .child(agencyUsername).child("Requests").child(clientUsername + "_" + currentDate);

        identifier.child("title").setValue(clientUsername + "_" + currentDate);
        identifier.child("matricula").setValue(matricula);
        identifier.child("borrowingPeriod").setValue(borrowingPeriod);
        identifier.child("deliveryOption").setValue(deliveryOption);
        identifier.child("pickUpDate").setValue(pickUpDate);
        identifier.child("requestState").setValue(requestState);
        identifier.child("clientUsername").setValue(clientUsername);

        DatabaseReference identifier2 = DatabaseUtil.connect().child("Client")
                .child(clientUsername).child("Requests").child(agencyUsername + "_" + currentDate);

        identifier2.child("title").setValue(agencyUsername + "_" + currentDate);
        identifier2.child("matricula").setValue(matricula);
        identifier2.child("borrowingPeriod").setValue(borrowingPeriod);
        identifier2.child("deliveryOption").setValue(deliveryOption);
        identifier2.child("pickUpDate").setValue(pickUpDate);
        identifier2.child("requestState").setValue(requestState);
        identifier2.child("agencyUsername").setValue(agencyUsername);
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

    public interface RequestRetrievalListener {
        void onRequestRetrieved(List<Request> requests);
        void onErrorRequest(DatabaseError databaseError);
    }

    @Override
    public void retrieveClientRequests(String clientUsername, RequestRetrievalListener listener) {
        DatabaseReference identifier = DatabaseUtil.connect().child("Client").child(clientUsername)
                .child("Requests");
        identifier.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Request> requests = new ArrayList<>();
                for (DataSnapshot requestSnapshot : dataSnapshot.getChildren()) {
                    // Read car data
                    String title = requestSnapshot.child("title").getValue(String.class);
                    String borrowingPeriod = requestSnapshot.child("borrowingPeriod").getValue(String.class);
                    String deliveryOption = requestSnapshot.child("deliveryOption").getValue(String.class);
                    String matricula = requestSnapshot.child("matricula").getValue(String.class);
                    String pickUpDate = requestSnapshot.child("pickUpDate").getValue(String.class);
                    String requestState = requestSnapshot.child("requestState").getValue(String.class);
                    String agencyUsername = requestSnapshot.child("agencyUsername").getValue(String.class);

                    Request request = new Request(title, borrowingPeriod, deliveryOption, pickUpDate, requestState, matricula, clientUsername, agencyUsername);
                    requests.add(request);
                }
                listener.onRequestRetrieved(requests);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onErrorRequest(databaseError);
            }
        });
    }

    @Override
    public void retrieveAgencyRequests(String agencyUsername, RequestDaoImp.RequestRetrievalListener listener) {
        DatabaseReference identifier = DatabaseUtil.connect().child("Agency").child(agencyUsername)
                .child("Requests");
        identifier.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Request> requests = new ArrayList<>();
                for (DataSnapshot requestSnapshot : dataSnapshot.getChildren()) {
                    // Read car data
                    String title = requestSnapshot.child("title").getValue(String.class);
                    String borrowingPeriod = requestSnapshot.child("borrowingPeriod").getValue(String.class);
                    String deliveryOption = requestSnapshot.child("deliveryOption").getValue(String.class);
                    String matricula = requestSnapshot.child("matricula").getValue(String.class);
                    String pickUpDate = requestSnapshot.child("pickUpDate").getValue(String.class);
                    String requestState = requestSnapshot.child("requestState").getValue(String.class);
                    String clientUsername = requestSnapshot.child("clientUsername").getValue(String.class);

                    Request request = new Request(title, borrowingPeriod, deliveryOption, pickUpDate, requestState, matricula, clientUsername, agencyUsername);
                    requests.add(request);
                }
                listener.onRequestRetrieved(requests);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onErrorRequest(databaseError);
            }
        });
    }
}
