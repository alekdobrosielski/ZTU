package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.client.R;

import java.util.List;

import Adapters.AllPaymentsAdapter;
import Adapters.UsersAdapter;
import AutoLogIn.AutoLogInPreferences;
import Connection.APIConnection;
import Models.User;

public class UsersActivity extends BaseActivity {
private RecyclerView recyclerView;
    UsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        APIConnection.getAllUsers(this);
        recyclerView = findViewById(R.id.recyclerViewUsers);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new UsersAdapter();
        recyclerView.setAdapter(adapter);
    }

    public void onResponseRecieve(List<User> users) {
        adapter.UpdateData(users);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter( adapter);
        adapter.notifyDataSetChanged();
    }


}