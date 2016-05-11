package com.example.meet;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.squareup.picasso.Picasso;

import org.brickred.socialauth.Profile;

import java.io.ByteArrayOutputStream;

public class UserQrGeneratorActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageButton goBack;
    com.melnykov.fab.ObservableScrollView scrollView;
    com.melnykov.fab.FloatingActionButton floatingAddButton;
    private ImageButton loadImage;
    private ImageView officeImage,userImage;
    private EditText designation,email_id,mob_no,phone_no,website,address;
    private TextView uName,uEmail,uMobile;
    private String LOG_TAG = "GenerateQRCode";
    private static final int PICK_FROM_GALLERY = 2;
    private Uri selectedImageUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newlayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        designation=(EditText)findViewById(R.id.editDesignation);
        uEmail=(EditText)findViewById(R.id.editEmail);
        mob_no=(EditText)findViewById(R.id.editMobile);
        phone_no=(EditText)findViewById(R.id.editPhone);
        website=(EditText)findViewById(R.id.editWebsite);
        address=(EditText)findViewById(R.id.editAddress);
        goBack=(ImageButton)findViewById(R.id.Back);
        loadImage=(ImageButton)findViewById(R.id.Edit);
        officeImage=(ImageView)findViewById(R.id.defaultImage);
        userImage=(ImageView)findViewById(R.id.userPerson);
        scrollView = (com.melnykov.fab.ObservableScrollView) findViewById(R.id.scrollView);
        floatingAddButton = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.fabGenerateQr);

        uName = (TextView) findViewById(R.id.uName);
        uEmail = (TextView) findViewById(R.id.uEmail);
        uMobile = (TextView) findViewById(R.id.uMobile);

        if(getIntent() != null){
            Profile data = (Profile) getIntent().getSerializableExtra("data");
            String fullName = data.getFirstName() + " " + data.getLastName();
            uName.setText(fullName);
            uEmail.setText(data.getEmail());
            uMobile.setText("8989898989");
        }
        //floatingAddButton.attachToScrollView(scrollView);
        setDefaultImage();




        //on pressing floatingbutton
        floatingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String desig = designation.getText().toString().trim();
                String email = email_id.getText().toString().trim();
                String mob = mob_no.getText().toString().trim();
                String phone = phone_no.getText().toString().trim();
                String site = website.getText().toString().trim();
                String add = address.getText().toString().trim();
                if ((desig.isEmpty()) || email.isEmpty() || mob.isEmpty())

                {
                    Toast.makeText(getBaseContext(), "Make Proper Entry", Toast.LENGTH_SHORT).show();
                } else {

                    String qrInputText = designation.getText().toString().trim();
                    Log.v(LOG_TAG, qrInputText);

                    String qrInputText2 = email_id.getText().toString().trim();
                    Log.v(LOG_TAG, qrInputText2);

                    String qrInputText3 = mob_no.getText().toString().trim();
                    Log.v(LOG_TAG, qrInputText3);

                    DataProvider dataProvider = new DataProvider();
                    String[] s = dataProvider.getData(qrInputText, qrInputText2, qrInputText3);

                    //Find screen size
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = width < height ? width : height;
                    smallerDimension = smallerDimension * 3 / 4;

                    //Encode with a QR Code image
                    String[] data = {qrInputText, qrInputText2, qrInputText3};
                    QrCodeDataEncoder qrCodeEncoder = new QrCodeDataEncoder(data,
                            null,
                            Contents.Type.TEXT,
                            BarcodeFormat.QR_CODE.toString(),
                            smallerDimension);

                    try {
                        Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
                        if (bitmap != null) {
                            generateQrcodeFragment(bitmap);
                        }
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                    setfocus(true);
                }
            }
        });





        //on pressing back key
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //load new office Image
        loadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               function();
            }
        });

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                function();
            }
        });

    }

    //setting new office image
    public void function()
    {


        Intent intent = new Intent();

        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Complete action using"),
                PICK_FROM_GALLERY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode != RESULT_OK) return;
        else
        {


            selectedImageUri=data.getData();
            Picasso.with(getBaseContext()).load(selectedImageUri).fit().into(officeImage);
//            if(setphotoIn==1)
//            {
//                Picasso.with(getBaseContext()).load(selectedImageUri).fit().into(officeImage);
//
//            }
//             else if(setphotoIn==2)
//            {
//                Glide.with(getBaseContext()).load(selectedImageUri).
//                        bitmapTransform(new CircleTransform(getBaseContext())).into(userImage);
//            }
//            else {
//                Toast.makeText(getBaseContext(),"Error Occured",Toast.LENGTH_SHORT).show();
//            }
//

        }


    }

    //create fragment with qrcode
    public void generateQrcodeFragment(Bitmap bitmap)
    {
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        QrCodeFragment qrCodeFragment=new QrCodeFragment();
        String qrcode=encodeThumbnail(bitmap);
        Bundle bundle=new Bundle();
        bundle.putString("qrstring",qrcode);
        qrCodeFragment.setArguments(bundle);
        transaction.addToBackStack(null);
        transaction.replace(R.id.generateqrcodefragment, qrCodeFragment, "QR_Fragment");
        transaction.commit();

    }

    //encoding bitmap to string
    public String encodeThumbnail(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
    }



             @Override
             public boolean onCreateOptionsMenu(Menu menu) {
                 // Inflate the menu; this adds items to the action bar if it is present.
                 getMenuInflater().inflate(R.menu.menu_user_qr_generator, menu);
                 return true;
             }

             @Override
             public boolean onOptionsItemSelected(MenuItem item) {
                 // Handle action bar item clicks here. The action bar will
                 // automatically handle clicks on the Home/Up button, so long
                 // as you specify a parent activity in AndroidManifest.xml.
                 int id = item.getItemId();

                 //noinspection SimplifiableIfStatement
                 if (id == R.id.action_settings) {
                     return true;
                 }

                 return super.onOptionsItemSelected(item);
             }


    public void setDefaultImage()
    {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        Integer integerWidth = new Integer(displaymetrics.widthPixels);
        Integer integerHeight=new Integer(displaymetrics.heightPixels);
        float h=(float)0.45;
        int imageviewheight=(int)(displaymetrics.heightPixels*h);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , imageviewheight);
        officeImage.setLayoutParams(layoutParams);
        Picasso.with(getBaseContext()).load(R.drawable.officeimage).fit().
                into(officeImage);
        Glide.with(getBaseContext()).load(R.drawable.defaultuserimage).
                bitmapTransform(new CircleTransform(getBaseContext())).into(userImage);
    }

    //close fragment if fragment is opened
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            int count = getFragmentManager().getBackStackEntryCount();

            if (count == 0) {
                super.onBackPressed();
                //additional code
            } else {
                getFragmentManager().popBackStack();
                setfocus(false);
            }
            }
        return true;

    }

    public void setfocus(boolean focus)
    {
        if(focus)
        {
            loadImage.setEnabled(false);
            designation.setEnabled(false);
            email_id.setEnabled(false);
            website.setEnabled(false);
            mob_no.setEnabled(false);
            phone_no.setEnabled(false);
            address.setEnabled(false);
            floatingAddButton.setVisibility(View.INVISIBLE);
        }
        else
        {
            loadImage.setEnabled(true);
            designation.setEnabled(true);
            email_id.setEnabled(true);
            website.setEnabled(true);
            mob_no.setEnabled(true);
            phone_no.setEnabled(true);
            address.setEnabled(true);

            floatingAddButton.setVisibility(View.VISIBLE);
        }
    }
}
