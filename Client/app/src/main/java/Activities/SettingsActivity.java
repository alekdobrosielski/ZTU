package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.client.R;

import AutoLogIn.AutoLogInPreferences;
import Connection.APIConnection;
import Models.User;

public class SettingsActivity extends BaseActivity {

    EditText loginET, emailET, passwordET, bankET, accNumberET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        connectViews();

    }

    private void connectViews()
    {
        loginET = findViewById(R.id.profileLogin);
        emailET = findViewById(R.id.profileEmail);
        passwordET = findViewById(R.id.profilePassword);
        bankET = findViewById(R.id.bankET);
        accNumberET = findViewById(R.id.accNumberET);
//        loginET.setText(AutoLogInPreferences.getStaticUsr().getName());
//        emailET.setText(AutoLogInPreferences.getStaticUsr().getEmail());
    }

    public void updateClick(View view) {
        User user = new User();
        user.setID(AutoLogInPreferences.getStaticUsr().getID());
        user.setName(loginET.getText().toString());
        user.setEmail(emailET.getText().toString());
        user.setPassword(passwordET.getText().toString());
        user.setPassword(passwordET.getText().toString());
        user.setBank(bankET.getText().toString());
        user.setAccountNumber(accNumberET.getText().toString());
        APIConnection.updateUser(user, this);
        finish();
    }

}