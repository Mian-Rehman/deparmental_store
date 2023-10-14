package com.rehman.newtrend.Start;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.rehman.newtrend.MainActivity;
import com.rehman.newtrend.R;
import com.rehman.newtrend.Utility.NetworkChangeListner;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    Button loginSignUp_button,skip_button;
    CountryCodePicker ccp;
    EditText ed_number;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;

    Button btn_google;

    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private boolean mLocationPermissionGranted = false;
    public static final int ERROR_DIALOG_REQUEST_DOWNLOAD = 101;
    public static final int PERMISSION_REQUEST_ENABLE_GPS = 102;
    public static final int PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 103;

    NetworkChangeListner networkChangeListner  = new NetworkChangeListner();

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListner,filter);

        FirebaseUser user = mAuth.getCurrentUser();
        if (user!=null)
        {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        skip_button.setVisibility(View.GONE);
        createRequest();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeListner);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (progressDialog!=null)
        {
            progressDialog.dismiss();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        initView();

        ccp.registerCarrierNumberEditText(ed_number);


        skip_button.setOnClickListener(v -> {

            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        });

        loginSignUp_button.setOnClickListener(v -> {

            progressDialog = ProgressDialog.show(LoginActivity.this, "Please wait", "Processing", true);
            String number = ed_number.getText().toString().trim();

            if (isValid(number,progressDialog))
            {
                Intent intent = new Intent(LoginActivity.this,VerifyOtpActivity.class);
                intent.putExtra("OPTNUMBER",ccp.getFullNumberWithPlus().replace("",""));
                Log.d("CCP", "Number:"+ccp.getFullNumberWithPlus().replace("",""));
                startActivity(intent);
            }

        });


        btn_google.setOnClickListener(v -> {
            progressDialog = ProgressDialog.show(LoginActivity.this, "Please wait", "Processing", true);
            signInGoogle();
        });
    }

    private boolean isValid(String number, ProgressDialog progressDialog)
    {
        if (number.isEmpty() || number.length() > 10 || number.length() < 10)
        {
            ed_number.setError("Enter valid number");
            ed_number.requestFocus();
            progressDialog.dismiss();
            return false;
        }

        return true;
    }

    private void initView()
    {
        loginSignUp_button = findViewById(R.id.loginSignUp_button);
        skip_button = findViewById(R.id.skip_button);
        ccp=findViewById(R.id.ccp);
        ed_number = findViewById(R.id.ed_number);
        btn_google = findViewById(R.id.btn_google);
    }

    private void createRequest()
    {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signInGoogle()
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                // ...
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            switch (requestCode)
            {
                case PERMISSION_REQUEST_ENABLE_GPS:
                {
                    if(mLocationPermissionGranted)
                    {
                        //Do nothing
                    }
                    else
                    {
                        getPermission();
                    }

                }

            }

        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account)
    {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressDialog = ProgressDialog.show(LoginActivity.this, "Please wait", "Processing", true);
                            saveUserProfileData();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(LoginActivity.this, "Sorry auth failed.", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }

    private void getPermission()
    {
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
        )  == PackageManager.PERMISSION_GRANTED)
        {

            mLocationPermissionGranted = true;

        }
        else
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.SEND_SMS,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },PERMISSION_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void saveUserProfileData()
    {

        String USERUID = mAuth.getCurrentUser().getUid();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String currentDateTime = simpleDateFormat.format(new Date());

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("CustomerAccounts");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String,String> map = new HashMap<>();
                map.put("userUID",USERUID);
                myRef.child(USERUID).setValue(map);
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}