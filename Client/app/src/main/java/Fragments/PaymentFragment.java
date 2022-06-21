package Fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.client.R;

import java.util.ArrayList;

import Adapters.SelectUsersAdapter;
import Connection.APIConnection;
import DTOs.PaymentForAssignDTO;
import Models.Payment;
import Models.User;

public class PaymentFragment extends DialogFragment {

    TextView titleTV, descriptionTV, dateTV;
    Payment payment;
    ListView listView;
    SelectUsersAdapter adapter;
    Button splitButton;
    ArrayList<Integer> usersToAssign;

    public PaymentFragment(Payment payment)
    {
        this.payment = payment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        connectViews(view);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return view;
    }

    private void connectViews(View view)
    {
        titleTV = view.findViewById(R.id.titlePaymentTV);
        descriptionTV = view.findViewById(R.id.descriptionPaymentTV);
        dateTV = view.findViewById(R.id.datePaymentTV);
        listView = view.findViewById(R.id.list_view);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        splitButton = view.findViewById(R.id.splitButton);
        splitButton.setOnClickListener(this::onSplitClick);

        titleTV.setText(payment.getTitle());
        descriptionTV.setText(payment.getDescription());
        dateTV.setText(payment.getDate());


         usersToAssign = new ArrayList<Integer>();

        adapter = new SelectUsersAdapter(view.getContext(), payment.getDebtors(), new SelectUsersAdapter.OnSplitClickListener() {
            @Override
            public void onSplit(View view1) {
            }

            @Override
            public void onSelect(User user) {
                usersToAssign.add(user.getID());
            }
        });
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void onSplitClick(View v) {
        APIConnection.assignUsersToPayment(v.getContext(), new PaymentForAssignDTO(payment.getID(), usersToAssign));

    }



}