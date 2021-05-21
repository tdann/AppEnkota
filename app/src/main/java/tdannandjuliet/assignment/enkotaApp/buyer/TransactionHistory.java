package tdannandjuliet.assignment.enkotaApp.buyer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import tdannandjuliet.assignment.enkotaApp.R;
import tdannandjuliet.assignment.enkotaApp.constants.RequestHandler;
import tdannandjuliet.assignment.enkotaApp.helper.SharedPrefManager;
import tdannandjuliet.assignment.enkotaApp.helper.User;

import static tdannandjuliet.assignment.enkotaApp.constants.Connectors.TRANSACTIONS;

public class TransactionHistory extends AppCompatActivity {
    TextView username,tx;
    RelativeLayout alert;
    WebView simpleWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_transection);
        username = findViewById(R.id.loggedin_user);
        tx = findViewById(R.id.today_lecture);
        alert = findViewById(R.id.tvAlert);
        simpleWebView = findViewById(R.id.view_today_attendance);


        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

     //   tx.setText(formattedDate);

        User user = SharedPrefManager.getInstance(this).getUser();

        final String id = String.valueOf(user.getBuyer_id());
        UserLoginFunction(id);

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

                return requestHandler.sendPostRequest(TRANSACTIONS, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                try {


                    //converting the string to json array object
                    JSONArray array = new JSONArray(httpResponseMsg);
                    if(array.length()>0){

                        simpleWebView.loadUrl(TRANSACTIONS);

                    }
                    else{

                       alert.setVisibility(View.VISIBLE);

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