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

import static tdannandjuliet.assignment.enkotaApp.constants.Connectors.REGISTER_BUYER;
import static tdannandjuliet.assignment.enkotaApp.constants.Connectors.URL_CHECK_PHONE;

public class SignUpBuyer extends AppCompatActivity {
    EditText bname,bphone,baddress,bemail,bpassword;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_customer);
        bname = findViewById(R.id.re_buyer_name);
        bphone = findViewById(R.id.re_buyer_mobile);
        baddress = findViewById(R.id.re_buyer_address);
        bemail = findViewById(R.id.re_buyer_email);
        bpassword = findViewById(R.id.re_buyer_password);
        progressDialog = new ProgressDialog(this);

    }

    public void backlogin(View view) {
        Intent intent=new Intent(SignUpBuyer.this,MainActivity.class);
        startActivity(intent);
    }


    public void signup_buyer(View view) {
      final String name = bname.getText().toString().trim();
      final String phone =bphone.getText().toString().trim();
      final String address =baddress.getText().toString().trim();
      final String email = bemail.getText().toString().trim();
      final String password = bpassword.getText().toString().trim();

      // check validations...
        if(TextUtils.isEmpty(name)){
            bname.requestFocus();
            bname.setError("Required !");
            return;
        }
        if(TextUtils.isEmpty(phone)){
            bphone.requestFocus();
            bphone.setError("Enter a Valid Phone Number!");
            return;
        }
        if(TextUtils.isEmpty(address)){
            baddress.setError("Delivery address is Required!");
            baddress.requestFocus();
            return;

        }
        if(TextUtils.isEmpty(email)){
            bemail.requestFocus();
            bemail.setError("Enter an email");
            return;
        }
        if(TextUtils.isEmpty(password)){
            bpassword.setError("Enter password!");
            bpassword.requestFocus();
        }

        //

        @SuppressLint("StaticFieldLeak")
        class Register_User extends AsyncTask<Void, Void, String> {



            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();
                params.put("phone", phone);


                return requestHandler.sendPostRequest(URL_CHECK_PHONE, params);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                if (s.equalsIgnoreCase("Phone already Registered!")) {
                    //Toast.makeText(EnterPhone_Number.this, s, Toast.LENGTH_LONG).show();
                    bphone.setError("Phone Number Already Registered!");
                    bphone.requestFocus();




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
                            params.put("name", name);
                            params.put("email", email);
                            params.put("phone", phone);
                            params.put("password", password);


                            return requestHandler.sendPostRequest(REGISTER_BUYER, params);


                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            progressDialog.dismiss();

                            Toast.makeText(SignUpBuyer.this, s, Toast.LENGTH_LONG).show();

                            if(s.equalsIgnoreCase("Registered successfully!")) {
                                Intent intent = new Intent(SignUpBuyer.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }

                            else {
                                Toast.makeText(SignUpBuyer.this, s, Toast.LENGTH_LONG).show();
                            }
                        }


                    }

                    RegisterBuyer registerBuyer = new RegisterBuyer();
                    registerBuyer.execute();



                }
                else {
                    Toast.makeText(SignUpBuyer.this, "Some went wrong!", Toast.LENGTH_LONG).show();
                }
            }


        }

        Register_User register_user = new Register_User();
        register_user.execute();



        //



    }



}
