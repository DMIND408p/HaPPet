package com.example.deepu.dogwiteration1.PlantRelevant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.deepu.dogwiteration1.PicassoClient;
import com.example.deepu.dogwiteration1.PlantRelevant.PlantEntities.HouseToxicPlant;
import com.example.deepu.dogwiteration1.R;

import java.util.ArrayList;

/**
 * Created by qingyunhe on 20/08/2017.
 */

public class HouseToxicPlantAdaptor extends BaseAdapter {
    private Context c;
    private ArrayList<HouseToxicPlant> plants;
    private LayoutInflater inflater;

    public HouseToxicPlantAdaptor(Context c, ArrayList<HouseToxicPlant> plants) {
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
            convertView = inflater.inflate(R.layout.house_toxic_plant_item, parent, false);
        }

        HouseToxicPlantHolder holder = new HouseToxicPlantHolder(convertView);
        holder.nameText.setText(plants.get(position).getPlantName());
        holder.typeText.setText(plants.get(position).getType());
        holder.clinicalSignText.setText(plants.get(position).getSymptom());
        PicassoClient.downloading(c, plants.get(position).getPicLink(), holder.img);
        if (plants.get(position).getToxicLevel().contains("Lo")) {
            holder.toxicLevelImg.setBackgroundResource(R.drawable.low);
        } else if (plants.get(position).getToxicLevel().contains("Mi")) {
            holder.toxicLevelImg.setBackgroundResource(R.drawable.mid);
        } else if (plants.get(position).getToxicLevel().contains("H")) {
            holder.toxicLevelImg.setBackgroundResource(R.drawable.high);
        }


        return convertView;
    }




}
