package com.example.sibi.mapapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Button btn1,btn2,btn3;
    GPSTracker gps;
    AddressResultReceiver mResultReceiver;

    double latitudeEdit;
    double longitudeEdit;
    TextView infoText;
    CheckBox checkBox;
    ImageView userImage ;
    private static final int TAKE_PHOTO_CODE = 0;
    private static final int SELECTED_PICTURE=121;


    int fetchType = Constants.USE_ADDRESS_LOCATION;

    private static final String TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // btnshw=(Button) findViewById(R.id.button4);


        Button capture = (Button) findViewById(R.id.button3);
        Button select1=(Button) findViewById(R.id.button6);
        //Bitmap photo=BitmapFactory.decodeResource(getResources(),R.drawable.marker_maps);
        //Intent intent=new Intent();
        //intent.setClass(MainActivity.this,home.class);
        //intent.putExtra("Bitmap",photo);
        //startActivity(intent);
        userImage = (ImageView) findViewById(R.id.imageView2);
        //Button click eventlistener. Initializes the camera.
        capture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,TAKE_PHOTO_CODE);
            }
        });
        select1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent galleryint=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryint,SELECTED_PICTURE);
            }

        });







        //addressEdit = (EditText) findViewById(R.id.addressEdit);

        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //button_map = (Button) findViewById(R.id.button);
        infoText = (TextView) findViewById(R.id.address_txt);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        mResultReceiver = new AddressResultReceiver(null);
        final Handler handler=new Handler();
        onClick(null);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onButtonClicked(null);
                    //handler.postDelayed(this, 1000);

                }

            }, 13000);


//Intent inte=new Intent();
//onActivityResult(2,1,inte);

    }

        public void onClick(View v) {


        gps=new GPSTracker(MainActivity.this);
        if(gps.canGetLocation()){

            double latidude=gps.getLatitude();
            double longitude=gps.getLongitude();
            longitudeEdit = longitude;
            latitudeEdit = latidude;
            Toast.makeText(getApplicationContext(),"Getting Coordinates",Toast.LENGTH_LONG).show();

        }else {
            gps.showSettingAlert();
        }

    }

    public void onButtonClicked(View view) {
        Intent intent = new Intent(this, GeocodeAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.FETCH_TYPE_EXTRA, fetchType);


            if(latitudeEdit == 0 || longitudeEdit== 0) {
                Toast.makeText(this,
                        "Please Wait",
                        Toast.LENGTH_LONG).show();
                return;
            }
            intent.putExtra(Constants.LOCATION_LATITUDE_DATA_EXTRA,
                    Double.parseDouble(String.valueOf(latitudeEdit)));
            intent.putExtra(Constants.LOCATION_LONGITUDE_DATA_EXTRA,
                    Double.parseDouble(String.valueOf(longitudeEdit)));

        infoText.setVisibility(View.INVISIBLE);

        Log.e(TAG, "Starting Service");
        startService(intent);
    }



    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, final Bundle resultData) {
            if (resultCode == Constants.SUCCESS_RESULT) {
                final Address address = resultData.getParcelable(Constants.RESULT_ADDRESS);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        infoText.setVisibility(View.VISIBLE);
                        infoText.setText(resultData.getString(Constants.RESULT_DATA_KEY));
                    }
                });
            }
            else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        infoText.setVisibility(View.VISIBLE);
                        infoText.setText(resultData.getString(Constants.RESULT_DATA_KEY));
                    }
                });
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == TAKE_PHOTO_CODE) {
            final Bitmap photo = (Bitmap) data.getExtras().get("data");
            userImage.setImageBitmap(photo);
            Log.d("CameraDemo", "Pic saved");
            btn1=(Button)findViewById(R.id.button);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this,home.class);
                    intent.putExtra("Bitmap",photo);
                    startActivity(intent);
                }
            });


        }
        else if(requestCode==SELECTED_PICTURE) {
            // (){
            Uri uri=data.getData();
            String[] projection ={MediaStore.Images.Media.DATA};
            Cursor cursor=getContentResolver().query(uri,projection,null,null,null);
            cursor.moveToFirst();
            int columnIndex=cursor.getColumnIndex(projection[0]);
            String filepath=cursor.getString(columnIndex);
            cursor.close();
            final Bitmap yourSelectedImage= BitmapFactory.decodeFile(filepath);
            userImage.setImageBitmap(yourSelectedImage);
            //Drawable d=new BitmapDrawable(yourSelectedImage);
            //userImage.setBackground(d);
            Log.d("gallerydemo","pic showed");
            btn2=(Button)findViewById(R.id.button);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent2=new Intent();
                    intent2.setClass(MainActivity.this,home.class);
                    intent2.putExtra("Bitmap",yourSelectedImage);
                    startActivity(intent2);
                }
            });

        }
        else if (requestCode!=TAKE_PHOTO_CODE&&requestCode!=SELECTED_PICTURE){

            Drawable drawable=getResources().getDrawable(R.drawable.marker_maps);
            final Bitmap bitmap2=((BitmapDrawable) drawable).getBitmap();
            //userImage.setImageBitmap(bitmap2);
            btn3=(Button) findViewById(R.id.button);
            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent3=new Intent();
                    intent3.setClass(MainActivity.this,home.class);
                    intent3.putExtra("Bitma",bitmap2);
                    startActivity(intent3);
                }
            });
        }

    }

}
