package com.example.deepu.dogwiteration1.PlantRelevant;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deepu.dogwiteration1.R;

/**
 * Created by Marx on 17/08/2017.
 */

public class WildToxicPlantHolder {
    TextView nameText, clinicalSigns;
    ImageView img;

    public WildToxicPlantHolder(View itemView) {
        nameText = (TextView) itemView.findViewById(R.id.nameText);
        clinicalSigns = (TextView) itemView.findViewById(R.id.clinicalSigns);
        img = (ImageView) itemView.findViewById(R.id.HouseToxicPlantImage);
    }
}
