package com.example.deepu.dogwiteration1.BreedActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deepu.dogwiteration1.R;

/**
 * Created by deepu on 18/09/2017.
 */

public class BreedDetailsHolder {
    TextView bName,bFCost,bEnergy;
    ImageView bPic;

  public BreedDetailsHolder(View viewItem){
      bName = (TextView) viewItem.findViewById(R.id.bName);
      bFCost = (TextView) viewItem.findViewById(R.id.bFoodCost);
      bEnergy = (TextView) viewItem.findViewById(R.id.bEnergy);
      bPic = (ImageView) viewItem.findViewById(R.id.bPic);


  }


}
