package tdannandjuliet.assignment.enkotaApp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import tdannandjuliet.assignment.enkotaApp.constants.RequestHandler;

import static tdannandjuliet.assignment.enkotaApp.constants.Connectors.REGISTER_FARMER;
import static tdannandjuliet.assignment.enkotaApp.constants.Connectors.URL_CHECK_PHONE;

public class SignUpFarmer extends AppCompatActivity {
 EditText name, phone, address,email, password;

 ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_farmer);

        name = findViewById(R.id.re_farmer_name);
        phone = findViewById(R.id.re_farmermobile);
        address = findViewById(R.id.re_farmer_address);
        email = findViewById(R.id.re_farmer_email);
        password = findViewById(R.id.re_farmer_password);
        progressDialog = new ProgressDialog(this);
    }

    public void signup_farmer(View view) {

        final String fname = name.getText().toString().trim();
        final String fphone = phone.getText().toString().trim();
        final String faddress = address.getText().toString().trim();
        final String femail = email.getText().toString().trim();
        final String fpassword = password.getText().toString().trim();

        if(TextUtils.isEmpty(fname)){
            name.setError("Enter Name");
            name.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(fphone)){
            phone.requestFocus();
            phone.setError("Enter Phone Number");
            return;
        }
        if(TextUtils.isEmpty(faddress)){
            address.requestFocus();
            address.setError("Enter address");
            return;
        }
        if(!TextUtils.isEmpty(femail) && !android.util.Patterns.EMAIL_ADDRESS.matcher(femail).matches()){
            email.setError("Enter a valid Email");
            email.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(femail).matches()) {
            email.setError("Enter a valid email");
            email.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(fpassword)){
            password.setError("Enter a password!");
            password.requestFocus();
            return;
        }

        // checking whether Phone Number is already registered..

        @SuppressLint("StaticFieldLeak")
        class Register_User extends AsyncTask<Void, Void, String> {



            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();
                params.put("phone", fphone);


                return requestHandler.sendPostRequest(URL_CHECK_PHONE, params);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                if (s.equalsIgnoreCase("Phone already Registered!")) {
                    //Toast.makeText(EnterPhone_Number.this, s, Toast.LENGTH_LONG).show();
                    phone.setError("Phone Number Already Registered!");
                    phone.requestFocus();




                } else if(s.equalsIgnoreCase("Not registered!")){

                    @SuppressLint("StaticFieldLeak")
                    class RegisterBuyer extends AsyncTask<Void, Void, String> {



                        protected void onPreExecute() {
                            super.onPreExecute();
                            progressDialog.show();
                            progressDialog.setMessage("Saving Please Wait....");
                            progressDialog.setCancelable(false);
                            progressDialog.setCanceledOnTouchOutside(false);
                        }

                        @Override
                        protected String doInBackground(Void... voids) {

                            RequestHandler requestHandler = new RequestHandler();

                            HashMap<String, String> params = new HashMap<>();
                            params.put("name", fname);
                            params.put("email", femail);
                            params.put("address", faddress);
                            params.put("phone", fphone);
                            params.put("password", fpassword);


                            return requestHandler.sendPostRequest(REGISTER_FARMER, params);


                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            progressDialog.dismiss();

                            Toast.makeText(SignUpFarmer.this, s, Toast.LENGTH_LONG).show();

                            if(s.equalsIgnoreCase("Farmer Account successfully created")) {
                                Intent intent = new Intent(SignUpFarmer.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                phone.setText("");
                                email.setText("");
                                address.setText("");

                            }

                            else {
                                Toast.makeText(SignUpFarmer.this, s, Toast.LENGTH_LONG).show();
                            }
                        }


                    }

                    RegisterBuyer registerBuyer = new RegisterBuyer();
                    registerBuyer.execute();

                }
                else {
                    Toast.makeText(SignUpFarmer.this, "Oops something went wrong!", Toast.LENGTH_LONG).show();
                }
            }


        }

        Register_User register_user = new Register_User();
        register_user.execute();

        ///

    }


    public void backlogin(View view) {
        Intent intent=new Intent(SignUpFarmer.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
