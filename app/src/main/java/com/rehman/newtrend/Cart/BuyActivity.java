package com.rehman.newtrend.Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rehman.newtrend.MainActivity;
import com.rehman.newtrend.R;

import java.util.ArrayList;

public class BuyActivity extends AppCompatActivity {

    Button continue_button;
    ImageView back_image;
    EditText ed_firtName,ed_lastName,ed_email,ed_phoneNumber,ed_address_one,ed_address_two,ed_city,ed_state;

    ArrayList<String> headArray = new ArrayList<>();
    String firstName,lastName,email,phoneNumber,addressOne,addressTwo,city,state;
    TextView cart_text;
    String userUID,productTitle,productPrice,productDiscountPrice,productDescription,productCoverImage,productKey;
    String orderType = "pending";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        initviews();
        getIntentValue();

        continue_button.setOnClickListener(v -> {

            firstName = ed_firtName.getText().toString().trim();
            lastName  = ed_lastName.getText().toString().trim();
            email  = ed_email.getText().toString().trim();
            phoneNumber  = ed_phoneNumber.getText().toString().trim();
            addressOne  = ed_address_one.getText().toString().trim();
            addressTwo  = ed_address_two.getText().toString().trim();
            city  = ed_city.getText().toString().trim();
            state  = ed_state.getText().toString().trim();

            if (isVald(firstName,lastName,email,phoneNumber,addressOne,city,state))
            {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("orders")
                        .child(userUID);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String key = reference.push().getKey();
                        AddressModel model = new AddressModel(userUID,
                                productKey,productTitle,productPrice,productDiscountPrice,productDescription
                        ,productCoverImage,firstName,lastName,email,phoneNumber,addressOne,
                                addressTwo,city,state,orderType,key);
                        assert key != null;
                        reference.child(key).setValue(model);

                        asveAdminORder(key);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });
    }

    private void asveAdminORder(String key)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ordersAdmin");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                AddressModel model = new AddressModel(userUID,
                        productKey,productTitle,productPrice,productDiscountPrice,productDescription
                        ,productCoverImage,firstName,lastName,email,phoneNumber,addressOne,
                        addressTwo,city,state,orderType,key);

                reference.child(key).setValue(model);
                Toast.makeText(BuyActivity.this, "Thanks for Order", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BuyActivity.this, MainActivity.class));
                finish();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private boolean isVald(String firstName, String lastName, String email, String phoneNumber,
                           String addressOne, String city, String state)
    {

        if (firstName.isEmpty())
        {
            ed_firtName.setError("Field required");
            ed_firtName.requestFocus();
            return false;
        }

        if (lastName.isEmpty())
        {
            ed_lastName.setError("Field required");
            ed_lastName.requestFocus();
            return false;
        }

        if (addressOne.isEmpty())
        {
            ed_address_one.setError("Field required");
            ed_address_one.requestFocus();
            return false;
        }

        if (city.isEmpty())
        {
            ed_city.setError("Field required");
            ed_city.requestFocus();
            return false;
        }

        if (state.isEmpty())
        {
            ed_state.setError("Field required");
            ed_state.requestFocus();
            return false;
        }

        if (state.isEmpty())
        {
            ed_state.setError("Field required");
            ed_state.requestFocus();
            return false;
        }

        if (phoneNumber.isEmpty() && phoneNumber.length() != 10)
        {
            ed_phoneNumber.setError("Field required");
            ed_phoneNumber.requestFocus();
            return false;
        }

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (!email.matches(emailPattern) || email.isEmpty())
        {
            ed_email.setError("invalid Email");
            ed_email.requestFocus();
            return false;
        }

        return true;
    }

    private void getIntentValue()
    {
        Intent intent = getIntent();
        userUID = intent.getStringExtra("userUID");
        productTitle = intent.getStringExtra("productTitle");
        productPrice = intent.getStringExtra("productPrice");
        productDiscountPrice = intent.getStringExtra("productDiscountPrice");
        productDescription = intent.getStringExtra("productDescription");
        productCoverImage = intent.getStringExtra("productCoverImage");
        productKey = intent.getStringExtra("productKey");

    }

    private void initviews()
    {
        continue_button = findViewById(R.id.continue_button);
        back_image = findViewById(R.id.back_image);
        ed_firtName = findViewById(R.id.ed_firtName);
        ed_lastName = findViewById(R.id.ed_lastName);
        ed_email = findViewById(R.id.ed_email);
        ed_phoneNumber = findViewById(R.id.ed_phoneNumber);
        ed_address_one = findViewById(R.id.ed_address_one);
        ed_address_two = findViewById(R.id.ed_address_two);
        ed_city = findViewById(R.id.ed_city);
        ed_state = findViewById(R.id.ed_state);
        cart_text = findViewById(R.id.cart_text);
    }
}