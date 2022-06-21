package Fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.client.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import AutoLogIn.AutoLogInPreferences;
import Models.Payment;

public class AddPaymentFragment extends DialogFragment {
private EditText titleET, descriptionET, valueET;
private List<String> data;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_add_payment, container, false);
        connectViews(view);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return view;
    }

    private void connectViews(View view)
    {
        titleET = view.findViewById(R.id.titlePaymentET);
        descriptionET = view.findViewById(R.id.descriptionPaymentET);
        valueET = view.findViewById(R.id.valuePaymentET);
    }

    public Payment getPayment()
    {
        Payment payment = new Payment();
        payment.setTitle(titleET.getText().toString());
        payment.setDescription(descriptionET.getText().toString());
        payment.setValue(Double.parseDouble(valueET.getText().toString()));

        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(date);
        payment.setDate(today);

        return payment;
    }

}