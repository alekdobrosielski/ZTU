package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.client.R;
import com.google.gson.Gson;

import AutoLogIn.AutoLogInPreferences;
import Connection.APIConnection;
import Connection.VolleyCallback;
import Models.User;

public class ProfileActivity extends BaseActivity {
    private TextView nameTV, emailTV, bankTV, accNumberTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        connectViews();
    }

    private void connectViews()
    {
        nameTV = findViewById(R.id.nav_nameTV);
        emailTV = findViewById(R.id.nav_emailTV);
        bankTV = findViewById(R.id.nav_bankTV);
        accNumberTV = findViewById(R.id.nav_accountNumberTV);

        APIConnection.getUser(AutoLogInPreferences.getStaticUsr().getID(), this,
                new VolleyCallback() {
                    @Override
                    public void OnSuccess(String response) {
                        User user = new Gson().fromJson(response, User.class);
                        nameTV.setText("Name: " + user.getName());
                        emailTV.setText("E-mail: " + user.getEmail());
                        bankTV.setText("Bank: " + user.getBank());
                        accNumberTV.setText("Account Number: " + user.getAccountNumber());
                    }
                });

    }




}