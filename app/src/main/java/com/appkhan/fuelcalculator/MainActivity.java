package com.appkhan.fuelcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;
    Button btnSaveCar, btnCalculateNow, btnKacYakiyor;
    static FragmentTransaction ft;
    static carSave carsave;
    Button btnSave;
    FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-8379310124540160~2828239787");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        btnSaveCar = (Button) findViewById(R.id.save);
        btnCalculateNow = (Button) findViewById(R.id.calculate);
        btnKacYakiyor = (Button) findViewById(R.id.calculate2);
        frameLayout = (FrameLayout) findViewById(R.id.fragmentSpace);

        btnSaveCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentSpace, new carSave());
                ft.commit();
                buttonDestroyer();
            }
        });

        btnCalculateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplication(), calculateNow.class);
                startActivity(i);
            }
        });

        btnKacYakiyor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplication(), howToActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().beginTransaction().remove(carsave).commitNowAllowingStateLoss();
    }

    private void buttonDestroyer(){
        btnSaveCar.setVisibility(View.GONE);
        btnCalculateNow.setVisibility(View.GONE);
        btnKacYakiyor.setVisibility(View.GONE);
    }

    private void buttonReturner(){
        btnSaveCar.setVisibility(View.VISIBLE);
        btnCalculateNow.setVisibility(View.VISIBLE);
        btnKacYakiyor.setVisibility(View.VISIBLE);
    }

    }
