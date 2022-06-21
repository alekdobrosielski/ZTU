package Connection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Activities.LoginActivity;
import Activities.MainWindowActivity;
import Activities.PaymentsActivity;
import Activities.UsersActivity;
import AutoLogIn.AutoLogInPreferences;
import DTOs.PaymentForAssignDTO;
import Models.Payment;
import Models.User;

public class APIConnection {
    private static String URL = "http://10.0.2.2:5000/api/";

    public static void loginToAPI(User user, Context context) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        Gson gson = new Gson();
        String a = gson.toJson(user);
        JSONObject jsonObject = new JSONObject(a);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL + "user/login", jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Logged in!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, MainWindowActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                        User u = null;
                        String us = response.toString();
                        u = new Gson().fromJson(us, User.class);
                        try {
                            u.setID(response.getInt("id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AutoLogInPreferences.setUser(context, u);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error.networkResponse.statusCode == 303)
                        {
                            Toast.makeText(context, "Given user doesn't exist!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    public static void registerToAPI(User user, Context context) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        Gson gson = new Gson();
        String a = gson.toJson(user);
        JSONObject jsonObject = new JSONObject(a);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, URL + "user/register", jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Successfully registered! Please log in.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    public static void AddPayment(Payment payment, Context context)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Gson gson = new Gson();
        String a = gson.toJson(payment);
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(a);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, URL + "payment/add", jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Successfully added payment!", Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + AutoLogInPreferences.getUser(context).getToken());
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
    public static void getPayments(int id, Context context, String sender)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL + "payment/get/" + id,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                TypeToken<List<Payment>> token = new TypeToken<List<Payment>>(){};
                List<Payment> payments = new Gson().fromJson(response, token.getType());

                if(sender.equals("payments")) {
                    PaymentsActivity paymentsActivity = (PaymentsActivity) context;
                    paymentsActivity.setPayments(payments);
                }
                else {
                    MainWindowActivity mainWindowActivity = (MainWindowActivity) context;
                    mainWindowActivity.paymentsSummary(payments);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + AutoLogInPreferences.getUser(context).getToken());
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }


    public static void deletePayment(int id, Context context)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, URL + "payment/delete/" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
                )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + AutoLogInPreferences.getUser(context).getToken());
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }

    public static void getAllUsers(Context context){
        RequestQueue requestQueue = Volley.newRequestQueue(context);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL + "user/getUsers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        TypeToken<List<User>> token = new TypeToken<List<User>>(){};
                        List<User> users = new Gson().fromJson(response, token.getType());
                        UsersActivity usersActivity = (UsersActivity) context;
                        usersActivity.onResponseRecieve(users);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + AutoLogInPreferences.getUser(context).getToken());
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }


    public static void getUser(int id, Context context, VolleyCallback callback){
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL + "user/getUser/" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.OnSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + AutoLogInPreferences.getUser(context).getToken());
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }


    public static void updateUser(User user, Context context)
    {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Gson gson = new Gson();
        String a = gson.toJson(user);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(a);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.PUT,URL + "user/update", jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Successfully updated!", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "There was an error while updating.", Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + AutoLogInPreferences.getUser(context).getToken());
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }

    public static void assignUsersToPayment(Context context, PaymentForAssignDTO dto) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Gson gson = new Gson();
        String a = gson.toJson(dto);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(a);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,URL + "payment/assign", jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Successfully added users!", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "There was an error while updating.", Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + AutoLogInPreferences.getUser(context).getToken());
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }
}

