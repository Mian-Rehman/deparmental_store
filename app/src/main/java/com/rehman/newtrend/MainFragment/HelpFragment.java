package com.rehman.newtrend.MainFragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rehman.newtrend.Help.AccountInfoActivity;
import com.rehman.newtrend.Help.PaymentInfoActivity;
import com.rehman.newtrend.Help.SaftyActivity;
import com.rehman.newtrend.Help.WarrantyActivity;
import com.rehman.newtrend.R;


public class HelpFragment extends Fragment {


    RelativeLayout account_layout,payment_layout,safety_layout,warrenty_layout;
    Button feedBack_button;
    String feedValue;

    public HelpFragment(String id) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        initField(view);

        feedBack_button.setOnClickListener(v -> {
            bottomDialog();
        });

        account_layout.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AccountInfoActivity.class));
        });


        payment_layout.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), PaymentInfoActivity.class));
        });

        safety_layout.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SaftyActivity.class));
        });
//
        warrenty_layout.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), WarrantyActivity.class));
        });

        return view;
    }

    private void bottomDialog()
    {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        TextView report_text = dialog.findViewById(R.id.report_text);
        TextView suggestions_text = dialog.findViewById(R.id.suggestions_text);
        EditText feedback_ed = dialog.findViewById(R.id.feedback_ed);
        Button sendFeedback_button = dialog.findViewById(R.id.sendFeedback_button);
        sendFeedback_button.setEnabled(false);

        report_text.setOnClickListener(v -> {

            report_text.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.selected_font_color));
            report_text.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));

            suggestions_text.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.border_layout));
            suggestions_text.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.transparent));
            suggestions_text.setTextColor(ContextCompat.getColor(getActivity(),R.color.black));

            feedValue = "reportError";
            sendFeedback_button.setEnabled(true);

        });

        suggestions_text.setOnClickListener(v -> {

            report_text.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.border_layout));
            report_text.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.transparent));
            report_text.setTextColor(ContextCompat.getColor(getActivity(),R.color.black));

            suggestions_text.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.selected_font_color));
            suggestions_text.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));

            feedValue = "suggestion";
            sendFeedback_button.setEnabled(true);

        });

        sendFeedback_button.setOnClickListener(v -> {
            String message = feedback_ed.getText().toString().trim();
            Toast.makeText(getActivity(), "Thanks For your response", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void initField(View view)
    {
        account_layout = view.findViewById(R.id.account_layout);
        payment_layout = view.findViewById(R.id.payment_layout);
        safety_layout = view.findViewById(R.id.mtSafety_layout);
        warrenty_layout = view.findViewById(R.id.warrenty_layout);
        feedBack_button = view.findViewById(R.id.feedBack_button);
    }
}