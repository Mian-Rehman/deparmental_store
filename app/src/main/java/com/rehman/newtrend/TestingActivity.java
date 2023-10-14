package com.rehman.newtrend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rehman.newtrend.Payment.PaymentActivity;

public class TestingActivity extends AppCompatActivity {

    TextView price;
    Button btn_buy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        price = findViewById(R.id.price);
        btn_buy = findViewById(R.id.btn_buy);

        btn_buy.setOnClickListener(v -> {


            Intent i = new Intent(TestingActivity.this, PaymentActivity.class);
            //startActivity(new Intent(MainActivity.this, PaymentActivity.class));
            i.putExtra("price", price.getText().toString());

            startActivityForResult(i, 0);
            //startActivity(i);

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == 0 && resultCode == RESULT_OK) {
            // Get String data from Intent
            String ResponseCode = data.getStringExtra("pp_ResponseCode");
            System.out.println("DateFn: ResponseCode:" + ResponseCode);
            if(ResponseCode.equals("000")) {
                Toast.makeText(getApplicationContext(), "Payment Success", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Payment Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}