package tdannandjuliet.assignment.enkotaApp.adapters;

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
import tdannandjuliet.assignment.enkotaApp.helper.ViewMatookeOrders;

public class SupplierOrders_Adapter extends RecyclerView.Adapter<SupplierOrders_Adapter.TipsViewHolder> {
    private Context mCtx;
    private List<ViewMatookeOrders> pdct_orders_list;

    private SupplierOrders_Adapter.OnItemClickListenerOrders mListener;

    public void setOnClickListenerMatookeOrders(SupplierOrders_Adapter.OnItemClickListenerOrders listener) {
        mListener = listener;
    }

    public SupplierOrders_Adapter(Context mCtx, List<ViewMatookeOrders> pdct_orders_list) {
        this.mCtx = mCtx;
        this.pdct_orders_list = pdct_orders_list;
    }


    @NonNull
    @Override
    public TipsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.supplier_order_list, null);
        return new TipsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TipsViewHolder holder, int position) {
        ViewMatookeOrders client_orders = pdct_orders_list.get(position);

        Glide.with(mCtx)
                .load(client_orders.getMoimage())
                .centerCrop()
                .placeholder(R.drawable.matooke_logo)
                .into(holder.matooke_image);
        holder.client_name.setText(client_orders.getMoclientname());
        holder.pdct_name.setText(client_orders.getMoname());
        holder.order_quantity.setText(client_orders.getMoquantity());
        holder.order_amount.setText(client_orders.getMoamount());
        holder.order_address.setText(client_orders.getMoaddress());
        holder.order_status.setText(client_orders.getMostatus());
        holder.order_date.setText(client_orders.getMoorderdate());
        holder.delivery_date.setText(client_orders.getModeliverydate());

        if (client_orders.getMostatus().equalsIgnoreCase("Processed")){
            // holder.cancel_order.setVisibility(View.GONE);
            holder.confirm_delivery.setVisibility(View.GONE);
        }
        else if(client_orders.getMostatus().equalsIgnoreCase("Cancelled")){
            holder.cancel_order.setVisibility(View.GONE);
            holder.confirm_delivery.setVisibility(View.GONE);
        }
        else if (client_orders.getMostatus().equalsIgnoreCase("Pending")){
            holder.cancel_order.setVisibility(View.VISIBLE);
            holder.confirm_delivery.setVisibility(View.VISIBLE);
        }
        else{
            holder.cancel_order.setVisibility(View.GONE);
            holder.confirm_delivery.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return pdct_orders_list.size();
    }


    public interface OnItemClickListenerOrders {
        void onItemclick(int position);
        void  onOrderCancel(int position);
    }

    public class TipsViewHolder extends RecyclerView.ViewHolder {
        TextView pdct_name, client_name, order_quantity, partno, order_amount, order_address, order_status, order_date, delivery_date,sp_deal_order_id;
        ImageView matooke_image;
        public Button confirm_delivery,cancel_order;

        public TipsViewHolder(@NonNull View itemView) {
            super(itemView);
            client_name = itemView.findViewById(R.id.dspare_name);
            pdct_name = itemView.findViewById(R.id.dpartname);
            order_quantity = itemView.findViewById(R.id.dqty);
            order_amount = itemView.findViewById(R.id.damount);
            order_address = itemView.findViewById(R.id.dlocate);
            order_status = itemView.findViewById(R.id.ddpending);
            order_date = itemView.findViewById(R.id.dorstatus);
            delivery_date = itemView.findViewById(R.id.dddpending);
            matooke_image = itemView.findViewById(R.id.dorder_image);
           // partno = itemView.findViewById(R.id.ppart_num);
            sp_deal_order_id = itemView.findViewById(R.id.sp_dealer_id);
            confirm_delivery = itemView.findViewById(R.id.requested_ordered);
            cancel_order = itemView.findViewById(R.id.requested_canceled_orders);
            confirm_delivery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemclick(position);
//                            mListener.onOrderCancel(position);
                        }
                    }
                }
            });
            cancel_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {

                            mListener.onOrderCancel(position);
                        }
                    }
                }
            });
        }
    }

}
