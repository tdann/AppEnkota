package tdannandjuliet.assignment.enkotaApp.farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import tdannandjuliet.assignment.enkotaApp.R;

import static tdannandjuliet.assignment.enkotaApp.constants.Connectors.URL_VIEW_PRICES;

public class MarketPrices extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_prices);
        WebView simpleWebView = findViewById(R.id.view_price_list);
        simpleWebView.loadUrl(URL_VIEW_PRICES);
    }
}