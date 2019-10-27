package com.example.tejasvedantham.carespreerewards;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class ScanQR extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
    }

    public void buttonClick(View v) {
        ImageView myImageView = (ImageView) findViewById(R.id.imgview);
        Bitmap myBitmap = BitmapFactory.decodeResource(
                getApplicationContext().getResources(),
                R.drawable.puppy);
        myImageView.setImageBitmap(myBitmap);

        //Setup the barcode detector
        BarcodeDetector detector =
                new BarcodeDetector.Builder(getApplicationContext())
                        .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                        .build();
        if(!detector.isOperational()){
            return;
        }
        //Detect the barcode
        Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
        SparseArray<Barcode> barcodes = detector.detect(frame);

        //Decode the barcode
        Barcode thisCode = barcodes.valueAt(0);
        TextView txtView = (TextView) findViewById(R.id.txtContent);
        txtView.setText(thisCode.rawValue);
    }
}
