package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.client.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Adapters.AllPaymentsAdapter;
import AutoLogIn.AutoLogInPreferences;
import Connection.APIConnection;
import Fragments.PaymentFragment;
import Models.Payment;

public class PaymentsActivity extends BaseActivity {

    private RecyclerView recyclerView;


    AllPaymentsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        connectViews();
        int id = AutoLogInPreferences.getStaticUsr().getID();
        APIConnection.getPayments(id, this, "payments");
    }

    private void connectViews()
    {


        recyclerView = findViewById(R.id.recyclerView);
        adapter = new AllPaymentsAdapter(new AllPaymentsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Payment payment, int position) {
                PaymentFragment paymentFragment = new PaymentFragment(payment);
                //Toast.makeText(recyclerView.getContext(), payment.getDescription(), Toast.LENGTH_SHORT).show();
                paymentFragment.show(getSupportFragmentManager(), "payment");
            }

            @Override
            public void onLongItemClick(Payment payment, int position) {
                new AlertDialog.Builder(recyclerView.getContext())
                        .setTitle("Delete payment")
                        .setMessage("Do you really want to delete payment?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                APIConnection.deletePayment(payment.getID(), recyclerView.getContext());
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        APIConnection.getPayments(AutoLogInPreferences.getStaticUsr().getID(), recyclerView.getContext(), "payments");

                                    }
                                }, 400);
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);



    }

    public void setPayments(List<Payment> payments) {
        adapter.UpdateData(payments);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter( adapter);
        adapter.notifyDataSetChanged();
    }
}