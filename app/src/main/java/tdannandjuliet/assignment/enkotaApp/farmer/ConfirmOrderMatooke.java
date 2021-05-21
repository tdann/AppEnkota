package tdannandjuliet.assignment.enkotaApp.farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;

import tdannandjuliet.assignment.enkotaApp.R;
import tdannandjuliet.assignment.enkotaApp.constants.RequestHandler;

import static tdannandjuliet.assignment.enkotaApp.constants.Connectors.URL_CONFIRM_PRODUCT_ORDER;
import static tdannandjuliet.assignment.enkotaApp.farmer.FarmerHome.CONFIRM_ORDER_ID;

public class ConfirmOrderMatooke extends AppCompatActivity {

    EditText del_date;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_order_matooke);
        del_date = findViewById(R.id.add_delivery_date);

        del_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(ConfirmOrderMatooke.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                del_date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });




    }

    public void save_delivery_order(View view) {
        final String  c_date = del_date.getText().toString();
        if(TextUtils.isEmpty(c_date)){
            del_date.requestFocus();
            del_date.setError("Delivery Date is Required!");
            return;
        }
        Intent intent = getIntent();
        final String id = intent.getStringExtra(CONFIRM_ORDER_ID);
        @SuppressWarnings("deprecation")
        @SuppressLint("StaticFieldLeak")
        class Add_Order extends AsyncTask<Void, Void, String> {

            private ProgressDialog pdLoading = new ProgressDialog(ConfirmOrderMatooke.this);

            protected void onPreExecute() {
                super.onPreExecute();

                //this method will be running on UI thread
                pdLoading.setMessage("\tConfirming order...");
                pdLoading.setCancelable(false);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("delivery_date", c_date);
                params.put("order_id", id);

                return requestHandler.sendPostRequest(URL_CONFIRM_PRODUCT_ORDER, params);
            }      @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pdLoading.dismiss();
                Toast.makeText(ConfirmOrderMatooke.this, s, Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), FarmerHome.class));
                finish();
            }


        }

        Add_Order add_order = new Add_Order();
        add_order.execute();

    }

    public void cancel_delivery_order(View view) {
        Intent dd = new Intent(getApplicationContext(), FarmerHome.class);
        startActivity(dd);
    }
}