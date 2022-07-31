package com.appkhan.fuelcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link carSave#newInstance} factory method to
 * create an instance of this fragment.
 */
public class carSave extends Fragment {
    View view;
    Button btnSave;
    EditText carName, fuelWasting;
    public carSave() {
        // Required empty public constructor
    }

    public static carSave newInstance(String param1, String param2) {
        carSave fragment = new carSave();
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
        view = inflater.inflate(R.layout.fragment_car_save, container, false);
        btnSave = (Button) view.findViewById(R.id.frg_save);
        carName = (EditText) view.findViewById(R.id.carNameEdit);
        fuelWasting = (EditText) view.findViewById(R.id.fuelWastingEdit);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vehicleName = carName.getText().toString();
                String fuel = fuelWasting.getText().toString();
                SharedPreferences sharedPref = getContext().getSharedPreferences("name", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("carName", vehicleName);
                editor.putString("fuelWasting", fuel);
                editor.commit();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}