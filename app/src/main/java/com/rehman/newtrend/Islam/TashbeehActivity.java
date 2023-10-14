package com.rehman.newtrend.Islam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rehman.newtrend.Adapter.TasbeehAdapter;
import com.rehman.newtrend.Model.TasbeehModel;
import com.rehman.newtrend.R;

import java.util.ArrayList;

public class TashbeehActivity extends AppCompatActivity {


    CardView popular_card1,popular_card2;
    ImageView back_image;
    Button btn_Create;
    String userUID;
    FirebaseAuth mAuth;
    TasbeehAdapter adapter;
    LinearLayout myTasbeeh_Layout;
    ProgressDialog progressDialog;
    RecyclerView tasbeeh_recycleView;

    TextView pop_text1;

    ArrayList<TasbeehModel> mDataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tashbeeh);


        initViews();
        mAuth = FirebaseAuth.getInstance();
        userUID = mAuth.getCurrentUser().getUid();

        myTasbeeh_Layout.setVisibility(View.GONE);
        getTeasbeehData();



        back_image.setOnClickListener(v -> {
            onBackPressed();
        });

        popular_card1.setOnClickListener(v -> {
            Intent intent = new Intent(TashbeehActivity.this,CounterActivity.class);
            intent.putExtra("name",pop_text1.getText().toString());
            intent.putExtra("steps","100");
            startActivity(intent);
        });

        btn_Create.setOnClickListener(v -> {

            progressDialog = ProgressDialog.show(TashbeehActivity.this, "", "Processing", true);
        craeteDialog();


        });
    }

    private void getTeasbeehData()
    {

        mDataList = new ArrayList<>();
        adapter = new TasbeehAdapter(this,mDataList);
        tasbeeh_recycleView.setLayoutManager(new LinearLayoutManager(this));
        tasbeeh_recycleView.setAdapter(adapter);



        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("CustomerInfoDetails").child(userUID).child("tasbeehDetails");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if (snapshot.exists())
                {

                    TasbeehModel model = snapshot.getValue(TasbeehModel.class);
                    mDataList.add(model);
                    adapter.notifyDataSetChanged();
                    myTasbeeh_Layout.setVisibility(View.VISIBLE);


                }
                else
                {
                    Toast.makeText(TashbeehActivity.this, "not found", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void craeteDialog()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.create_tasbeh_layout);

        EditText ed_step = dialog.findViewById(R.id.ed_step);
        EditText ed_name = dialog.findViewById(R.id.ed_name);
        Button btn_save = dialog.findViewById(R.id.btn_save);

        btn_save.setOnClickListener(v -> {

            String tasbeehName = ed_name.getText().toString().trim();
            String tasbeehSteps = ed_step.getText().toString().trim();

            if (tasbeehName.isEmpty() && tasbeehSteps.isEmpty())
            {
                Toast.makeText(this, "please fill data", Toast.LENGTH_SHORT).show();
            }
            else
            {
                DatabaseReference reference = FirebaseDatabase.getInstance()
                        .getReference("CustomerInfoDetails").child(userUID);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists())
                        {
                            String key = reference.push().getKey();
                            TasbeehModel model = new TasbeehModel(key,tasbeehName,tasbeehSteps);
                            reference.child("tasbeehDetails").child(key).setValue(model);
                            progressDialog.dismiss();
                            dialog.dismiss();

                        }
                        else
                        {
                            Toast.makeText(TashbeehActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }



    private void initViews()
    {
        popular_card1 = findViewById(R.id.popular_card1);
        popular_card2 = findViewById(R.id.popular_card2);
        btn_Create = findViewById(R.id.btn_Create);
        back_image = findViewById(R.id.back_image);
        myTasbeeh_Layout = findViewById(R.id.myTasbeeh_Layout);
        tasbeeh_recycleView = findViewById(R.id.tasbeeh_recycleView);
        pop_text1 = findViewById(R.id.pop_text1);
    }
}