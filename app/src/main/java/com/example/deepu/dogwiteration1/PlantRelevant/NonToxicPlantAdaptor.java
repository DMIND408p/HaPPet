package com.example.deepu.dogwiteration1.PlantRelevant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.deepu.dogwiteration1.PicassoClient;
import com.example.deepu.dogwiteration1.PlantRelevant.PlantEntities.NonToxicPlant;
import com.example.deepu.dogwiteration1.R;

import java.util.ArrayList;

/**
 * Created by qingyunhe on 20/08/2017.
 */

public class NonToxicPlantAdaptor extends BaseAdapter {
    Context c;
    ArrayList<NonToxicPlant> plants;
    LayoutInflater inflater;

    public NonToxicPlantAdaptor(Context c, ArrayList<NonToxicPlant> plants) {
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
            convertView = inflater.inflate(R.layout.non_toxic_plant_item, parent, false);
        }

        NonToxicPlantHolder holder = new NonToxicPlantHolder(convertView);
        holder.nameText.setText(plants.get(position).getCommonName());
        holder.typeText.setText(plants.get(position).getPlantType());
        holder.colorText.setText(plants.get(position).getFlowerColor());
        holder.boomingText.setText(plants.get(position).getBloomTime());
        PicassoClient.downloading(c, plants.get(position).getPicLink(), holder.img);


        return convertView;
    }
}
