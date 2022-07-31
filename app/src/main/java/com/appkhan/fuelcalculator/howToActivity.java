package com.appkhan.fuelcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class howToActivity extends AppCompatActivity {

    EditText kacKmGittin, kacLAldin;
    TextView sonucTV;
    Button button;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);

        MobileAds.initialize(this, "ca-app-pub-8379310124540160~2828239787");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        kacKmGittin = (EditText) findViewById(R.id.kacKmGittinCevap);
        kacLAldin = (EditText) findViewById(R.id.neKadarYakitAldinCevap);
        sonucTV = (TextView) findViewById(R.id.sonucTV);
        button = (Button) findViewById(R.id.button2);

        AlertDialog.Builder builder = new AlertDialog.Builder(howToActivity.this);
        builder.setTitle(getString(R.string.previously))
                .setMessage(getString(R.string.ilkaciklama))
                .setPositiveButton(getString(R.string.tamam), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(howToActivity.this);
                        builder1.setTitle(getString(R.string.later))
                                .setMessage(getString(R.string.aciklama2))
                                .setNeutralButton(getString(R.string.tamam), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                        builder1.show();
                    }
                });
        builder.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String km = kacKmGittin.getText().toString();
                String l = kacLAldin.getText().toString();
                try {
                    Float kmF = Float.parseFloat(km);
                    Float lF = Float.parseFloat(l);
                    hesapla(kmF, lF);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),getString(R.string.noValues),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void hesapla(float km, float l){
        Float sonuc = (l * 100) / km;
        sonucTV.setVisibility(View.VISIBLE);
        sonucTV.setText(getString(R.string.araciniz)+ " " + sonuc.toString() + " " + getString(R.string.kmyakiyor));
    }
}