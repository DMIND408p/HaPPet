package com.example.deepu.dogwiteration1.BreedActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.deepu.dogwiteration1.PicassoClient;
import com.example.deepu.dogwiteration1.R;

import java.util.ArrayList;

/**
 * Created by deepu on 18/09/2017.
 */

public class MyAdapter extends BaseAdapter {

    private Context context;
    ArrayList<Breed> breedArrayList;
    LayoutInflater inflater;


    public MyAdapter(Context context, ArrayList<Breed> breedArrayList) {
        this.context = context;
        this.breedArrayList = breedArrayList;
    }

    @Override
    public int getCount() {
        return breedArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return breedArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View changeView, ViewGroup viewGroup) {
       if(inflater == null){
           inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       }
       if(changeView == null){
           changeView= inflater.inflate(R.layout.breed_details_item,viewGroup,false);
       }
       BreedDetailsHolder breedDetailsHolder = new BreedDetailsHolder(changeView);
        breedDetailsHolder.bName.setText(breedArrayList.get(i).getBreedName());
        breedDetailsHolder.bFCost.setText(breedArrayList.get(i).getfood_cost());
        breedDetailsHolder.bEnergy.setText(breedArrayList.get(i).getEnergy());
        PicassoClient.downloading(context, breedArrayList.get(i).getpic_link(), breedDetailsHolder.bPic);

        return null;
    }
}
