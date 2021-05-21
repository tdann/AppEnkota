package tdannandjuliet.assignment.enkotaApp.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomappbar.BottomAppBar;

import tdannandjuliet.assignment.enkotaApp.R;

public class AdminDashboard extends AppCompatActivity implements View.OnClickListener {

    private BottomAppBar bottomAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard);

        View card1 = findViewById(R.id.add_farm_tips);
        View card2 = findViewById(R.id.add_matooke_prices);
        View card3 = findViewById(R.id.add_common_diseases);
        View card4 = findViewById(R.id.adverts);


        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);


    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.add_farm_tips:
                Intent intent = new Intent(AdminDashboard.this, AddFarmingTips.class);
                startActivity(intent);
                break;
            case R.id.add_matooke_prices:
                Intent i = new Intent(AdminDashboard.this, AddMatookePrice.class);
                startActivity(i);
                break;
            case R.id.add_common_diseases:
                Intent ar = new Intent(AdminDashboard.this, Add_CommonDiseases.class);
                startActivity(ar);
                break;
            case R.id.adverts:
                Intent mech = new Intent(AdminDashboard.this, Add_Adverts.class);
                startActivity(mech);
                break;


        }
    }
}