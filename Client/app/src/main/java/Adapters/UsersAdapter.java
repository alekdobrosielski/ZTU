package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client.R;

import java.util.List;

import Models.Payment;
import Models.User;

public class UsersAdapter extends RecyclerView.Adapter {

    List<User> users;

    public UsersAdapter(List<User> users) {
        this.users = users;
    }
    public UsersAdapter() {
    }

    public void UpdateData(List<User> list)
    {
        this.users = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);

        return new UserAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UserAdapterViewHolder hld = (UserAdapterViewHolder) holder;
        hld.getName().setText(users.get(position).getName());
        hld.getEmail().setText(users.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return users==null? 0: users.size();
    }
}

class UserAdapterViewHolder extends RecyclerView.ViewHolder
{
    TextView name;
    TextView email;
    public UserAdapterViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.nameTV);
        email = itemView.findViewById(R.id.emailTV);
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getEmail() {
        return email;
    }

    public void setEmail(TextView email) {
        this.email = email;
    }
}

