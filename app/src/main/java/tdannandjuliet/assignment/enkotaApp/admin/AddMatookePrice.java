package tdannandjuliet.assignment.enkotaApp.admin;

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

import tdannandjuliet.assignment.enkotaApp.R;
import tdannandjuliet.assignment.enkotaApp.constants.RequestHandler;

import static tdannandjuliet.assignment.enkotaApp.constants.Connectors.URL_SET_PRICES;

public class AddMatookePrice extends AppCompatActivity {
EditText small, medium,large;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_matooke_price);
        small = findViewById(R.id.small_price);
        medium = findViewById(R.id.medium_price);
        large = findViewById(R.id.large_price);
    }

    public void set_prices(View view) {
        final String Msmall = small.getText().toString();
        final String Mmedium = medium.getText().toString();
        final String MLarge = large.getText().toString();

        if(TextUtils.isEmpty(Msmall)){
           small.requestFocus();
           small.setError("Enter Price");
        }
        if(TextUtils.isEmpty(Mmedium)){
            medium.requestFocus();
            medium.setError("Enter Price");
        }
        if(TextUtils.isEmpty(MLarge)){
            large.requestFocus();
            large.setError("Enter Price");
        }

        @SuppressLint("StaticFieldLeak")
        class User extends AsyncTask<Void, Void, String> {

            private ProgressDialog pdLoading = new ProgressDialog(AddMatookePrice.this);

            protected void onPreExecute() {
                super.onPreExecute();

                //this method will be running on UI thread
                pdLoading.setMessage("\tSaving...");
                pdLoading.setCancelable(false);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();

                params.put("small", Msmall);
                params.put("medium", Mmedium);
                params.put("large", MLarge);

                return requestHandler.sendPostRequest(URL_SET_PRICES, params);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pdLoading.dismiss();
                Toast.makeText(AddMatookePrice.this, s, Toast.LENGTH_LONG).show();

                if(s.equalsIgnoreCase("Price Updated!")) {
                    Intent intent = new Intent(AddMatookePrice.this, AdminDashboard.class);
                    startActivity(intent);
                    finish();
                    small.setText("");
                    medium.setText("");
                    large.setText("");

                }

                else {
                    Toast.makeText(AddMatookePrice.this, s, Toast.LENGTH_LONG).show();
                }

            }

        }

        User prod_exec = new User();
        prod_exec.execute();
    }
}