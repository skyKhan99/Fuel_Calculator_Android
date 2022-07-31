package com.appkhan.fuelcalculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link noSavedCar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class noSavedCar extends Fragment {

    private AdView mAdView;
    View view;
    EditText kacKmCevap, kacLCevap, gnclFyt;
    TextView kacSaat, kacPara, kacL;
    Button button, btn_shell, btn_bp, btn_aytmz, btn_total;
    WebView yakitWeb;
    String linkOfAytemiz = "https://www.aytemiz.com.tr/faaliyet-alanlari-hizmetler/istasyonlar-hakkinda/akaryakit-ve-pompa-fiyatlari/yakit-fiyatlari";
    String linkOfBP = "https://www.bp.com/tr_tr/turkey/home/urun-ve-hizmetler/akaryakit-ve-pompa-fiyatlari.html";
    String linkOfShell = "https://www.shell.com.tr/suruculer/shell-yakitlari/akaryakit-pompa-satis-fiyatlari.html";
    String linkOfTotal = "http://195.216.232.22/product_price.asp";



    public noSavedCar() {
        // Required empty public constructor
    }


    public static noSavedCar newInstance(String param1, String param2) {
        noSavedCar fragment = new noSavedCar();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_no_saved_car, container, false);
        MobileAds.initialize(getActivity(), "ca-app-pub-8379310124540160~2828239787");
        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        kacKmCevap = (EditText) view.findViewById(R.id.kacKmCevap);
        kacLCevap = (EditText) view.findViewById(R.id.kacLCevap);
        gnclFyt = (EditText) view.findViewById(R.id.gnclFyt);
        kacSaat = (TextView) view.findViewById(R.id.textView3);
        kacPara = (TextView) view.findViewById(R.id.textView5);
        kacL = (TextView) view.findViewById(R.id.textView7);
        button = (Button) view.findViewById(R.id.button);
        yakitWeb = (WebView) view.findViewById(R.id.webview);
        btn_aytmz = (Button) view.findViewById(R.id.aytemizButton);
        btn_shell = (Button) view.findViewById(R.id.shellButton);
        btn_total = (Button) view.findViewById(R.id.totalButton);
        btn_bp = (Button) view.findViewById(R.id.bpButton);

        return view;
    }

    @Override
    public void onStart() {


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String kacKmCevapGet = kacKmCevap.getText().toString();
                    String kacLCevapGet = kacLCevap.getText().toString();
                    try {
                        float km = Float.parseFloat(kacKmCevapGet);
                        float l = Float.parseFloat(kacLCevapGet);
                        hesapla(km, l);
                        }catch (Exception e){
                        Toast.makeText(getActivity(),getString(R.string.noValues),Toast.LENGTH_LONG).show();
                    }
                }
                });
        btn_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goneOnClick();
                yakitWeb.getSettings().setJavaScriptEnabled(true);
                yakitWeb.loadUrl(linkOfTotal);
            }
        });

        btn_aytmz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goneOnClick();
                yakitWeb.getSettings().setJavaScriptEnabled(true);
                yakitWeb.loadUrl(linkOfAytemiz);
            }
        });

        btn_bp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goneOnClick();
                yakitWeb.getSettings().setJavaScriptEnabled(true);
                yakitWeb.loadUrl(linkOfBP);
            }
        });

        btn_shell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goneOnClick();
                yakitWeb.getSettings().setJavaScriptEnabled(true);
                yakitWeb.loadUrl(linkOfShell);
            }
        });

        super.onStart();
    }


    private void hesapla(float km, float l){
        float sonucL = (km * l) / 100;
        String.format("#.%2f", sonucL);
        kacL.setText(sonucL + "L");
        kacL.setTextColor(Color.RED);
        float sonucFiyat, vGnclFyt = Float.valueOf(gnclFyt.getText().toString());
        sonucFiyat = sonucL * vGnclFyt;
        String.format("#.%2f", sonucFiyat);
        kacPara.setText(sonucFiyat + getString(R.string.money));
        kacPara.setTextColor(Color.RED);
        float sonucSure = km / 100;
        int dakika = (int) (sonucSure * 60);

        kacSaat.setText(sonucSure + getString(R.string.saat) + "(" +dakika + "dk)");
        kacSaat.setTextColor(Color.RED);
    }

    private void goneOnClick(){
        yakitWeb.setVisibility(View.VISIBLE);
        btn_aytmz.setVisibility(View.GONE);
        btn_bp.setVisibility(View.GONE);
        btn_shell.setVisibility(View.GONE);
        btn_total.setVisibility(View.GONE);
    }
}