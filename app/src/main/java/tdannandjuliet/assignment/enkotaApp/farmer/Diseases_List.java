package tdannandjuliet.assignment.enkotaApp.farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import tdannandjuliet.assignment.enkotaApp.R;

import static tdannandjuliet.assignment.enkotaApp.constants.Connectors.URL_VIEW_DISEASES;

public class Diseases_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diseases__list);

        WebView simpleWebView = findViewById(R.id.view_diseases_list);
        simpleWebView.loadUrl(URL_VIEW_DISEASES);
    }
}