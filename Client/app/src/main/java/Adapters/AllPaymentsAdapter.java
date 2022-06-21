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

public class AllPaymentsAdapter extends RecyclerView.Adapter {

    public interface OnItemClickListener {
        public void onItemClick(Payment payment, int position);

        public void onLongItemClick(Payment payment, int position);
    }

    OnItemClickListener listener;
    List<Payment> payments;

    public AllPaymentsAdapter(List<Payment> payments) {
        this.payments = payments;
    }
    public AllPaymentsAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void UpdateData(List<Payment> list)
    {
        this.payments = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_item, parent, false);

        return new PaymentAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PaymentAdapterViewHolder hld = (PaymentAdapterViewHolder) holder;
        hld.getTitle().setText(payments.get(position).getTitle());
        hld.getValue().setText(Double.toString(payments.get(position).getValue()));
        hld.bind(payments.get(position), listener);

    }

    @Override
    public int getItemCount() {
        return payments==null? 0: payments.size();
    }

}

class PaymentAdapterViewHolder extends RecyclerView.ViewHolder
{
    TextView title;
    TextView value;
    public PaymentAdapterViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.titleTV);
        value = itemView.findViewById(R.id.valueTV);
    }


    public void bind(final Payment item, final AllPaymentsAdapter.OnItemClickListener listener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item, getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongItemClick(item, getAdapterPosition());
                return false;
            }
        });
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getValue() {
        return value;
    }

    public void setValue(TextView value) {
        this.value = value;
    }


}




