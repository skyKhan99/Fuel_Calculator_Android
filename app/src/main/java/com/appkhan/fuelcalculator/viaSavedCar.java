package com.appkhan.fuelcalculator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.HttpResponse;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link viaSavedCar#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class viaSavedCar extends Fragment {

    private AdView mAdView;
    TextView carName, fuelWasting, kacSaatKayitli, kacLKayitli, kacParaKayitli;
    EditText gnclFytSaved, kacKmCevap;
    Button button, btn_shell, btn_bp, btn_aytmz, btn_total;
    WebView yakitWeb;
    Switch aSwitch;
    String linkOfAytemiz = "https://www.aytemiz.com.tr/faaliyet-alanlari-hizmetler/istasyonlar-hakkinda/akaryakit-ve-pompa-fiyatlari/yakit-fiyatlari";
    String linkOfBP = "https://www.bp.com/tr_tr/turkey/home/urun-ve-hizmetler/akaryakit-ve-pompa-fiyatlari.html";
    String linkOfShell = "https://www.turkiyeshell.com/pompatest/";
    String linkOfTotal = "http://195.216.232.22/product_price.asp";

    public static String savedCarName, savedFuelWaste;
    public static viaSavedCar newInstance(String param1, String param2) {
        viaSavedCar fragment = new viaSavedCar();
        return fragment;
    }

    public viaSavedCar() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("name", Context.MODE_PRIVATE);
        savedCarName = sharedPreferences.getString("carName", "kayıtlı araç yok...");
        savedFuelWaste = sharedPreferences.getString("fuelWasting", "kayıtlı araç yok...");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_via_saved_car, container, false);
        MobileAds.initialize(getActivity(), "ca-app-pub-8379310124540160~2828239787");
        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        carName = (TextView) view.findViewById(R.id.carNameFrgTV);
        fuelWasting = (TextView) view.findViewById(R.id.fuelWastingFrgTV);
        kacSaatKayitli = (TextView) view.findViewById(R.id.textView3Saved);
        kacLKayitli = (TextView) view.findViewById(R.id.textView5Saved);
        kacParaKayitli = (TextView) view.findViewById(R.id.textView7Saved);
        button = (Button) view.findViewById(R.id.buttonSaved);
        btn_aytmz = (Button) view.findViewById(R.id.aytemizButton);
        btn_shell = (Button) view.findViewById(R.id.shellButton);
        btn_total = (Button) view.findViewById(R.id.totalButton);
        btn_bp = (Button) view.findViewById(R.id.bpButton);
        gnclFytSaved = (EditText) view.findViewById(R.id.gnclFytSaved);
        kacKmCevap = (EditText) view.findViewById(R.id.kacKmCevapSaved);
        yakitWeb = (WebView) view.findViewById(R.id.webview);
        return view;
    }

    @Override
    public void onStart() {
        carName.setText(savedCarName);
        fuelWasting.setText(savedFuelWaste + " L");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kmGet = kacKmCevap.getText().toString();
                try {
                    float km = Float.parseFloat(kmGet);
                    float l = Float.parseFloat(savedFuelWaste);
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
                yakitWeb.setWebChromeClient(new WebChromeClient());
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
                yakitWeb.setWebChromeClient(new WebChromeClient());
                yakitWeb.setWebViewClient(new WebViewClient());
                yakitWeb.loadUrl(linkOfShell);
            }
        });


        super.onStart();
    }

    private void hesapla(float km, float l){
        float sonucL = (km * l) / 100;
        String.format("#.%2f", sonucL);
        kacLKayitli.setText(sonucL + "L");
        kacLKayitli.setTextColor(Color.RED);
        float sonucFiyat, vGnclFyt = Float.valueOf(gnclFytSaved.getText().toString());
        sonucFiyat = sonucL * vGnclFyt;
        String.format("#.%2f", sonucFiyat);
        kacParaKayitli.setText(sonucFiyat + getString(R.string.money));
        kacParaKayitli.setTextColor(Color.RED);
        float sonucSure = km / 100;
        int dakika = (int) (sonucSure * 60);

        kacSaatKayitli.setText(sonucSure + getString(R.string.saat) + "(" +dakika + "dk)");
        kacSaatKayitli.setTextColor(Color.RED);
    }
    private void goneOnClick(){
        yakitWeb.setVisibility(View.VISIBLE);
        btn_aytmz.setVisibility(View.GONE);
        btn_bp.setVisibility(View.GONE);
        btn_shell.setVisibility(View.GONE);
        btn_total.setVisibility(View.GONE);
    }
}