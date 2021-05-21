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
import tdannandjuliet.assignment.enkotaApp.helper.DisplayMatooke;


public class Products_Adapter extends RecyclerView.Adapter<Products_Adapter.JobViewHolder> {

    private Context mCtx;
    private List<DisplayMatooke> product_dbList;

    private Products_Adapter.OnItemClickListenerJobs mListener;

    public void setOnClickListenerJobs(Products_Adapter.OnItemClickListenerJobs listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public Products_Adapter.JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.product_list, null);
        return new JobViewHolder(view);
    }


    public Products_Adapter(Context mCtx, List<DisplayMatooke> product_dbList) {
        this.mCtx = mCtx;
        this.product_dbList = product_dbList;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Products_Adapter.JobViewHolder holder, int position) {
        DisplayMatooke s = product_dbList.get(position);

        Glide.with(mCtx)
                .load(s.getPdt_image())
                .placeholder(R.drawable.banna)
                .into(holder.PImage);
        holder.PName.setText(s.getPdt_name());
        holder.Plocation.setText(s.getPdt_location());
        holder.Pprice.setText("Ugx" + " " + s.getPdct_price());
        holder.Pquantity.setText(s.getPdct_qty());
        holder.Ptype.setText(s.getPdt_type());
        holder.Pseller.setText(s.getSeller_phone());

    }

    public interface OnItemClickListenerJobs {
        void onContactSeller(int position);
        void onOrderNow(int position);
    }


    @Override
    public int getItemCount() {
        return product_dbList.size();
    }
    public class JobViewHolder extends RecyclerView.ViewHolder {
        TextView PName,Plocation,Pprice,Pquantity,Ptype,Pseller,allocate_map;
        ImageView PImage;
        Button Contact_sell,btn_order;
        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            PName = itemView.findViewById(R.id.pdct_name);
            Plocation = itemView.findViewById(R.id.pdct_location);
            Pprice = itemView.findViewById(R.id.pdct_price);
            Pquantity = itemView.findViewById(R.id.pdt_qty);
            Ptype = itemView.findViewById(R.id.pdt_typee);
            PImage = itemView.findViewById(R.id.pdct_image);
            Pseller = itemView.findViewById(R.id.pdt_seller_name);
            btn_order = itemView.findViewById(R.id.order_pdt);
            Contact_sell = itemView.findViewById(R.id.contact_seller);
            allocate_map = itemView.findViewById(R.id.allocate_on_map);

            Contact_sell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onContactSeller(position);
                        }
                    }
                }
            });

            btn_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onOrderNow(position);
                        }
                    }
                }
            });

        }

    }

}
