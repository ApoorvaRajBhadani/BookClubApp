package com.iitism.bookclub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class BarCodeScanner extends AppCompatActivity
{
    private ZXingScannerView scannerView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code_scanner);
        button=findViewById(R.id.start_scanning);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scancode();
            }
        });

    }
    public void scancode()
    {
        scannerView=new ZXingScannerView(this);
        scannerView.setResultHandler(new ZXingscannerResultHandler());

        setContentView(scannerView);
        scannerView.startCamera();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        scannerView.stopCamera();
    }

    public class ZXingscannerResultHandler implements ZXingScannerView.ResultHandler
    {

        @Override
        public void handleResult(Result rawResult)
        {
            String resultcode= rawResult.getText();
            Toast.makeText(BarCodeScanner.this,resultcode,Toast.LENGTH_SHORT).show();

            setContentView(R.layout.activity_bar_code_scanner);
            scannerView.stopCamera();
        }
    }
}
//added but not pushed
//so testing