package tdannandjuliet.assignment.enkotaApp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import tdannandjuliet.assignment.enkotaApp.constants.RequestHandler;
import tdannandjuliet.assignment.enkotaApp.helper.SharedPrefManager;
import tdannandjuliet.assignment.enkotaApp.helper.User;

import static tdannandjuliet.assignment.enkotaApp.constants.Connectors.UPDATE_PROFILE_FARMER;

public class AccountManager extends AppCompatActivity {
EditText newpass,oldpass,phone,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);
        newpass = findViewById(R.id.new_password);
        oldpass = findViewById(R.id.old_pass);
        phone = findViewById(R.id.phone_noo);
        email = findViewById(R.id.old_email);
        User user = SharedPrefManager.getInstance(this).getUser();
        final String number = user.getUser_role();
        final String password = user.getFarmer_id();
        final String emmail = user.getEmail();
        phone.setText(number);
        oldpass.setText(password);
        email.setText(emmail);
    }




    public void update_data(View view) {
        final String paa= newpass.getText().toString();
        final String mail = email.getText().toString();
        final String phone_n = phone.getText().toString();

        //noinspection deprecation
        @SuppressLint("StaticFieldLeak")
        class User extends AsyncTask<Void, Void, String> {

            private ProgressDialog pdLoading = new ProgressDialog(AccountManager.this);

            protected void onPreExecute() {
                super.onPreExecute();

                //this method will be running on UI thread
                pdLoading.setMessage("\tupdating...");
                pdLoading.setCancelable(false);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();

                params.put("password", paa);
                params.put("phone", phone_n);
                params.put("email", mail);

                return requestHandler.sendPostRequest(UPDATE_PROFILE_FARMER, params);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pdLoading.dismiss();
                Toast.makeText(AccountManager.this, s, Toast.LENGTH_LONG).show();



            }

        }

        User prod_exec = new User();
        prod_exec.execute();


    }
}
