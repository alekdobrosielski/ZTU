package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.client.R;

import org.json.JSONException;

import java.lang.reflect.Method;

import AutoLogIn.AutoLogInPreferences;
import Connection.APIConnection;
import Models.User;
import Presenters.LoginActivityPresenter;

public class LoginActivity extends Activity {
    private EditText login, password;
    LoginActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginActivityPresenter(this);

        setContentView(R.layout.activity_login);
        connectViews();
        presenter.checkForAutoLogIn();
    }

    private void connectViews() {
        login = findViewById(R.id.loginET);
        password = findViewById(R.id.paswordET);
    }

    public void loginClick(View view) {
        presenter.onLoginClick(login.getText().toString(), password.getText().toString());
    }

    public void createAccountClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}