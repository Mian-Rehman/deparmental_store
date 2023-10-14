package com.rehman.newtrend.Utility;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.rehman.newtrend.R;

public class NetworkChangeListner extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Common.isConnectedInternet(context)) // internet is not connected
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View layout_dialog = LayoutInflater.from(context).inflate(R.layout.network_dialog, null);
            builder.setView(layout_dialog);

            //Show Dialog
            Button button = layout_dialog.findViewById(R.id.retry_button);

            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setCancelable(false);
            dialog.getWindow().setGravity(Gravity.CENTER);

            button.setOnClickListener(v -> {
                dialog.dismiss();
                onReceive(context, intent);
            });

        }
    }
}