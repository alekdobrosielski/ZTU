package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.client.R;

import org.json.JSONException;

import Connection.APIConnection;
import Models.User;
import Presenters.RegisterActivityPresenter;

public class RegisterActivity extends Activity {
    private EditText loginET, passwordET, confirmPasswordET, emailET;
    private RegisterActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RegisterActivityPresenter(this);

        setContentView(R.layout.activity_register);
        connectViews();
    }

    private void connectViews()
    {
        loginET = findViewById(R.id.regLoginET);
        passwordET = findViewById(R.id.regPaswordET);
        confirmPasswordET = findViewById(R.id.regConfirmPaswordET);
        emailET = findViewById(R.id.regEmailET);
    }

    public void registerClick(View view) {
        if(passwordET.getText().toString().equals(confirmPasswordET.getText().toString()))
        {
            presenter.onRegisterClick(loginET.getText().toString(), passwordET.getText().toString(), emailET.getText().toString());
        }
        else
        {
            Toast.makeText(this, "Given password does not match!", Toast.LENGTH_LONG).show();
        }
    }
}