package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.client.R;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import AutoLogIn.AutoLogInPreferences;
import Connection.APIConnection;
import Fragments.AddPaymentFragment;
import Models.Payment;
import Models.User;

public class MainWindowActivity extends BaseActivity {
    private NavigationView navView;
    private DrawerLayout drawerLayout;
    private AddPaymentFragment addPaymentFragment;
    private TextView nameTV, emailTV, nameHelloTV, valHelloTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);
        getSupportActionBar().hide();
        connectViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        APIConnection.getPayments(AutoLogInPreferences.getStaticUsr().getID(), this, "mainWindow");
    }

    private void connectViews()
    {
        drawerLayout = findViewById(R.id.drawerLayout);
        navView = drawerLayout.findViewById(R.id.nav_view);
        nameTV = navView.getHeaderView(0).findViewById(R.id.nav_nameTV);
        emailTV = navView.getHeaderView(0).findViewById(R.id.nav_emailTV);
        nameHelloTV = findViewById(R.id.helloUserTV);
        valHelloTV = findViewById(R.id.helloValTV);
        User loggedUser = AutoLogInPreferences.getStaticUsr();

        nameTV.setText(loggedUser.getName());
        emailTV.setText(loggedUser.getEmail());

        APIConnection.getPayments(loggedUser.getID(), this, "mainWindow");
        nameHelloTV.setText(loggedUser.getName());

    }

    public void menuClick(View view) {
        drawerLayout.openDrawer(GravityCompat.START);
        drawerLayout.invalidate();
    }

    public void addPaymentClick(MenuItem item) {
        addPaymentFragment = new AddPaymentFragment();
        addPaymentFragment.show(getSupportFragmentManager(), "addPayment");
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void addPaymentBtn(View view) {
        Payment payment = addPaymentFragment.getPayment();
        int id = AutoLogInPreferences.getUser(this).getID();
        payment.setOwnerID(id);
        APIConnection.AddPayment(payment, this);
        getSupportFragmentManager().beginTransaction().remove(addPaymentFragment).commit();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                APIConnection.getPayments(AutoLogInPreferences.getStaticUsr().getID(), valHelloTV.getContext(), "mainWindow");

            }
        }, 400);
    }

    public void logOutBtn(View view) {
        AutoLogInPreferences.clearPrefs(this);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        Reset();
        finish();
    }

    public void getPayments(MenuItem item) {
        Intent intent = new Intent(this, PaymentsActivity.class);
        startActivity(intent);
        //finish();
    }

    public void paymentsSummary(List<Payment> payments){
        double val = 0;
        for(Payment p : payments)
        {
            val+= p.getValue();
        }
        String value = Double.toString(val);
        valHelloTV.setText(value);

    }


    public void getAllUsers(MenuItem item) {
        Intent intent = new Intent(this, UsersActivity.class);
        startActivity(intent);
    }

    public void profileClick(MenuItem item) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void settingsClick(MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}