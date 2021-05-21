package tdannandjuliet.assignment.enkotaApp.buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import tdannandjuliet.assignment.enkotaApp.R;

import static tdannandjuliet.assignment.enkotaApp.buyer.MyHome.SUPPLIER_NO;

public class Call_Farmer extends AppCompatActivity {

    EditText edittext1;
    Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call__farmer);
        edittext1 = findViewById(R.id.supplier_no_holder);
        button1 = findViewById(R.id.call_supplier_now);
        edittext1.setInputType(InputType.TYPE_NULL);
        Intent intent = getIntent();
        final String no_number = intent.getStringExtra(SUPPLIER_NO);
        edittext1.setText(no_number);
        //Performing action on button click
        button1.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                // callPhoneNumber();
                Intent intentcall = new Intent(Intent.ACTION_CALL);
                String number = edittext1.getText().toString();
                if(number.trim().isEmpty()){
                    Toast.makeText(Call_Farmer.this,"Something went Wrong",Toast.LENGTH_LONG).show();
                }
                else {
                    intentcall.setData(Uri.parse("tel:"+number));
                }
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){

                    Toast.makeText(Call_Farmer.this,"Please Grant Permission",Toast.LENGTH_LONG).show();
                    requestPermission();
                }
                else {
                    startActivity(intentcall);
                }
            }

        });
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(Call_Farmer.this,new String[]{Manifest.permission.CALL_PHONE},1);
    }

    /* For your information, android version above 23 categorizes permissions as normal and dangerous permissions
     * Therefore android assumes that if you app used a camera yesterday, it does not have the instance kept. */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhoneNumber();
            }
        }
    }

    public void callPhoneNumber() {
        try {
            if (Build.VERSION.SDK_INT > 25) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Call_Farmer.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + edittext1.getText().toString()));
                startActivity(callIntent);

            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + edittext1.getText().toString()));
                startActivity(callIntent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
