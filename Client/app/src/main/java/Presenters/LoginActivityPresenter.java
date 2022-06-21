package Presenters;

import android.content.Intent;

import org.json.JSONException;

import Activities.LoginActivity;
import Activities.MainWindowActivity;
import AutoLogIn.AutoLogInPreferences;
import Connection.APIConnection;
import Models.User;

public class LoginActivityPresenter {
    LoginActivity activity;

    public LoginActivityPresenter(LoginActivity activity) {
        this.activity = activity;
    }

    public void checkForAutoLogIn()
    {
        User u = AutoLogInPreferences.getUser(activity);
        if(u != null)
        {
            AutoLogInPreferences.setUser(activity, u);
            Intent intent = new Intent(activity, MainWindowActivity.class);
            activity.startActivity(intent);
        }
    }

    public void onLoginClick(String login, String password) {
        try {
            APIConnection.loginToAPI(new User(login, password), activity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
