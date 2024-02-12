package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import com.example.myapplication.Controller.CarController;
import com.example.myapplication.Controller.RequestController;
import com.example.myapplication.DAO.CarDaoImp;
import com.example.myapplication.DAO.RequestDaoImp;
import com.example.myapplication.Extra.Functions;
import com.example.myapplication.Extra.State;
import com.example.myapplication.Model.Agency;
import com.example.myapplication.Model.Car;
import com.example.myapplication.Model.Request;
import com.example.myapplication.Util.DatabaseUtil;
import com.example.myapplication.View.UserViewImp;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class agencymain extends AppCompatActivity implements CarDaoImp.CarRetrievalListener, RequestDaoImp.RequestRetrievalListener {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://clientregister-c1856-default-rtdb.firebaseio.com/").getReference();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    Map<TextView, Pair<Uri, BitmapDrawable>> textViewImages = new HashMap<>();
    private TextView agencyNameTextView;
    TextView selectedTextView;
    private ScrollView scrollView;
    private Agency loggedInAgency;
    private String color, fuelType, isAutomatic, matricula, model, pricePerDay, seatsNumber, agencyUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agencymain);

        NavigationView nv = findViewById(R.id.navigation_view);
        ImageView menu = findViewById(R.id.menu_icon);
        View headerView = nv.getHeaderView(0);
        ImageView  backbtn = headerView.findViewById(R.id.backnav);
        agencyNameTextView = headerView.findViewById(R.id.username);
        scrollView = findViewById(R.id.scrollview);

        Intent intent = getIntent();
        if(intent != null) {
            loggedInAgency = (Agency) intent.getSerializableExtra("loggedInAgency");
            agencyUsername = loggedInAgency.getUsername();
        }

        switchToLayout(R.layout.postedcars);


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenu();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
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
                    switchToLayout(R.layout.agencyprofil);
                } else if (itemId == R.id.postedcards) {
                    switchToLayout(R.layout.postedcars);
                } else if (itemId == R.id.pendreq) {
                    switchToLayout(R.layout.agencypendingrequest);
                }
                else if (itemId == R.id.exit) {
                    logOut();
                }
                return false;
            }
        });
    }
    public void openMenu() {
        NavigationView nv = findViewById(R.id.navigation_view);
        nv.setVisibility(View.VISIBLE);
        // Apply fade animation
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        nv.startAnimation(fadeIn);
        setUpAgencyName();
    }

    public void setUpAgencyName(){
        if(loggedInAgency != null) {
            final String agencyName = loggedInAgency.getAgencyName();
            agencyNameTextView.setText(String.format("%s", agencyName));
        }
    }

    public void closeMenu() {
        final NavigationView nv = findViewById(R.id.navigation_view);
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

    private void getFieldsValues() {
        color = Functions.getEditTextValue((EditText)findViewById(R.id.edit_text_car_color));
        fuelType = Functions.getEditTextValue((EditText)findViewById(R.id.edit_text_fuel)).toUpperCase();
        isAutomatic = Functions.getCheckBoxValue((CheckBox) findViewById(R.id.checkbox_automatic));
        matricula = Functions.getEditTextValue((EditText)findViewById(R.id.edit_text_car_matricule));
        model = Functions.getEditTextValue((EditText)findViewById(R.id.edit_text_car_model));
        pricePerDay = Functions.getEditTextValue((EditText)findViewById(R.id.edit_text_car_price_per_day));
        seatsNumber = Functions.getEditTextValue((EditText)findViewById(R.id.edit_text_car_seats_number));
    }


    private boolean checkFields() {
        UserViewImp userViewImp = new UserViewImp();

        if(!Functions.isValidColor(color)) {
            userViewImp.OnError(agencymain.this, "Invalid Color!");
            return false;
        } else if (!Functions.isValidFuelType(fuelType)) {
            userViewImp.OnError(agencymain.this, "Invalid Fuel Type!");
            return false;
        } else if (!Functions.isValidMatricula(matricula)) {
            userViewImp.OnError(agencymain.this, "Invalid Matricula!");
            return false;
        } else if (!Functions.isValidModel(model)) {
            userViewImp.OnError(agencymain.this, "Invalid Car Model!");
            return false;
        } else if (!Functions.isValidPricePerDay(pricePerDay)) {
            userViewImp.OnError(agencymain.this, "Invalid Price!");
            return false;
        }  else if (!Functions.isValidSeatsNumber(seatsNumber)) {
            userViewImp.OnError(agencymain.this, "Invalid Number of Seats!");
            return false;
        }
        closeMenu();

        return true;
    }

    protected void switchToLayout(int layoutResId) {
        scrollView.removeAllViews();
        View newLayout = LayoutInflater.from(agencymain.this).inflate(layoutResId, scrollView, false);
        scrollView.addView(newLayout);

        if(layoutResId == R.layout.postedcars){
            Button loginButton = findViewById(R.id.ADDcars);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switchToLayout(R.layout.carinsert);
                }
            });

            CarController carController = new CarController();
            carController.retrievePostedCars(agencyUsername, this);
        }

        if (layoutResId == R.layout.carinsert) {
            TextView carPicture = findViewById(R.id.carpicture);
            setOnClickListenerForTextView(carPicture);
            Button addCar = findViewById(R.id.button_add_car);

            addCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFieldsValues();

                    if(checkFields()) {
                        DatabaseUtil.connect().child(agencyUsername).child("matricula").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.hasChild(matricula))
                                    Toast.makeText(agencymain.this, "The car already exists", Toast.LENGTH_SHORT).show();
                                else {
                                    CarController carController = new CarController();
                                    String directoryPath = "carImages/" + agencyUsername;
                                    String filename = matricula + ".png";

                                    carController.addCar(color, fuelType, isAutomatic, matricula, model, pricePerDay, seatsNumber, agencyUsername);
                                    DatabaseUtil.uploadImagesToFirebaseStorage(directoryPath, filename, textViewImages);

                                    Toast.makeText(agencymain.this, "Car is inserted successfully", Toast.LENGTH_SHORT).show();
                                    switchToLayout(R.layout.postedcars);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });
                    }
                }
            });
        }

        if(layoutResId == R.layout.agencyprofil) {
            setUpAgencyProfile();
        }

        if(layoutResId == R.layout.agencypendingrequest) {
            RequestController requestController = new RequestController();
            requestController.retrieveAgencyRequests(loggedInAgency.getUsername(), this);
        }
    }

    private void setUpAgencyProfile() {
        TextView agencyNameTV = (TextView) findViewById(R.id.agencyName);
        TextView phoneNumberTV = (TextView) findViewById(R.id.phoneNumber);
        TextView usernameTV = (TextView) findViewById(R.id.agencyUsername);
        TextView emailTV = (TextView) findViewById(R.id.email);
        TextView patentNumberTV = (TextView) findViewById(R.id.patentNumber);
        TextView managerFullNameTV = (TextView) findViewById(R.id.managerFullName);
        TextView addressTV = (TextView) findViewById(R.id.address);
        TextView cityTV = (TextView) findViewById(R.id.city);

        if(loggedInAgency != null) {
            final String email = loggedInAgency.getEmail();
            final String username = loggedInAgency.getUsername();
            final String agencyName = loggedInAgency.getAgencyName();
            final String phoneNumber = loggedInAgency.getUserPhoneNumber();
            final String patentNumber = String.valueOf(loggedInAgency.getPatentNumber());
            final String managerFullName = loggedInAgency.getManagerFullName();
            final String address = loggedInAgency.getAddress();
            final String city = loggedInAgency.getCity();

            agencyNameTV.setText(agencyName);
            emailTV.setText(email);
            phoneNumberTV.setText(phoneNumber);
            usernameTV.setText(username);
            patentNumberTV.setText(patentNumber);
            managerFullNameTV.setText(managerFullName);
            addressTV.setText(address);
            cityTV.setText(city);
        }
    }

    @SuppressLint("SetTextI18n")
    private void displayAgencyRequests(List<Request> requests) {
        for(Request request : requests) {
            LinearLayout myLayout = findViewById(R.id.agencyPendingRequests);
            // Create CardView for each car
            CardView cardView = new CardView(agencymain.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(40, 20, 40, 50);
            cardView.setLayoutParams(layoutParams);

            // Inflate the layout for the card
            View cardLayout = LayoutInflater.from(agencymain.this).inflate(R.layout.request_agency_card, null);

            // Find views in the cardLayout
            TextView titleTV = cardLayout.findViewById(R.id.requestNumber);
            TextView requestDetailsTV = cardLayout.findViewById(R.id.requestDetails);
            TextView deliveryOptionTV = cardLayout.findViewById(R.id.deliveryOptionId);
            ImageView acceptBtn = cardLayout.findViewById(R.id.image_view_modify);
            ImageView rejectBtn = cardLayout.findViewById(R.id.image_view_delete);

            String title = request.getRequestTitle();
            String pickUpDate = String.valueOf(request.getPickUpDate());
            String borrowingPeriod = String.valueOf(request.getBorrowingPeriod());
            String matricula = request.getCarMatricula();
            String deliveryOption = String.valueOf(request.getDeliveryOption());
            String requestState = String.valueOf(request.getRequestState());
            String firstName = request.getFirstName();
            String lastName = request.getLastName();
            String phoneNumber = request.getPhoneNumber();
            String clientUsername = request.getClientUsername();


            // Set data to views
            titleTV.setText(matricula);
            requestDetailsTV.setText("Pickup date: " + pickUpDate + " for: " + borrowingPeriod + " days");
            deliveryOptionTV.setText(deliveryOption + " | " +requestState + "\n" + firstName + " " + lastName + "\n" + phoneNumber   );
            acceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestController requestController = new RequestController();
                    requestController.updateRequestState(title, String.valueOf(State.APPROVED),agencyUsername,clientUsername);
                    switchToLayout(R.layout.agencypendingrequest);
                }
            });
            rejectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestController requestController = new RequestController();
                    requestController.updateRequestState(title, String.valueOf(State.DECLINED),agencyUsername,clientUsername);
                    switchToLayout(R.layout.agencypendingrequest);

            }
            });
            cardView.addView(cardLayout);
            // Add the CardView to the LinearLayout
            myLayout.addView(cardView);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ImagePicker.REQUEST_CODE && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
                    textViewImages.put(selectedTextView, new Pair<>(uri, drawable));
                    selectedTextView.setBackground(drawable);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void displayCarsOnUI(List<Car> carList) {
        LinearLayout myLayout = findViewById(R.id.mylayout);

        for (Car car : carList) {
            CardView cardView = new CardView(agencymain.this);

            // Set layout parameters for the CardView
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(40, 20, 40, 50);
            cardView.setId(R.id.edit_text_car_matricule);
            cardView.setLayoutParams(layoutParams);

            // Inflate the layout
            View cardLayout = LayoutInflater.from(agencymain.this).inflate(R.layout.card_layout, null);

            // Find views in the cardLayout
            ImageView imageView = cardLayout.findViewById(R.id.imageView);
            TextView textViewCarModel = cardLayout.findViewById(R.id.textViewCarModel);
            TextView textViewPrice = cardLayout.findViewById(R.id.textViewPrice);
            TextView textViewLocation = cardLayout.findViewById(R.id.textViewLocation);
            Button buttonModify = cardLayout.findViewById(R.id.buttonModify);
            Button buttonDelete = cardLayout.findViewById(R.id.buttonDelete);

            buttonModify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                       switchToLayout(R.layout.modify_posted_car);
                       modifycar(car);
                }
            });
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CarController carController = new CarController();
                    carController.removeCar(car.getMatricula(),agencyUsername);
                    switchToLayout(R.layout.postedcars);
                }
            });

            // Set data to views
            textViewCarModel.setText(car.getModel());
            textViewPrice.setText(String.valueOf(car.getPricePerDay()));
            textViewLocation.setText(loggedInAgency.getCity());

            // Retrieve and set the car image from Firebase Storage
            String imageName = car.getMatricula() + ".png";
            StorageReference imageRef = FirebaseStorage.getInstance().getReference().child("carImages/" + loggedInAgency.getUsername() + "/" + imageName);
            final long ONE_MEGABYTE = 1024 * 1024; // Adjust as needed
            // Handle any errors that occurred while fetching the image
            imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
                // Convert byte array to Bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                // Set the Bitmap to the ImageView
                imageView.setImageBitmap(bitmap);
            }).addOnFailureListener(Throwable::printStackTrace);

            // Add the cardLayout to the CardView
            cardView.addView(cardLayout);
            // Add the CardView to the LinearLayout
            myLayout.addView(cardView, myLayout.getChildCount() - 1);
        }
    }

    private void setOnClickListenerForTextView(final TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedTextView = textView;
                startImagePickerForTextView();
            }
        });
    }

    private void startImagePickerForTextView() {
        ImagePicker.with(agencymain.this)
                .crop()                     // Crop image (Optional)
                .compress(1024)             // Final image size will be less than 1 MB (Optional)
                .maxResultSize(1080, 1080)  // Final image resolution will be less than 1080 x 1080 (Optional)
                .start();
    }

    @Override
    public void onCarsRetrieved(List<Car> cars) {
        displayCarsOnUI(cars);
    }

    @Override
    public void onError(DatabaseError databaseError) {

    }

    private void logOut()
    {
        Intent intent = new Intent(agencymain.this, login.class);
        startActivity(intent);
        finish();
    }

    protected void modifycar( Car car){
        Button modifycar = findViewById(R.id.button_submit_car);
        String matricule = car.getMatricula();
        TextView modifiedpic = findViewById(R.id.carpicturemodify);
        setOnClickListenerForTextView(modifiedpic);
        EditText colorTV = findViewById(R.id.modify_color);
        EditText pricePerDayTV = findViewById(R.id.modify_priceperday);
        colorTV.setText(car.getColor());
        pricePerDayTV.setText(String.valueOf(car.getPricePerDay()));
        modifycar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = Functions.getEditTextValue((EditText)colorTV);
                pricePerDay = Functions.getEditTextValue((EditText)pricePerDayTV);

                    DatabaseUtil.connect().child(agencyUsername).child("matricula").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            CarController carController = new CarController();
                            String directoryPath = "carImages/" + agencyUsername;
                            String filename = matricule + ".png";
                            carController.updateCarDetails(matricule,color,pricePerDay,agencyUsername);
                            DatabaseUtil.uploadImagesToFirebaseStorage(directoryPath, filename, textViewImages);

                            Toast.makeText(agencymain.this, "Car is modified successfully", Toast.LENGTH_SHORT).show();
                            switchToLayout(R.layout.postedcars);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });

                }

        });
    }







    @Override
    public void onRequestRetrieved(List<Request> requests) {
        displayAgencyRequests(requests);
    }

    @Override
    public void onErrorRequest(DatabaseError databaseError) {

    }
}






