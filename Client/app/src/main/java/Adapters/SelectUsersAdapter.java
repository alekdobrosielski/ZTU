package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.client.R;

import java.util.ArrayList;

import Models.User;

public class SelectUsersAdapter extends ArrayAdapter<User> {

    public interface OnSplitClickListener {
        void onSplit(View view);
        void onSelect(User user);
    }
    OnSplitClickListener listener;

    public SelectUsersAdapter(Context context, ArrayList<User> users, OnSplitClickListener listener) {
        super(context, 0, users);
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_multiple_item, parent, false);
        }

        TextView nameTV = (TextView) convertView.findViewById(R.id.nameTV);
        TextView emailTV = (TextView) convertView.findViewById(R.id.emailTV);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSelect(user);
            }
        });

        checkBox.setChecked(user.isAssigned());
        if(user.isAssigned()) {
            checkBox.setClickable(false);
        }
        nameTV.setText(user.getName());
        emailTV.setText(user.getEmail());

        return convertView;
    }
}
