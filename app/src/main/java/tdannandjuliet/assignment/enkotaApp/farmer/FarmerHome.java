package tdannandjuliet.assignment.enkotaApp.farmer;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alexzaitsev.meternumberpicker.MeterView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tdannandjuliet.assignment.enkotaApp.AccountManager;
import tdannandjuliet.assignment.enkotaApp.MainActivity;
import tdannandjuliet.assignment.enkotaApp.R;
import tdannandjuliet.assignment.enkotaApp.adapters.SupplierOrders_Adapter;
import tdannandjuliet.assignment.enkotaApp.constants.RequestHandler;
import tdannandjuliet.assignment.enkotaApp.helper.SharedPrefManager;
import tdannandjuliet.assignment.enkotaApp.helper.User;
import tdannandjuliet.assignment.enkotaApp.helper.ViewMatookeOrders;

import static tdannandjuliet.assignment.enkotaApp.R.drawable.ic_error_outline_black_24dp;
import static tdannandjuliet.assignment.enkotaApp.constants.Connectors.LOAD_FARMER_ORDERS;
import static tdannandjuliet.assignment.enkotaApp.constants.Connectors.URL_CANCEL_MATOOKE_ORDER;

public class FarmerHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,SupplierOrders_Adapter.OnItemClickListenerOrders{

    private MeterView q;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    // private InventoryItem it;4b4
    private ProgressBar spinner;
    private SwipeRefreshLayout swipeRefreshLayout;


    public final static String CONFIRM_ORDER_ID = "";
    List<ViewMatookeOrders> matookeOrdersList;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    TextView no_orders_today;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);
        no_orders_today = findViewById(R.id.no_data);

        recyclerView = findViewById(R.id.items_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        matookeOrdersList = new ArrayList<>();

        User user = SharedPrefManager.getInstance(this).getUser();

        final String farmer_id = String.valueOf(user.getPhone());

       FarmerFunction(farmer_id);



        progressDialog = new ProgressDialog(this);

        progressDialog.show();
        progressDialog.setMessage("Loading my orders, Please stay connected....");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);




        Toolbar toolbar = null;
        setSupportActionBar(toolbar);


        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        spinner = (ProgressBar) findViewById(R.id.progressBar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_farmer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_farmer);
        navigationView.setNavigationItemSelectedListener(this);

        View headView = navigationView.getHeaderView(0);
        final TextView headerManagerName = headView.findViewById(R.id.ManagerName);
        final TextView headerManagerEmail = headView.findViewById(R.id.ManagerMail);
        final ImageView headerManagerImage = headView.findViewById(R.id.imageView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddMatooke.class));

            }

        });
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_farmer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            // exit dialog box
            new FancyAlertDialog.Builder(this)
                    .setTitle("Warning!!!")
                    .setBackgroundColor(Color.parseColor("#5002a4"))  //Don't pass R.color.colorvalue
                    .setMessage("Do you really want to exit?")
                    .setNegativeBtnText("No")
                    .setPositiveBtnBackground(Color.parseColor("#FF4081"))  //Don't pass R.color.colorvalue
                    .setPositiveBtnText("Yes")
                    .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  //Don't pass R.color.colorvalue
                    .setAnimation(Animation.POP)
                    .isCancellable(true)
                    .setIcon(ic_error_outline_black_24dp, Icon.Visible)
                    .OnPositiveClicked(new FancyAlertDialogListener() {
                        @Override
                        public void OnClick() {
                            FarmerHome.super.onBackPressed();
                        }
                    })
                    .OnNegativeClicked(new FancyAlertDialogListener() {
                        @Override
                        public void OnClick() {

                        }
                    })
                    .build();
            // super.onBackPressed();
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.dashboard) {

        }
        else if (id == R.id.my_account) {

            //show the manager's account
            Intent intent = new Intent(FarmerHome.this, AccountManager.class);
            startActivity(intent);

        } else if (id == R.id.my_team) {
          Intent intent= new Intent(this, MyTeam.class);
          startActivity(intent);

        } else if (id == R.id.statistics) {
            Intent intent=new Intent(FarmerHome.this,MarketPrices.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            // Share app with others
            ApplicationInfo api = getApplicationContext().getApplicationInfo();
            String apkpath = api.sourceDir;
            Intent share_intent = new Intent(Intent.ACTION_SEND);
            share_intent.setType("application/vnd.android.package-archive");
            share_intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkpath)));
            startActivity(Intent.createChooser(share_intent, "Share app using"));

        } else if (id == R.id.nav_send) {

        } else if(id == R.id.diseses){

            Intent intent=new Intent(FarmerHome.this,Diseases_List.class);
            startActivity(intent);
        }
        else if(id==R.id.nav_logout){

            new FancyAlertDialog.Builder(this)
                    .setTitle("Signout!!!")
                    .setBackgroundColor(Color.parseColor("#D50000"))  //Don't pass R.color.colorvalue
                    .setMessage("Do you really want to exit?")
                    .setNegativeBtnText("No")
                    .setPositiveBtnBackground(Color.parseColor("#FF4081"))  //Don't pass R.color.colorvalue
                    .setPositiveBtnText("Yes")
                    .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  //Don't pass R.color.colorvalue
                    .setAnimation(Animation.POP)
                    .isCancellable(true)
                    .setIcon(ic_error_outline_black_24dp, Icon.Visible)
                    .OnPositiveClicked(new FancyAlertDialogListener() {
                        @Override
                        public void OnClick() {
                            SharedPrefManager.getInstance(getApplicationContext()).logout();
                            Intent i = new Intent(FarmerHome.this, MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.putExtra("EXIT", true);
                            startActivity(i);
                            finish();
                        }
                    })
                    .OnNegativeClicked(new FancyAlertDialogListener() {
                        @Override
                        public void OnClick() {

                        }
                    })
                    .build();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_farmer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void set_nav_viewable(View view) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_farmer);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @SuppressLint("StaticFieldLeak")
    public void FarmerFunction(final String email) {


        @SuppressWarnings("deprecation")
        class UserLoginClass extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("farmer_id", email);

                return requestHandler.sendPostRequest(LOAD_FARMER_ORDERS, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();


            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                try {


                    JSONArray array = new JSONArray(httpResponseMsg);
                    if(array.length()>0){
                        //traversing through all the object
                        for (int i = 0; i < array.length(); i++) {

                            //getting product object from json array
                            JSONObject s = array.getJSONObject(i);
//[{"id":1,"":"0776097843","":"Pending","":"1","":"prisons","":"2020-11-19 21:41:11","":"","":0,"":"cooking","":"https:\/\/linksuites.com\/matooke\/\/user_images\/7_matooke.png","price":"10000"}]
                            //adding the product to product list
                            matookeOrdersList.add(new ViewMatookeOrders(
                                    s.getString("id"),
                                    s.getString("phone"),
                                    s.getString("name"),
                                    s.getString("quantity"),
                                    s.getString("amount"),
                                    s.getString("delivery_address"),
                                    s.getString("order_date"),
                                    s.getString("delivery_date"),
                                    s.getString("status"),
                                    s.getString("matooke_photo")



                            ));

                            SupplierOrders_Adapter adapter = new SupplierOrders_Adapter(FarmerHome.this, matookeOrdersList);
                            recyclerView.setAdapter(adapter);
                            progressDialog.dismiss();
                            adapter.setOnClickListenerMatookeOrders(FarmerHome.this);
                        }

                    }
                    else {
                        no_orders_today.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
                        progressDialog.dismiss();
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(email);
    }



    @Override
    public void onItemclick(int position) {


        Intent intent = new Intent(FarmerHome.this, ConfirmOrderMatooke.class);
        ViewMatookeOrders clickedItem = matookeOrdersList.get(position);
        intent.putExtra(CONFIRM_ORDER_ID, clickedItem.getMoid());
        startActivity(intent);

    }

    @Override
    public void onOrderCancel(int position) {

        ViewMatookeOrders clickedItem = matookeOrdersList.get(position);
        final String id =clickedItem.getMoid();

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Cancelling Order, Please Wait....");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        //noinspection deprecation
        @SuppressLint("StaticFieldLeak")
        class User extends AsyncTask<Void, Void, String> {

            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("order_id", id);

                return requestHandler.sendPostRequest(URL_CANCEL_MATOOKE_ORDER, params);


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();

                Toast.makeText(FarmerHome.this, s, Toast.LENGTH_LONG).show();
                Intent register = new Intent(getApplicationContext(), FarmerHome.class);
                startActivity(register);
            }


        }

        User prod_exec = new User();
        prod_exec.execute();


    }


}
