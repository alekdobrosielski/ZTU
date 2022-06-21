package Presenters;

import android.widget.Toast;

import org.json.JSONException;

import Activities.RegisterActivity;
import Connection.APIConnection;
import Models.User;

public class RegisterActivityPresenter {
    RegisterActivity activity;

    public RegisterActivityPresenter(RegisterActivity activity) {
        this.activity = activity;
    }

    public void onRegisterClick(String login, String password, String email) {
        User user = new User(login, password);
        user.setEmail(email);
        try {
            APIConnection.registerToAPI(user, activity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
