package com.rehman.newtrend.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rehman.newtrend.Model.UserUpdateModel;
import com.rehman.newtrend.R;

public class EditProfileInfoActivity extends AppCompatActivity {

    ImageView back_image;
    EditText ed_name,ed_email;
    RadioGroup radioGroup;
    Button update_button;
    RadioButton radioButton;
    String gender;
    FirebaseAuth mAuth;
    String userUID;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_info);

        initViews();
        progressDialog = ProgressDialog.show(EditProfileInfoActivity.this, "Please wait", "Processing", true);
        mAuth = FirebaseAuth.getInstance();
        userUID = mAuth.getCurrentUser().getUid();
        update_button.setEnabled(false);

        checkNameEmailFirebase();


        back_image.setOnClickListener(v -> {
            onBackPressed();
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = findViewById(checkedId);
                switch (radioButton.getId())
                {
                    case R.id.male:
                        gender = "male";
                        update_button.setEnabled(true);
                        break;

                    case R.id.female:
                        gender = "female";
                        update_button.setEnabled(true);
                        break;

                }
            }
        });

        update_button.setOnClickListener(v -> {
            progressDialog = ProgressDialog.show(EditProfileInfoActivity.this, "Please wait", "Processing", true);
            String fullName = ed_name.getText().toString();
            String email = ed_email.getText().toString();

            if (isValid(fullName,email))
            {

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("CustomerInfoDetails")
                        .child(userUID);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserUpdateModel model = new UserUpdateModel(fullName,email,gender);
                        reference.child("userProfileUpdate").setValue(model);
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

        });
    }
    private boolean isValid(String fullName, String email)
    {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (!email.matches(emailPattern) || email.isEmpty())
        {
            ed_email.setError("invalid Email");
            ed_email.requestFocus();
            progressDialog.dismiss();
            return false;
        }

        if (fullName.isEmpty())
        {
            ed_name.setError("Enter Name");
            ed_name.requestFocus();
            return  false;
        }
        return true;
    }

    private void checkNameEmailFirebase()
    {
        DatabaseReference reference  = FirebaseDatabase.getInstance().getReference("CustomerInfoDetails").child(userUID);
        reference.child("userProfileUpdate").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child("fullName").exists())
                {
                    String name = snapshot.child("fullName").getValue(String.class);
                    ed_name.setText(name);
                    progressDialog.dismiss();
                }

                if (snapshot.child("email").exists())
                {
                    String email = snapshot.child("email").getValue(String.class);
                    ed_email.setText(email);
                    progressDialog.dismiss();
                }

                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initViews()
    {
        back_image = findViewById(R.id.back_image);
        ed_name = findViewById(R.id.ed_name);
        ed_email = findViewById(R.id.ed_email);
        radioGroup = findViewById(R.id.radioGroup);
        update_button = findViewById(R.id.update_button);
    }
}