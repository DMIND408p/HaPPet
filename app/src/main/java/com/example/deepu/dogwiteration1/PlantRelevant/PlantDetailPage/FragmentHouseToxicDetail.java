package com.example.deepu.dogwiteration1.PlantRelevant.PlantDetailPage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deepu.dogwiteration1.PicassoClient;
import com.example.deepu.dogwiteration1.PlantRelevant.PlantEntities.HouseToxicPlant;
import com.example.deepu.dogwiteration1.R;

/**
 * Created by qingyunhe on 27/08/2017.
 */

public class FragmentHouseToxicDetail extends Fragment {
    View view;
    TextView plantName, scienName, symptom, toxicPart, type, toxicLevel;
    HouseToxicPlant houseToxicPlant;
    ImageView img;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.house_toxic_detail, container, false);
        plantName = (TextView) view.findViewById(R.id.house_toxic_common_name);
        plantName.setText(houseToxicPlant.getPlantName());

        scienName = (TextView) view.findViewById(R.id.house_toxic_botanical_name);
        scienName.setText(houseToxicPlant.getScienName());
        img = (ImageView) view.findViewById(R.id.house_toxic_detail_pic);
        PicassoClient.downloading(getContext(), houseToxicPlant.getPicLink(), img);

        symptom = (TextView) view.findViewById(R.id.house_toxic_symptom);
        if (houseToxicPlant.getSymptom().equals("") || houseToxicPlant.getSymptom() == null) {
            symptom.setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.textView10)).setVisibility(View.GONE);
            view.findViewById(R.id.house_toxic_divider).setVisibility(View.GONE);
        } else {
            symptom.setText(houseToxicPlant.getSymptom());
        }

        toxicPart = (TextView) view.findViewById(R.id.house_toxic_part);
        if (houseToxicPlant.getTioxicPart().equals("") || houseToxicPlant.getTioxicPart() == null) {
            toxicPart.setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.textView9)).setVisibility(View.GONE);
        } else {
            toxicPart.setText(houseToxicPlant.getTioxicPart());
        }

        type = (TextView) view.findViewById(R.id.house_toxic_type);
        if (houseToxicPlant.getType().equals("") || houseToxicPlant.getType() == null) {
            type.setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.textView12)).setVisibility(View.GONE);
        } else {
            type.setText(houseToxicPlant.getType());
        }

        toxicLevel = (TextView) view.findViewById(R.id.house_toxic_level);
        toxicLevel.setText(houseToxicPlant.getToxicLevel());
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            houseToxicPlant = new HouseToxicPlant();
            houseToxicPlant.setPicLink(bundle.getString("PicLink"));
            houseToxicPlant.setPlantName(bundle.getString("PlantName"));
            houseToxicPlant.setScienName(bundle.getString("ScienName"));
            houseToxicPlant.setSymptom(bundle.getString("Symptom"));
            houseToxicPlant.setTioxicPart(bundle.getString("ToxicPart"));
            houseToxicPlant.setToxicLevel(bundle.getString("ToxicLevel"));
            houseToxicPlant.setType(bundle.getString("Type"));
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }

}
