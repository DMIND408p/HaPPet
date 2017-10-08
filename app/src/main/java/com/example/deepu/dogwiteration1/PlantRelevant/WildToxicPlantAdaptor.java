package com.example.deepu.dogwiteration1.PlantRelevant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.deepu.dogwiteration1.PicassoClient;
import com.example.deepu.dogwiteration1.PlantRelevant.PlantEntities.WildToxicPlantEntity;
import com.example.deepu.dogwiteration1.R;

import java.util.ArrayList;

/**
 * Created by Marx on 17/08/2017.
 */

public class WildToxicPlantAdaptor extends BaseAdapter {

    Context c;
    ArrayList<WildToxicPlantEntity> plants;
    LayoutInflater inflater;

    public WildToxicPlantAdaptor(Context c, ArrayList<WildToxicPlantEntity> plants) {
        this.c = c;
        this.plants = plants;
    }

    @Override
    public int getCount() {
        return plants.size();
    }

    @Override
    public Object getItem(int position) {
        return plants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.wild_toxic_plant_item, parent, false);
        }

        WildToxicPlantHolder holder = new WildToxicPlantHolder(convertView);
        holder.nameText.setText(plants.get(position).getPlantName());
        holder.clinicalSigns.setText(plants.get(position).getClinicalSigns());
        PicassoClient.downloading(c, plants.get(position).getPicLink(), holder.img);


        return convertView;
    }
}
