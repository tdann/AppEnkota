package tdannandjuliet.assignment.enkotaApp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import tdannandjuliet.assignment.enkotaApp.R;
import tdannandjuliet.assignment.enkotaApp.helper.BuyerOrders;

public class BuyerOrders_Adapter extends RecyclerView.Adapter<BuyerOrders_Adapter.OrderViewHolder> {
    private Context mCtx;
    private List<BuyerOrders> spare_request_list;

    public BuyerOrders_Adapter(Context mCtx, List<BuyerOrders> tips_list) {
        this.mCtx = mCtx;
        this.spare_request_list = tips_list;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.buyer_order_list, null);
        return new OrderViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        BuyerOrders order = spare_request_list.get(position);


        Glide.with(mCtx)
                .load(order.getOimage())
                .centerCrop()
                .placeholder(R.drawable.matooke_logo)
                .into(holder.spare_image);

        holder.pdct_name.setText(order.getOname());
        holder.order_quantity.setText(order.getOqty());
        holder.order_amount.setText(order.getOamount());
        holder.order_address.setText(order.getOaddress());

        holder.order_status.setText(order.getOstatus());
        holder.order_date.setText(order.getO_orderDate());
        if (order.getO_deliverydate().equalsIgnoreCase("null")){
            holder.deliver_date.setText("Date not set!");
        }
        else {
            holder.deliver_date.setText(order.getO_deliverydate());
        }


    }

    @Override
    public int getItemCount() {
        return spare_request_list.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView pdct_name, order_quantity, order_amount, order_address, order_status, order_date,deliver_date;
        ImageView spare_image;
        Button cancel_order, confirm_order;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
          pdct_name = itemView.findViewById(R.id.spare_name);
            order_quantity = itemView.findViewById(R.id.quantity_order);
            order_amount = itemView.findViewById(R.id.amount_to_pay);
            order_address = itemView.findViewById(R.id.delivery_address);
            order_status = itemView.findViewById(R.id.order_status);
            order_date = itemView.findViewById(R.id.order_date);
            spare_image = itemView.findViewById(R.id.gorder_image);
            deliver_date = itemView.findViewById(R.id.mydelivery_date);
            cancel_order = itemView.findViewById(R.id.conce_request_order);
            confirm_order = itemView.findViewById(R.id.conf_request_order);
        }
    }
}
