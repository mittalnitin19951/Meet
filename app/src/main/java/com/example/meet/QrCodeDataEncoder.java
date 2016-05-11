package com.example.meet;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.Map;

public final class QrCodeDataEncoder {
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    private int dimension = Integer.MIN_VALUE;
    private String contents = null;
    private String[] con={};
    private String displayContents = null;
    private String title = null;
    private BarcodeFormat format = null;
    private boolean encoded = false;


    public QrCodeDataEncoder(String[] data, Bundle bundle, String type, String format, int dimension)
    {
        this.dimension=dimension;
        encoded = encodeContents(data, bundle, type, format);
//
    }

    private boolean encodeContents(String[] data, Bundle bundle, String type, String formatString) {
        // Default to QR_CODE if no format given.

        int size=data[0].length();
        format = null;
        if (formatString != null) {
            try {
                format = BarcodeFormat.valueOf(formatString);
            } catch (IllegalArgumentException iae) {
                // Ignore it then
            }
        }
        if (format == null || format == BarcodeFormat.QR_CODE) {
            this.format = BarcodeFormat.QR_CODE;
            encodeQRCodeContents(data, bundle, type);
        }
        else if (data != null && size > 0) {
            con = data;
            // displayContents = data;
            title = "Text";
        }
        return con != null && size > 0;
    }


    public String getContents() {
        return contents;
    }

    public String getDisplayContents() {
        return displayContents;
    }

    public String getTitle() {
        return title;
    }



    private void encodeQRCodeContents(String[] data, Bundle bundle, String type) {
        con=data;
    }

    public Bitmap encodeAsBitmap() throws WriterException {
        if (!encoded) return null;

        Map<EncodeHintType, Object> hints = null;
//        String encoding = guessAppropriateEncoding(contents);
        String encoding ="UTF-8";
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }

        MultiFormatWriter writer = new MultiFormatWriter();
//        BitMatrix result = writer.encode(con.toString(), format, dimension, dimension, hints);

        String s=StringArrayString(con);
        BitMatrix result = writer.encode(s, format, dimension, dimension, hints);
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        // All are 0, or black, by default
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }



    public String StringArrayString(String[] data)
    {
        String s="";
        s="D:" +data[0]+ "N:" +data[1]+ "P:" +data[2];
        return s;
    }





}