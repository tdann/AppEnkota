package tdannandjuliet.assignment.enkotaApp.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import tdannandjuliet.assignment.enkotaApp.R;
import tdannandjuliet.assignment.enkotaApp.constants.RequestHandler;

import static tdannandjuliet.assignment.enkotaApp.constants.Connectors.URL_ADD_COMMON_DISEASES;

public class Add_CommonDiseases extends AppCompatActivity {
    EditText name,signs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add__common_diseases);
        name = findViewById(R.id.add_disease_name);
        signs = findViewById(R.id.add_disease_signs);
    }

    public void save_disease(View view) {
        final String dName = name.getText().toString();
        final String dSigns = signs.getText().toString();


        @SuppressLint("StaticFieldLeak")
        class User extends AsyncTask<Void, Void, String> {

            private ProgressDialog pdLoading = new ProgressDialog(Add_CommonDiseases.this);

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

                params.put("name", dName);
                params.put("signs", dSigns);


                return requestHandler.sendPostRequest(URL_ADD_COMMON_DISEASES, params);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pdLoading.dismiss();
                Toast.makeText(Add_CommonDiseases.this, s, Toast.LENGTH_LONG).show();

                if(s.equalsIgnoreCase("Disease Saved")) {
                    Intent intent = new Intent(Add_CommonDiseases.this, Add_CommonDiseases.class);
                    startActivity(intent);
                    finish();
                    name.setText("");
                    signs.setText("");

                }

                else {
                    Toast.makeText(Add_CommonDiseases.this, s, Toast.LENGTH_LONG).show();
                }

            }

        }

        User prod_exec = new User();
        prod_exec.execute();

    }

}
