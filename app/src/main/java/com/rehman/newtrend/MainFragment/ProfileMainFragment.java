package com.rehman.newtrend.MainFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rehman.newtrend.Islam.TashbeehActivity;
import com.rehman.newtrend.Profile.AboutActivity;
import com.rehman.newtrend.Profile.EditProfileInfoActivity;
import com.rehman.newtrend.Profile.SliderPaymentActivity;
import com.rehman.newtrend.R;
import com.rehman.newtrend.Start.LoginActivity;


public class ProfileMainFragment extends Fragment {

    ImageView edit_image;
    CardView about_card,paymentOption_card,counter_card;
    TextView guardiran_text,email_text,phone_text,text_logout;
    FirebaseAuth mAuth;
    String userUID;
    ProgressDialog progressDialog;

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            text_logout.setText("Login");
            about_card.setVisibility(View.INVISIBLE);
            paymentOption_card.setVisibility(View.INVISIBLE);
        } else {
            text_logout.setText("Logout");
            about_card.setVisibility(View.VISIBLE);
            paymentOption_card.setVisibility(View.VISIBLE);
        }

    }

    public ProfileMainFragment(String id) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_main, container, false);

        initViews(view);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = ProgressDialog.show(getActivity(), "Please wait", "Processing", true);

        FirebaseUser user = mAuth.getCurrentUser();
        if (user!=null)
        {
            checkNameEmailFirebase();
        }
        else
        {
            progressDialog.dismiss();
        }


        edit_image.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), EditProfileInfoActivity.class));
        });

        about_card.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AboutActivity.class));
        });

        paymentOption_card.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SliderPaymentActivity.class));
        });

        counter_card.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), TashbeehActivity.class));
        });

        text_logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });


        return view;
    }

    private void checkNameEmailFirebase()
    {
        mAuth = FirebaseAuth.getInstance();
        userUID = mAuth.getCurrentUser().getUid();
        String phone = mAuth.getCurrentUser().getPhoneNumber();
        phone_text.setText(phone);

        DatabaseReference reference  = FirebaseDatabase.getInstance().getReference("CustomerInfoDetails")
                .child(userUID);
        reference.child("userProfileUpdate").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("fullName").exists())
                {
                    String name = snapshot.child("fullName").getValue(String.class);
                    guardiran_text.setText(name);
                    progressDialog.dismiss();
                }
                else
                {
                    progressDialog.dismiss();
                }

                if (snapshot.child("email").exists())
                {
                    String email = snapshot.child("email").getValue(String.class);
                    email_text.setText(email);
                    progressDialog.dismiss();
                }
                else
                {
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initViews(View view)
    {
        edit_image = view.findViewById(R.id.edit_image);
        guardiran_text = view.findViewById(R.id.guardiran_text);
        email_text = view.findViewById(R.id.email_text);
        phone_text = view.findViewById(R.id.phone_text);
        about_card = view.findViewById(R.id.aboutMT_card);
        paymentOption_card = view.findViewById(R.id.paymentOption_card);
        text_logout = view.findViewById(R.id.text_logout);
        counter_card = view.findViewById(R.id.counter_card);
    }
}