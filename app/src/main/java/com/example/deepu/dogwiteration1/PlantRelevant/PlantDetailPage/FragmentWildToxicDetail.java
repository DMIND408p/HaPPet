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
import com.example.deepu.dogwiteration1.PlantRelevant.PlantEntities.WildToxicPlantEntity;
import com.example.deepu.dogwiteration1.R;

/**
 * Created by qingyunhe on 29/08/2017.
 */

public class FragmentWildToxicDetail extends Fragment {
    private WildToxicPlantEntity wildToxicPlantEntity;
    private View view;
    private TextView plantName, commonName, clinicalSign, family,toxicPrinciples;
    private ImageView img;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.wild_toxic_detail, container, false);

        img = (ImageView) view.findViewById(R.id.wild_toxic_detail_pic);
        PicassoClient.downloading(getContext(), wildToxicPlantEntity.getPicLink(), img);

        plantName = (TextView) view.findViewById(R.id.wild_toxic_plant_name);
        plantName.setText(wildToxicPlantEntity.getPlantName());

        commonName = (TextView) view.findViewById(R.id.wild_toxic_common_name);
        if(wildToxicPlantEntity.getAddcommonName().equals("") || wildToxicPlantEntity.getAddcommonName()==null){
            commonName.setVisibility(View.GONE);
        }else{
            commonName.setText(wildToxicPlantEntity.getAddcommonName());
        }

        family = (TextView) view.findViewById(R.id.wild_toxic_family);
        if (wildToxicPlantEntity.getFamily().equals("") || wildToxicPlantEntity.getFamily()==null){
            family.setVisibility(View.GONE);
            view.findViewById(R.id.textView6).setVisibility(View.GONE);
        }else{
            family.setText(wildToxicPlantEntity.getFamily());
        }

        toxicPrinciples = (TextView) view.findViewById(R.id.wild_toxic_principles);
        if(wildToxicPlantEntity.getToxicPrinciples().equals("") || wildToxicPlantEntity.getToxicPrinciples()==null){
            toxicPrinciples.setVisibility(View.GONE);
            view.findViewById(R.id.textView9).setVisibility(View.GONE);
        }else {
            toxicPrinciples.setText(wildToxicPlantEntity.getToxicPrinciples());
        }

        clinicalSign = (TextView) view.findViewById(R.id.wild_toxic_symptom);
        clinicalSign.setText(wildToxicPlantEntity.getClinicalSigns());

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            wildToxicPlantEntity = new WildToxicPlantEntity();
            wildToxicPlantEntity.setToxicity(bundle.getString("toxicity"));
            wildToxicPlantEntity.setLink(bundle.getString("link"));
            wildToxicPlantEntity.setAddcommonName(bundle.getString("addcommonName"));
            wildToxicPlantEntity.setClinicalSigns(bundle.getString("clinicalSigns"));
            wildToxicPlantEntity.setFamily(bundle.getString("family"));
            wildToxicPlantEntity.setPicLink(bundle.getString("picLink"));
            wildToxicPlantEntity.setPlantName(bundle.getString("PlantName"));
            wildToxicPlantEntity.setToxicPrinciples(bundle.getString("toxicPrinciples"));
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }
}
