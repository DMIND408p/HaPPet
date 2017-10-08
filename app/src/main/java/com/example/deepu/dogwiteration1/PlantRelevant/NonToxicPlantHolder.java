package com.example.deepu.dogwiteration1.PlantRelevant;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deepu.dogwiteration1.R;

/**
 * Created by qingyunhe on 20/08/2017.
 */

public class NonToxicPlantHolder {
    TextView nameText, typeText, colorText, boomingText;
    ImageView img;

    public NonToxicPlantHolder(View itemView) {
        nameText = (TextView) itemView.findViewById(R.id.nonToxicPlantNameText);
        typeText = (TextView) itemView.findViewById(R.id.non_toxic_type);
        colorText = (TextView) itemView.findViewById(R.id.non_toxic_color_flower);
        boomingText = (TextView) itemView.findViewById(R.id.non_toxic_booming);
        img = (ImageView) itemView.findViewById(R.id.nonToxicPlantImage);
    }
}
