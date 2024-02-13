package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.myapplication.Controller.CarController;
import com.example.myapplication.Controller.RequestController;
import com.example.myapplication.DAO.CarDaoImp;
import com.example.myapplication.DAO.RequestDaoImp;
import com.example.myapplication.Extra.Functions;
import com.example.myapplication.Extra.State;
import com.example.myapplication.Model.Car;
import com.example.myapplication.Model.Client;
import com.example.myapplication.Model.Request;
import com.example.myapplication.Model.User;
import com.example.myapplication.Util.DatabaseUtil;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class clientmain extends AppCompatActivity implements CarDaoImp.CarRetrievalListener, RequestDaoImp.RequestRetrievalListener {
    private ScrollView scrollView;
    private TextView clientNameTextView;
    private Client loggedInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientmain);

        NavigationView nv = findViewById(R.id.navigation_view2);
        ImageView menu = findViewById(R.id.menu_icon);
        View headerView = nv.getHeaderView(0);
        ImageView  backbtn = headerView.findViewById(R.id.backnav);
        scrollView = findViewById(R.id.scrollview);
        clientNameTextView = headerView.findViewById(R.id.username);

        switchToLayout(R.layout.cars);

        Intent intent = getIntent();
        if(intent != null) loggedInClient = (Client) intent.getSerializableExtra("loggedInClient");

        menu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                openMenu();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                closeMenu();
            }
        });

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.profil) {
                    switchToLayout(R.layout.clientprofil);
                } else if (itemId == R.id.cars) {
                    switchToLayout(R.layout.cars);
                } else if (itemId == R.id.pendreq) {
                    switchToLayout(R.layout.clientpendingrequest);
                } else if (itemId == R.id.exit) {
                    logOut();
                }

                closeMenu();
                return false;
            }
        });
    }
    public void openMenu() {
        NavigationView nv = findViewById(R.id.navigation_view2);
        nv.setVisibility(View.VISIBLE);
        // Apply fade animation
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        nv.startAnimation(fadeIn);
        setLoggedInClientName();
    }

    public void setLoggedInClientName() {
        if(loggedInClient != null) {
            final String clientFirstName = loggedInClient.getFirstName();
            final String clientLastName = loggedInClient.getLastName();
            clientNameTextView.setText(String.format("%s %s", clientFirstName, clientLastName));
        }
    }

    public void closeMenu() {
        final NavigationView nv = findViewById(R.id.navigation_view2);
        // Apply fade animation
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                nv.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        nv.startAnimation(fadeOut);
    }

    protected void switchToLayout(int layoutResId) {
        scrollView.removeAllViews();
        View newLayout = LayoutInflater.from(clientmain.this).inflate(layoutResId, scrollView, false);
        scrollView.addView(newLayout);

        if(layoutResId == R.layout.clientprofil) {
            setUpClientProfile();
        }

        if(layoutResId == R.layout.cars){
            CarController carController = new CarController();
            carController.retrieveAllCars(this);
        }

        if(layoutResId == R.layout.clientpendingrequest) {
            RequestController requestController = new RequestController();
            requestController.retrieveClientRequests(loggedInClient.getUsername(), this);
        }
    }

    private void setUpClientProfile() {
        TextView clientFullNameTV = (TextView) findViewById(R.id.clientFullName);
        TextView phoneNumberTV = (TextView) findViewById(R.id.phoneNumber);
        TextView usernameTV = (TextView) findViewById(R.id.clientUsername);
        TextView emailTV = (TextView) findViewById(R.id.email);
        TextView cinNumberTV = (TextView) findViewById(R.id.cinNumber);
        TextView drivingLicenseCategoryTV = (TextView) findViewById(R.id.drivingLicenseCategory);
        TextView drivingLicenseExpireDateTV = (TextView) findViewById(R.id.drivingLicenseExpireDate);
        TextView drivingLicenseObtainDateTV = (TextView) findViewById(R.id.drivingLicenseObtainDate);
        TextView addressTV = (TextView) findViewById(R.id.address);
        TextView cityTV = (TextView) findViewById(R.id.city);

        if(loggedInClient != null) {
            final String email = loggedInClient.getEmail();
            final String username = loggedInClient.getUsername();
            final String clientFullName = String.format("%s %s", loggedInClient.getFirstName(), loggedInClient.getLastName());
            final String phoneNumber = loggedInClient.getUserPhoneNumber();
            final String cinNumber = loggedInClient.getCin();
            final String drivingLicenseCategory = String.valueOf(loggedInClient.getLicenseCategory());
            final String drivingLicenseExpireDate = String.valueOf(loggedInClient.getLicenseExpireDate());
            final String drivingLicenseObtainDate = String.valueOf(loggedInClient.getLicenseObtainDate());
            final String address = loggedInClient.getAddress();
            final String city = loggedInClient.getCity();

            clientFullNameTV.setText(clientFullName);
            emailTV.setText(email);
            phoneNumberTV.setText(phoneNumber);
            usernameTV.setText(username);
            cinNumberTV.setText(cinNumber);
            addressTV.setText(address);
            cityTV.setText(city);
            drivingLicenseCategoryTV.setText(drivingLicenseCategory);
            drivingLicenseExpireDateTV.setText(drivingLicenseExpireDate);
            drivingLicenseObtainDateTV.setText(drivingLicenseObtainDate);
        }
    }

    private void displayCarsOnUI(List<Car> cars) {
        LinearLayout myLayout = findViewById(R.id.mylayoutcar);
        for (Car car : cars) {
            // Create CardView for each car
            CardView cardView = new CardView(clientmain.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(40, 20, 40, 50);
            cardView.setLayoutParams(layoutParams);

            // Inflate the layout for the card
            View cardLayout = LayoutInflater.from(clientmain.this).inflate(R.layout.card_client_layout, null);
            Button buttonInspect = cardLayout.findViewById(R.id.inspectButton);

            // Set an OnClickListener for the Inspect button
            buttonInspect.setOnClickListener(view -> {
                // Handle the click event, e.g., show detailed information
                showCarDetailsDialog(car);
            });

            // Find views in the cardLayout
            ImageView imageView = cardLayout.findViewById(R.id.imageView);
            TextView textViewCarModel = cardLayout.findViewById(R.id.textViewCarModel);
            TextView textViewPrice = cardLayout.findViewById(R.id.textViewPrice);
            TextView textViewLocation = cardLayout.findViewById(R.id.textViewLocation);

            // Set data to views
            textViewCarModel.setText("Car Model : " + car.getModel());
            textViewPrice.setText("Price Per day (MAD) : " + String.valueOf(car.getPricePerDay()));
            textViewLocation.setText("City : " + car.getAgencyCity());

            // Retrieve and set the car image from Firebase Storage
            String imageName = car.getMatricula() + ".png";
            StorageReference imageRef = FirebaseStorage.getInstance().getReference().child("carImages/" + car.getAgencyUsername() + "/" + imageName);

            final long ONE_MEGABYTE = 1024 * 1024; // Adjust as needed
            // Handle any errors that occurred while fetching the image
            imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
                // Convert byte array to Bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                // Set the Bitmap to the ImageView
                imageView.setImageBitmap(bitmap);
            }).addOnFailureListener(Throwable::printStackTrace);

            cardView.addView(cardLayout);
            // Add the CardView to the LinearLayout
            myLayout.addView(cardView);
        }
    }

    @SuppressLint("SetTextI18n")
    private void displayClientRequests(List<Request> requests) {
        for(Request request : requests) {
            LinearLayout myLayout = findViewById(R.id.myrequestlayout);
            // Create CardView for each car
            CardView cardView = new CardView(clientmain.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(40, 20, 40, 50);
            cardView.setLayoutParams(layoutParams);

            // Inflate the layout for the card
            View cardLayout = LayoutInflater.from(clientmain.this).inflate(R.layout.request_client_card_layout, null);

            // Find views in the cardLayout
            TextView titleTV = cardLayout.findViewById(R.id.requestNumber);
            TextView requestDetailsTV = cardLayout.findViewById(R.id.requestDetails);
            TextView deliveryOptionTV = cardLayout.findViewById(R.id.deliveryOptionId);

            String title = request.getRequestTitle();
            String pickUpDate = String.valueOf(request.getPickUpDate());
            String borrowingPeriod = String.valueOf(request.getBorrowingPeriod());
            String matricula = request.getCarMatricula();
            String deliveryOption = String.valueOf(request.getDeliveryOption());
            String model = request.getCarModel();
            String requestState = String.valueOf(request.getRequestState());

            // Set data to views
            titleTV.setText(matricula + " | " + model);
            requestDetailsTV.setText("Pickup date: " + pickUpDate + " for " + borrowingPeriod + " days");
            deliveryOptionTV.setText("Delivery Option :      " + deliveryOption + " \n" + "Request State :        "+ requestState);

            cardView.addView(cardLayout);
            // Add the CardView to the LinearLayout
            myLayout.addView(cardView);
        }
    }

    @Override
    public void onCarsRetrieved(List<Car> cars) {
        displayCarsOnUI(cars);
    }

    @Override
    public void onError(DatabaseError databaseError) {

    }

    @SuppressLint("SetTextI18n")
    private void showCarDetailsDialog(Car car) {
        switchToLayout(R.layout.inspectcar);

        // Find views in the dialogLayout
        ImageView imageViewCarDetails = findViewById(R.id.carpictureinspect);
        TextView textViewModel = findViewById(R.id.carModelInspect);
        TextView textViewAutomatic = findViewById(R.id.textViewisautomaticLabel);
        TextView textViewFuelType = findViewById(R.id.textViewFuelTypeLabel);
        TextView textViewCity = findViewById(R.id.textViewCityLabel);
        TextView textViewPricePerDay = findViewById(R.id.textViewPriceLabel);
        TextView textViewSeatsNumber = findViewById(R.id.textViewsitsnumberLabel);

        String model = car.getModel();
        String pricePerDay = String.valueOf(car.getPricePerDay());
        String isAutomatic = String.valueOf(car.getIsAutomatic());
        String fuelType = String.valueOf(car.getFuelType());
        String city = car.getAgencyCity();
        String seatsNumber = String.valueOf(car.getSeatsNumber());

        textViewModel.setText("Car Model: " + model);
        textViewPricePerDay.setText("Price Per Day: " + pricePerDay +" MAD" );
        textViewAutomatic.setText("Automatic Car: " + isAutomatic.toUpperCase());
        textViewFuelType.setText("Fuel Type: " + fuelType);
        textViewCity.setText("City: " + city);
        textViewSeatsNumber.setText("Number of Seats: " + seatsNumber);

        // Retrieve and set the car image from Firebase Storage
        String imageName = car.getMatricula() + ".png";
        StorageReference imageRef = FirebaseStorage.getInstance().getReference().child("carImages/" + car.getAgencyUsername() + "/" + imageName);
        final long ONE_MEGABYTE = 1024 * 1024; // Adjust as needed
        // Handle any errors that occurred while fetching the image
        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            // Convert byte array to Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            // Set the Bitmap to the ImageView in the dialogLayout
            imageViewCarDetails.setImageBitmap(bitmap);
        }).addOnFailureListener(Throwable::printStackTrace);

        Button requestClientButton = findViewById(R.id.rentCarButton);
        requestClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToLayout(R.layout.rent_process);
                rentCar(car);
            }
        });

    }

    private void logOut()
    {
        Intent intent = new Intent(clientmain.this, login.class);
        startActivity(intent);
        finish();
    }

    private void rentCar(Car car)
    {
        switchToLayout(R.layout.rent_process);
        TextView textViewPickUp = findViewById(R.id.PickupDate);
        TextView textViewReturnDate = findViewById(R.id.returndate);
        setDatePickerDialog(textViewReturnDate);
        setDatePickerDialog(textViewPickUp);

        Button doneBtn = findViewById(R.id.doneBtn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String borrowingPeriod = Functions.getEditTextValue((EditText) findViewById(R.id.borrowperiod));
                String pickupDate = Functions.getEditTextValue(textViewPickUp);
                String returnDate = Functions.getEditTextValue(textViewReturnDate);
                String matricula = car.getMatricula();
                String clientUsername = loggedInClient.getUsername();
                String requestState = String.valueOf(State.PENDING);
                String agencyUsername = car.getAgencyUsername();
                String firstName = loggedInClient.getFirstName();
                String lastName = loggedInClient.getLastName();
                String phoneNumber = loggedInClient.getUserPhoneNumber();
                String carModel = car.getModel();

                RadioGroup deliveryOptionRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
                int selectedDeliveryOptionId = deliveryOptionRadioGroup.getCheckedRadioButtonId();
                String deliveryOption = "IN_PERSON";
                if(selectedDeliveryOptionId != -1) deliveryOption = ((RadioButton)findViewById(selectedDeliveryOptionId)).getText().toString().toUpperCase(); // add constraints

                RequestController requestController = new RequestController();
                requestController.submitRequest(borrowingPeriod, deliveryOption, pickupDate, matricula, clientUsername, requestState, agencyUsername , firstName , lastName , phoneNumber,carModel);

                switchToLayout(R.layout.clientpendingrequest);
//                createCardLayout(new Request());
            }
        });
    }


    private void setDatePickerDialog(final TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                // Set the minimum date to be greater than today
                DatePickerDialog dialog = new DatePickerDialog(
                        clientmain.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month = month + 1;
                                Log.d(TAG, "onDateSet: yyyy-mm-dd: " + year + "-" + month + "-" + day);
                                String date = year + "-" + month + "-" + day;
                                textView.setText(date);
                            }
                        },
                        year, month, day);

                // Set minimum date to be greater than today
                dialog.getDatePicker(); // 1000 ms = 1 second

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }


    @Override
    public void onRequestRetrieved(List<Request> requests) {
        displayClientRequests(requests);
    }

    @Override
    public void onErrorRequest(DatabaseError databaseError) {

    }
}