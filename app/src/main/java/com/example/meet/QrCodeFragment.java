package com.example.meet;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class QrCodeFragment extends Fragment {
    private String qrString;
    private ImageView qrimage;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle=this.getArguments();
        qrString=bundle.getString("qrstring");
        View v=inflater.inflate(R.layout.fragment_qrcodelayout,container,false);
        qrimage=(ImageView)v.findViewById(R.id.qrcodeImage);
        qrimage.setImageBitmap(decodeThumbnail(qrString));;
        return v;

    }
    private Bitmap decodeThumbnail(String thumbData) {
        byte[] bytes = Base64.decode(thumbData, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }




}
