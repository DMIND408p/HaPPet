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
import com.example.deepu.dogwiteration1.PlantRelevant.PlantEntities.NonToxicPlant;
import com.example.deepu.dogwiteration1.R;

/**
 * Created by qingyunhe on 29/08/2017.
 */

public class FragmentNonToxicDetail extends Fragment {
    private NonToxicPlant nonToxicPlant;
    private View view;
    private TextView commonName, description, fertlizier, flowerColor,
            lightNeeds, plantType, temperature, waterNeeds, botanicalName, bloomTime;
    private ImageView img;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.non_toxic_detail, container, false);

        img = (ImageView) view.findViewById(R.id.non_toxic_detail_pic);
        PicassoClient.downloading(getContext(), nonToxicPlant.getPicLink(), img);

        commonName = (TextView) view.findViewById(R.id.non_toxic_common_name);
        commonName.setText(nonToxicPlant.getCommonName());

        botanicalName = (TextView) view.findViewById(R.id.non_toxic_botanical_name);
        botanicalName.setText(nonToxicPlant.getBotanicalName());

        description = (TextView) view.findViewById(R.id.non_toxic_description);
        description.setText(nonToxicPlant.getDescription());

        plantType = (TextView) view.findViewById(R.id.non_toxic_plant_type);
        plantType.setText(nonToxicPlant.getPlantType());

        flowerColor = (TextView) view.findViewById(R.id.non_toxic_flower_color);
        flowerColor.setText(nonToxicPlant.getFlowerColor());

        bloomTime = (TextView) view.findViewById(R.id.non_toxic_booming_time);
        bloomTime.setText(nonToxicPlant.getBloomTime());

        lightNeeds = (TextView) view.findViewById(R.id.non_toxic_light_need);
        lightNeeds.setText(nonToxicPlant.getLightNeeds());

        waterNeeds = (TextView) view.findViewById(R.id.non_toxic_water);
        waterNeeds.setText(nonToxicPlant.getWaterNeeds());

        temperature = (TextView) view.findViewById(R.id.non_toxic_temperature);
        temperature.setText(nonToxicPlant.getTemperature());

        fertlizier = (TextView) view.findViewById(R.id.non_toxic_fertilizer);
        fertlizier.setText(nonToxicPlant.getFertlizier());

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        setHasOptionsMenu(true);
        if (bundle != null) {
            nonToxicPlant = new NonToxicPlant();
            nonToxicPlant.setDescription(bundle.getString("Description"));
            nonToxicPlant.setBloomTime(bundle.getString("BloomTime"));
            nonToxicPlant.setPicLink(bundle.getString("PicLink"));
            nonToxicPlant.setTemperature(bundle.getString("Temperature"));
            nonToxicPlant.setFertlizier(bundle.getString("Fertlizier"));
            nonToxicPlant.setBotanicalName(bundle.getString("botanicalName"));
            nonToxicPlant.setCommonName(bundle.getString("CommonName"));
            nonToxicPlant.setFlowerColor(bundle.getString("FlowerColor"));
            nonToxicPlant.setLightNeeds(bundle.getString("LightNeeds"));
            nonToxicPlant.setWaterNeeds(bundle.getString("WaterNeeds"));
            nonToxicPlant.setPlantType(bundle.getString("PlantType"));
            nonToxicPlant.setPlantId(bundle.getInt("PlantId"));
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }



}
