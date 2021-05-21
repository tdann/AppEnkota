package tdannandjuliet.assignment.enkotaApp.buyer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tdannandjuliet.assignment.enkotaApp.AboutUs;
import tdannandjuliet.assignment.enkotaApp.ContactUs;
import tdannandjuliet.assignment.enkotaApp.MainActivity;
import tdannandjuliet.assignment.enkotaApp.R;
import tdannandjuliet.assignment.enkotaApp.adapters.Products_Adapter;
import tdannandjuliet.assignment.enkotaApp.helper.DisplayMatooke;
import tdannandjuliet.assignment.enkotaApp.helper.SharedPrefManager;

import static tdannandjuliet.assignment.enkotaApp.R.drawable.ic_error_outline_black_24dp;
import static tdannandjuliet.assignment.enkotaApp.constants.Connectors.LOAD_MATOOKE;


public class MyHome extends AppCompatActivity implements Products_Adapter.OnItemClickListenerJobs{

    private AppBarConfiguration mAppBarConfiguration;
    public final static String PRODUCT_IDD = "";
    public final static String SUPPLIER_NO = "";
    //
    List<DisplayMatooke> jobsList;

    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    TextView no_pdcts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        recyclerView = findViewById(R.id.list_matooke);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        no_pdcts = findViewById(R.id.matooke_no);
        jobsList = new ArrayList<>();

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Loading Matooke Please Wait....");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        load_materials();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //trial
    public void logout(MenuItem item) {

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
                        SharedPrefManager.getInstance(getApplicationContext()).logout();
                        Intent i = new Intent(MyHome.this, MainActivity.class);
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
    public void manage_address(MenuItem item) {
        Intent intent=new Intent(MyHome.this, MyAddress.class);
        startActivity(intent);
    }
    public void mycart(MenuItem item) {
        Intent intent=new Intent(MyHome.this, MyCart.class);
        startActivity(intent);
    }
    public void myorders(MenuItem item) {
        Intent intent=new Intent(MyHome.this, MyOrders.class);
        startActivity(intent);
    }

    public void tranction_history(MenuItem item) {
        Intent intent=new Intent(MyHome.this, TransactionHistory.class);
        startActivity(intent);
    }

    public void refer_earn(MenuItem item) {
        Intent intent=new Intent(MyHome.this, ReferEarn.class);
        startActivity(intent);
    }
    public void change_password(MenuItem item) {
        Intent intent=new Intent(MyHome.this, ChangePassword.class);
        startActivity(intent);
    }
    public void contact_us(MenuItem item) {
        Intent intent=new Intent(MyHome.this, ContactUs.class);
        startActivity(intent);
    }
    public void about_us(MenuItem item) {
        Intent intent=new Intent(MyHome.this, AboutUs.class);
        startActivity(intent);
    }
    //
    public void share_app(MenuItem item) {
        ApplicationInfo api = getApplicationContext().getApplicationInfo();
        String apkpath = api.sourceDir;
        Intent share_intent = new Intent(Intent.ACTION_SEND);
        share_intent.setType("application/vnd.android.package-archive");
        share_intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkpath)));
        startActivity(Intent.createChooser(share_intent, "Share app using"));
    }

    //

    public void mynotifications(MenuItem item) {
        Intent intent=new Intent(MyHome.this, MyNotifications.class);
        startActivity(intent);
    }
    //mynotifications


    private void load_materials() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, LOAD_MATOOKE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray array = new JSONArray(response);
                            if(array.length()>0){
                                //traversing through all the object
                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject s = array.getJSONObject(i);

                                    jobsList.add(new DisplayMatooke(
                                            s.getString("id"),
                                            s.getString("name"),
                                            s.getString("category"),
                                            s.getString("amount"),
                                            s.getString("quantity"),
                                            s.getString("location"),
                                            s.getString("matooke_photo"),
                                            s.getString("phone")



                                    ));
                                    Products_Adapter adapter = new Products_Adapter(MyHome.this, jobsList);
                                    recyclerView.setAdapter(adapter);
                                    progressDialog.dismiss();
                                    adapter.setOnClickListenerJobs(MyHome.this);
                                }


                            }
                            else {
                                no_pdcts.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                                recyclerView.setVisibility(View.INVISIBLE);
                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }


    @Override
    public void onContactSeller(int position) {
        Intent intent = new Intent(MyHome.this, Call_Farmer.class);
        DisplayMatooke clickedItem = jobsList.get(position);
        intent.putExtra(SUPPLIER_NO, clickedItem.getSeller_phone());
        startActivity(intent);

    }

    @Override
    public void onOrderNow(int position) {
        Intent intent = new Intent(MyHome.this, OrderNow.class);
        DisplayMatooke clickedItem = jobsList.get(position);
        intent.putExtra(PRODUCT_IDD, clickedItem.getPdId());
        startActivity(intent);
        Toast.makeText(MyHome.this, clickedItem.getPdId(), Toast.LENGTH_LONG).show();
    }
}