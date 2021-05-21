package tdannandjuliet.assignment.enkotaApp;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import tdannandjuliet.assignment.enkotaApp.admin.AdminDashboard;
import tdannandjuliet.assignment.enkotaApp.buyer.MyHome;
import tdannandjuliet.assignment.enkotaApp.constants.RequestHandler;
import tdannandjuliet.assignment.enkotaApp.farmer.FarmerHome;
import tdannandjuliet.assignment.enkotaApp.helper.SharedPrefManager;
import tdannandjuliet.assignment.enkotaApp.helper.User;

import static tdannandjuliet.assignment.enkotaApp.constants.Connectors.LOGIN;


public class MainActivity extends AppCompatActivity {
    EditText password, phone;
    ProgressDialog progressDialog;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);

        progressBar = new ProgressBar(this);
        progressDialog = new ProgressDialog(this);
    }

    public void managerlogin(View view) {
       final  String entered_phone = phone.getText().toString().trim();
       final  String entered_password = password.getText().toString().trim();

        if(TextUtils.isEmpty(entered_phone)){
            phone.requestFocus();
            phone.setError("Phone is Required!");
            return;
        }

        if (entered_phone.length() < 10 || entered_phone.length()>13) {
            phone.setError("Valid  Phone number is required");
            phone.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(entered_password)){
            password.requestFocus();
            password.setError("Enter password!");
            return;
        }
//        if(entered_phone.equals("0773871371")&& entered_password.equals("admin")){
//            // not yet impleted
//            Intent intent=new Intent(MainActivity.this, AdminDashboard.class);
//            startActivity(intent);
//            finish();
//        }
        else{

        UserLoginFunction(entered_phone,entered_password);
        }
    }

    public void signupasfarmer(View view) {
        Intent intent=new Intent(MainActivity.this, SignUpFarmer.class);
        startActivity(intent);
    }

    public void signupasbuyer(View view) {

        Intent intent=new Intent(MainActivity.this, SignUpBuyer.class);
        startActivity(intent);
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manager_main, menu);
        return true;



}


    @SuppressLint("StaticFieldLeak")
    public void UserLoginFunction(final String email, final String password) {


/**
 *
 */
        @SuppressWarnings("deprecation")
        class UserLoginClass extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("phone", email);
                params.put("password", password);
                return requestHandler.sendPostRequest(LOGIN, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();


                progressDialog.show();
                progressDialog.setMessage("Checking   Please Wait....");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                // progressDialog = ProgressDialog.show(Login.this, "Checking...", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(httpResponseMsg);

                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();


                        JSONObject userJson = obj.getJSONObject("user");

                        User user = new User(
                                userJson.getInt("id"),
                                userJson.getString("email"),//email
                                userJson.getString("password"),//user_role
                                userJson.getString("phone"),//farmer_id
                                userJson.getString("user_role"),//phone
                                userJson.getString("buyer_id"),//buyer_id
                                userJson.getString("farmer_id")//password

                        );


                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                        //Toast.makeText(getApplicationContext(), user.getBuyer_id(), Toast.LENGTH_LONG).show();
                        if (user.getPassword().equals("Buyer")) {
                            startActivity(new Intent(getApplicationContext(), MyHome.class));
                            finish();

                        } else if (user.getPassword().equals("Farmer")) {
                            startActivity(new Intent(getApplicationContext(), FarmerHome.class));
                            finish();
                        }


                    } else {
                        Toast.makeText(getApplicationContext(), httpResponseMsg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(email, password);
    }


    public void head_to_ma(View view) {

    }
}
