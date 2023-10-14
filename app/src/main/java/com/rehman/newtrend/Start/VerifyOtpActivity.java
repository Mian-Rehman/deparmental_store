package com.rehman.newtrend.Start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.rehman.newtrend.MainActivity;
import com.rehman.newtrend.Model.User;
import com.rehman.newtrend.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class VerifyOtpActivity extends AppCompatActivity {

    ImageView back_image;
    LinearLayout otp_layout;
    EditText inputNUmber1,inputNUmber2,inputNUmber3,inputNUmber4,inputNUmber5,inputNUmber6;
    RelativeLayout resendLayout;
    TextView ReSendOTP,text_number,count_text;
    Button btn_verify;
    String phoneNumber;
    ProgressBar progressbar;
    FirebaseAuth mAuth;
    String getOtpCode;
    String verificationID;

    String userCity;
    String userState;
    String userCountry;
    String userLocation;
    String userLatitude;
    String userLongitude;
    String userUID;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    LocationManager locationManager;
    ProgressDialog progressDialog;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("CustomerAccounts");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        initView();
        phoneNumber = getIntent().getStringExtra("OPTNUMBER");
        text_number.setText("Send To: "+phoneNumber);
        inputOtpCode();

        initiateotp();
        coundownTimer();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationPremissionCheck();
        GooglePlayServiceCheck();
        GPSLocationServiceCheck();

        user = mAuth.getCurrentUser();
        if (user!=null)
        {
            userUID = firebaseAuth.getCurrentUser().getUid();
        }




        btn_verify.setOnClickListener(v -> {

            if (inputNUmber1.getText().toString().isEmpty()
                    || inputNUmber2.getText().toString().isEmpty()
                    || inputNUmber3.getText().toString().isEmpty()
                    || inputNUmber4.getText().toString().isEmpty()
                    || inputNUmber5.getText().toString().isEmpty()
                    || inputNUmber6.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Enter valid code", Toast.LENGTH_SHORT).show();
                return;
            }
            else
            {
                getOtpCode = inputNUmber1.getText().toString()+
                        inputNUmber2.getText().toString()+
                        inputNUmber3.getText().toString()+
                        inputNUmber4.getText().toString()+
                        inputNUmber5.getText().toString()+
                        inputNUmber6.getText().toString();
                PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationID,getOtpCode);
                signInWithPhoneAuthCredential(credential);
            }


        });
    }

    private void coundownTimer()
    {

        ReSendOTP.setVisibility(View.GONE);
        count_text.setVisibility(View.VISIBLE);

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                count_text.setText("" + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                count_text.setVisibility(View.GONE);
                count_text.setVisibility(View.VISIBLE);

            }

        }.start();
    }

    private void inputOtpCode()
    {
        inputNUmber1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty())
                {
                    inputNUmber2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputNUmber2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty())
                {
                    inputNUmber3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputNUmber3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty())
                {
                    inputNUmber4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputNUmber4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty())
                {
                    inputNUmber5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputNUmber5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty())
                {
                    inputNUmber6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initiateotp()
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60, TimeUnit.SECONDS, VerifyOtpActivity.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                {

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
                    {
                        verificationID = s;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential)
                    {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e)
                    {
                        Toast.makeText(getApplicationContext(),"Number Not Found!",Toast.LENGTH_LONG).show();
                    }
                }
        );


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {


        if (verificationID!=null)
        {
            progressbar.setVisibility(View.VISIBLE);
            btn_verify.setVisibility(View.INVISIBLE);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressbar.setVisibility(View.GONE);
                            btn_verify.setVisibility(View.VISIBLE);
                            if (task.isSuccessful())
                            {
                                saveUserProfileData();
                                startActivity(new Intent(VerifyOtpActivity.this, MainActivity.class));
//                               Toast.makeText(VerifyOtpActivity.this, "Verified", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
//                            ed_otp_code.setError("invalid Code");
//                            ed_otp_code.requestFocus();
                                Toast.makeText(getApplicationContext(),"SignIn Code Error",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(this, "Failed to Access", Toast.LENGTH_SHORT).show();
        }

    }

    private void saveUserProfileData()
    {

        String USERUID = mAuth.getCurrentUser().getUid();

//        saveDataToFireStore(USERUID,userCity,userState,userCountry,userLocation,userLatitude,userLongitude
//                ,phoneNumber);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String currentDateTime = simpleDateFormat.format(new Date());

        String phoneNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        Log.d("checkPhone", "saveUserProfileData: " + phoneNumber);
//        String userJoined = String.valueOf(Timestamp.now());

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("CustomerAccounts");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = new User(USERUID, userCity,userState,userCountry,userLocation,userLatitude,userLongitude
                        ,phoneNumber);
                SharedPreferences preferences = getSharedPreferences("FULL_DATA_MAIN",MODE_PRIVATE);
                SharedPreferences.Editor editor  = preferences.edit();
                editor.putString("userCity",userCity);
                editor.putString("userState",userState);
                editor.putString("userCountry",userCountry);
                editor.putString("userLocation",userLocation);
                editor.putString("phoneNumber",phoneNumber);
                editor.apply();
                myRef.child(USERUID).setValue(user);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void saveDataToFireStore(String useruid, String userCity, String userState, String userCountry, String userLocation, String userLatitude, String userLongitude, String phoneNumber) {

        User user = new User(useruid, userCity,userState,userCountry,userLocation,userLatitude,userLongitude
                ,phoneNumber);

        firebaseFirestore.collection("users")
                .document(userUID)
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(VerifyOtpActivity.this, "inserted", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(VerifyOtpActivity.this, "Something went wrong! Please try again!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }


    private void initView()
    {
        back_image = findViewById(R.id.back_image);
        otp_layout = findViewById(R.id.otp_layout);
        inputNUmber1 = findViewById(R.id.inputNUmber1);
        inputNUmber2 = findViewById(R.id.inputNUmber2);
        inputNUmber3 = findViewById(R.id.inputNUmber3);
        inputNUmber4 = findViewById(R.id.inputNUmber4);
        inputNUmber5 = findViewById(R.id.inputNUmber5);
        inputNUmber6 = findViewById(R.id.inputNUmber6);
        resendLayout = findViewById(R.id.resendLayout);
        ReSendOTP = findViewById(R.id.ReSendOTP);
        btn_verify = findViewById(R.id.btn_verify);
        text_number = findViewById(R.id.text_number);
        progressbar = findViewById(R.id.progressbar);
        count_text = findViewById(R.id.count_text);
    }

    private void LocationPremissionCheck() {

        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        String rationale = "Please provide location permission so that you can ...";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Location Permission")
                .setSettingsDialogTitle("Warning");
        Permissions.check(this/*context*/, permissions, null/*rationale*/, null/*options*/, new PermissionHandler() {
            @Override
            public void onGranted() {
                LocationRequest();

            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
                LocationPremissionCheck();
            }
        });
    }

    private void LocationRequest() {


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PermissionChecker.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PermissionChecker.PERMISSION_GRANTED) {


            fusedLocationProviderClient = new FusedLocationProviderClient(this);

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {


                    if (location != null) {

                        Double locationLatitude = location.getLatitude();
                        Double locationLongitude = location.getLongitude();

                        userLatitude = locationLatitude.toString();
                        userLongitude = locationLongitude.toString();

                        if (!userLatitude.equals("0.0") && !userLongitude.equals("0.0")) {

                            LocationRetreive(locationLatitude, locationLongitude);

                        } else {

                            Toast.makeText(VerifyOtpActivity.this,
                                    "Please turn on any GPS or location service and restart to use the app", Toast.LENGTH_SHORT).show();

                        }


                    } else {
                        Toast.makeText(VerifyOtpActivity.this,
                                "Please turn on any GPS or location service and restart to use the app", Toast.LENGTH_SHORT).show();
                    }

                }

            });


        } else {

            LocationPremissionCheck();

        }
    }

    public boolean GooglePlayServiceCheck() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(this, status, 2404).show();
            }
            return false;
        }
        return true;
    }


    private void GPSLocationServiceCheck() {

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Your GPS seems to be disabled, enable it to use this app?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
//                            Intent intent = new Intent(RegisterActivity.this, StartActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(intent);
                            finish();

                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void LocationRetreive(Double locationLatitude, Double locationLongitude) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(locationLatitude, locationLongitude, 1);
            if (addresses != null && addresses.size() > 0) {
                userCity = addresses.get(0).getLocality();
                userState = addresses.get(0).getAdminArea();
                userCountry = addresses.get(0).getCountryName();
                userLocation = addresses.get(0).getAddressLine(0);


                if (userCountry == null) {
                    if (userState != null) {
                        userCountry = userState;
                    } else if (userCity != null) {
                        userCountry = userCity;
                    } else {
                        userCountry = "null";
                    }
                }
                if (userCity == null) {
                    if (userState != null) {
                        userCity = userState;
                    } else {
                        userCity = userCountry;
                    }
                }
                if (userState == null) {
                    if (userCity != null) {
                        userState = userCity;
                    } else {
                        userState = userCountry;
                    }
                }
                if (userLocation == null) {
                    userLocation = "Null";
                }




            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(VerifyOtpActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}