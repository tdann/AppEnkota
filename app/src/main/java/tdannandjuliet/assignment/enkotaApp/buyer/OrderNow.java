package tdannandjuliet.assignment.enkotaApp.buyer;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.util.HashMap;

import tdannandjuliet.assignment.enkotaApp.R;
import tdannandjuliet.assignment.enkotaApp.constants.RequestHandler;
import tdannandjuliet.assignment.enkotaApp.helper.SharedPrefManager;
import tdannandjuliet.assignment.enkotaApp.helper.User;

import static tdannandjuliet.assignment.enkotaApp.buyer.MyHome.PRODUCT_IDD;
import static tdannandjuliet.assignment.enkotaApp.constants.Connectors.URL_ADD_ORDERS;


public class OrderNow extends AppCompatActivity {
    EditText address,qty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_now);
        qty = findViewById(R.id.specify_quantity);
        address = findViewById(R.id.enter_delivery_address);


    }

    public void order_now(View view) {
        final String quantity = qty.getText().toString();
        final String delivery_address = address.getText().toString();

        User user = SharedPrefManager.getInstance(this).getUser();
        final String c = String.valueOf(user.getBuyer_id());


        if(TextUtils.isEmpty(quantity)){
            qty.requestFocus();
            qty.setError("How many do you want?");
            return;
        }
        if(TextUtils.isEmpty(delivery_address)){
            address.setError("Where do you want your material to be delivered?");
            address.requestFocus();
            return;
        }



        @SuppressWarnings("deprecation")
        @SuppressLint("StaticFieldLeak")
        class Add_Order extends AsyncTask<Void, Void, String> {

            private ProgressDialog pdLoading = new ProgressDialog(OrderNow.this);

            protected void onPreExecute() {
                super.onPreExecute();

                //this method will be running on UI thread
                pdLoading.setMessage("\tadding to cart...");
                pdLoading.setCancelable(false);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();
                Intent intent = getIntent();
                final String id = intent.getStringExtra(PRODUCT_IDD);

                HashMap<String, String> params = new HashMap<>();
                params.put("buyer_id", c);
                params.put("matooke_id", id);//matooke_id
                params.put("quantity", quantity);
                params.put("delivery_address", delivery_address);

                return requestHandler.sendPostRequest(URL_ADD_ORDERS, params);
            }      @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pdLoading.dismiss();
                Toast.makeText(OrderNow.this, s, Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), MyOrders.class));
                finish();
            }


        }

        Add_Order add_order = new Add_Order();
        add_order.execute();

    }
}