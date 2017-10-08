package com.example.deepu.dogwiteration1.PlantRelevant;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deepu.dogwiteration1.R;

/**
 * Created by qingyunhe on 20/08/2017.
 */

public class HouseToxicPlantHolder {
    TextView nameText, clinicalSignText, typeText;
    ImageView img, toxicLevelImg;

    public HouseToxicPlantHolder(View itemView) {
        nameText = (TextView) itemView.findViewById(R.id.non_toxic_common_name);
        clinicalSignText = (TextView) itemView.findViewById(R.id.HouseToxicclinicalSignsTextView);
        typeText = (TextView) itemView.findViewById((R.id.HouseToxicTypeTextView));

        img = (ImageView) itemView.findViewById(R.id.HouseToxicPlantImage);
        toxicLevelImg = (ImageView) itemView.findViewById(R.id.houseToxicLevelImage);
    }
}
