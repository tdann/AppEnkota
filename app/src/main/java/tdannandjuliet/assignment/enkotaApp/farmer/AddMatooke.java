package tdannandjuliet.assignment.enkotaApp.farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import tdannandjuliet.assignment.enkotaApp.R;
import tdannandjuliet.assignment.enkotaApp.constants.RequestHandler;
import tdannandjuliet.assignment.enkotaApp.helper.SharedPrefManager;
import tdannandjuliet.assignment.enkotaApp.helper.User;

import static tdannandjuliet.assignment.enkotaApp.constants.Connectors.ADD_PRODUCT;

public class AddMatooke extends AppCompatActivity {

    EditText pdct_name,type,price,qty,location,decs;
    //DatabaseReference product_reference;
    private static final int REQUEST_CAMERA = 1, SELECT_FILE = 0;
    Uri imageUri;
    Bitmap bitmap;
    private ImageView certificate;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._add_matooke);

        pdct_name = findViewById(R.id.product_name);
        type = findViewById(R.id.product_type);
        price =findViewById(R.id.product_price);
        qty = findViewById(R.id.product_quantity);
        location = findViewById(R.id.product_location);
        certificate = findViewById(R.id.build_image_p);
        progressDialog = new ProgressDialog(this);
    }

    public void save_product(View view) {
    final String name = pdct_name.getText().toString().trim();
    final String pd_type = type.getText().toString().trim();
    final String pd_price = price.getText().toString().trim();
    final String pd_qty = qty.getText().toString().trim();
    final String loca = location.getText().toString().trim();

    User user = SharedPrefManager.getInstance(this).getUser();
    final String supplier_id = String.valueOf(user.getPhone());

        if(TextUtils.isEmpty(name)){
        pdct_name.setError("Field Required!");
        pdct_name.requestFocus();
        return;
    }
        if(TextUtils.isEmpty(pd_type)){
        type.requestFocus();
        type.setError("Field Required!");
        return;
    }
        if(TextUtils.isEmpty(pd_price)){
        price.setError("Required Field!");
        price.requestFocus();
        return;
    }
        if(TextUtils.isEmpty(pd_qty)){
        qty.requestFocus();
        qty.setError("Required Field");
        return;

    }
        if(TextUtils.isEmpty(loca)){
        location.setError("Field Required!");
        location.requestFocus();
        return;
    }



    //noinspection deprecation
    @SuppressLint("StaticFieldLeak")
    class AddingProdct extends AsyncTask<Void, Void, String> {



        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
            progressDialog.setMessage("Saving Item Please Wait....");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected String doInBackground(Void... voids) {
            // pick selected certificate image and convert it o bytes for storing in db;

            ByteArrayOutputStream byteArrayOutputStreamObject;

            byteArrayOutputStreamObject = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);

            byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

            final String pdct_image = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);
            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("name", name);
            params.put("category", pd_type);
            params.put("amount", pd_price);
            params.put("quantity", pd_qty);
            params.put("location",loca );
            params.put("matooke_photo", pdct_image);
            params.put("farmer_id", supplier_id);
            return requestHandler.sendPostRequest(ADD_PRODUCT, params);


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s.equalsIgnoreCase("Product posted successful")){
                pdct_name.setText("");
                type.setText("");
                price.setText("");
                qty.setText("");
                location.setText("");
                decs.setText("");
                Toast.makeText(AddMatooke.this, s, Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(AddMatooke.this, s, Toast.LENGTH_LONG).show();
            }


        }


    }

    AddingProdct addingProdct = new AddingProdct();
        addingProdct.execute();


}



    public void get_pdct_image(View view) {
        SelectImage();
    }

    private void SelectImage() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddMatooke.this);
        builder.setTitle("Choose Product Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);


                } else if (items[i].equals("Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Add Matooke Image"), SELECT_FILE);

                } else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }

        });

        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA) {

            Bundle bundle = data.getExtras();
            assert bundle != null;
            bitmap = (Bitmap) bundle.get("data");
            certificate.setImageBitmap(bitmap);
        } else if (requestCode == SELECT_FILE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                certificate.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}