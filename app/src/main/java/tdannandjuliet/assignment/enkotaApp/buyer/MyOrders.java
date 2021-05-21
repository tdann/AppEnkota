package tdannandjuliet.assignment.enkotaApp.buyer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tdannandjuliet.assignment.enkotaApp.R;
import tdannandjuliet.assignment.enkotaApp.adapters.BuyerOrders_Adapter;
import tdannandjuliet.assignment.enkotaApp.constants.RequestHandler;
import tdannandjuliet.assignment.enkotaApp.helper.BuyerOrders;
import tdannandjuliet.assignment.enkotaApp.helper.SharedPrefManager;
import tdannandjuliet.assignment.enkotaApp.helper.User;

import static tdannandjuliet.assignment.enkotaApp.constants.Connectors.LOAD_MY_ORDERS;

public class MyOrders extends AppCompatActivity {
    List<BuyerOrders> orders;
    RecyclerView recyclerView;
    TextView no_orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        recyclerView = findViewById(R.id.spare_request_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orders = new ArrayList<>();
        no_orders = findViewById(R.id.nodata);
        User user = SharedPrefManager.getInstance(this).getUser();

        final String xx = String.valueOf(user.getBuyer_id());
        UserLoginFunction(xx);
    }
    @SuppressLint("StaticFieldLeak")
    public void UserLoginFunction(final String email) {


/**
 *
 */
        @SuppressWarnings("deprecation")
        class UserLoginClass extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("buyer_id", email);

                return requestHandler.sendPostRequest(LOAD_MY_ORDERS, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                try {

                    final ProgressBar progressBar = findViewById(R.id.progressBar_request_order);
                    progressBar.setVisibility(View.VISIBLE);
                    setProgressBarIndeterminateVisibility(true);
                    //converting the string to json array object
                    JSONArray array = new JSONArray(httpResponseMsg);
                    if(array.length()>0){
                        //traversing through all the object
                        for (int i = 0; i < array.length(); i++) {

                            //getting product object from json array
                            JSONObject s = array.getJSONObject(i);

                            orders.add(new BuyerOrders(
                                    s.getString("id"),
                                    s.getString("matooke_photo"),
                                    s.getString("name"),
                                    s.getString("quantity"),
                                    s.getString("amount"),
                                    s.getString("delivery_address"),
                                    s.getString("status"),
                                    s.getString("order_date"),
                                    s.getString("delivery_date")
                            ));
                            BuyerOrders_Adapter adapter = new BuyerOrders_Adapter(MyOrders.this, orders);
                            recyclerView.setAdapter(adapter);
                            progressBar.setVisibility(View.GONE);
                        }


                    }
                    else{

                        no_orders.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.INVISIBLE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(email);
    }

}