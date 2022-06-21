package AutoLogIn;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import Models.User;

public class AutoLogInPreferences {
    static final String PREF_USER_NAME= "username";
    static final String PREF_PASSWORD= "password";
    static final String PREF_TOKEN= "token";
    static final String PREF_ID= "ID";
    static final String PREF_EMAIL= "email";
    static User user;

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static void setUser(Context ctx, User usr)
    {
        user = usr;
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, usr.getName());
        editor.putString(PREF_PASSWORD, usr.getPassword());
        editor.putString(PREF_TOKEN, usr.getToken());
        editor.putInt(PREF_ID, usr.getID());
        editor.putString(PREF_EMAIL, usr.getEmail());
        editor.apply();
    }

    public static User getUser(Context ctx)
    {
        if(getSharedPreferences(ctx).getString(PREF_USER_NAME, "").equals(""))
            return null;

        User user = new User();
        user.setName(getSharedPreferences(ctx).getString(PREF_USER_NAME, ""));
        user.setPassword(getSharedPreferences(ctx).getString(PREF_PASSWORD, ""));
        user.setToken(getSharedPreferences(ctx).getString(PREF_TOKEN, ""));
        user.setEmail(getSharedPreferences(ctx).getString(PREF_EMAIL, ""));
        user.setID(getSharedPreferences(ctx).getInt(PREF_ID, -1));
        return user;
    }

    public static void clearPrefs(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.apply();
    }

    public static User getStaticUsr()
    {
        return user;
    }
}
