package com.example.meet;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CropActivity extends AppCompatActivity {

    ImageView iview;
    private static final String TEMP_PHOTO_FILE = "temporary_holder.jpg";
    protected static final int REQ_CODE_PICK_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        iview=(ImageView)findViewById(R.id.image);
    }


    public void ChooseImage(View View)
    {
        functionCrop();
    }
    public void functionCrop() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photoPickerIntent.setType("image/*");
        photoPickerIntent.putExtra("crop", "true");
        photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
        photoPickerIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(photoPickerIntent, REQ_CODE_PICK_IMAGE);
    }






    private Uri getTempUri() {
        return Uri.fromFile(getTempFile());
    }

    private File getTempFile() {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            File file = new File(Environment.getExternalStorageDirectory(),TEMP_PHOTO_FILE);
            try {

                file.createNewFile();
            } catch (IOException e) {
                Toast.makeText(getBaseContext(),"IOexception",Toast.LENGTH_LONG).show();
            }

            return file;
        } else {

            return null;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {

        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case REQ_CODE_PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    if (imageReturnedIntent!=null) {

                        File tempFile = getTempFile();

                        String filePath= Environment.getExternalStorageDirectory()
                                +"/"+TEMP_PHOTO_FILE;
                        System.out.println("path "+filePath);


                        Bitmap selectedImage =  BitmapFactory.decodeFile(filePath);
                        Uri uri=getTempUri();
                        if(uri!=null)
                        {
                            if (tempFile.exists()) tempFile.delete();
                            Picasso.with(getBaseContext()).load(uri).fit().into(iview);
                        }
                        else
                        {
                            Toast.makeText(getBaseContext(),"Error in loading",Toast.LENGTH_LONG).show();
                        }



                    }
                }
        }
    }
//    public void cropSelectedPicture() {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("outputX", pictureWidth);
//        intent.putExtra("outputY", pictureHeight);
//        intent.putExtra("aspectX", pictureWidth);
//        intent.putExtra("aspectY", pictureHeight);
//        intent.putExtra("scale", true);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        intent.putExtra("return-data", true);
//
//        PackageManager packageManager = context.getPackageManager();
//        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
//        if (activities.size() > 0) {
//            for (int i = 0; i < activities.size(); i++) {
//                String label = (String) activities.get(i).loadLabel(packageManager);
//                if (label.equals("Crop picture")) {
//                    ActivityInfo activity = activities.get(i).activityInfo;
//                    ComponentName name = new ComponentName(activity.applicationInfo.packageName,
//                            activity.name);
//                    intent.setComponent(name);
//                }
//            }
//        }
//
//        try {
//            cordova.startActivityForResult(this, intent, FILE_CROP_CODE);
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(context, "Please install a Cropping program", Toast.LENGTH_SHORT).show();
//        }
//    }
}
