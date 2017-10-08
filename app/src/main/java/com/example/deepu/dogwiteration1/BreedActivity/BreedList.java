package com.example.deepu.dogwiteration1.BreedActivity;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deepu.dogwiteration1.PicassoClient;
import com.example.deepu.dogwiteration1.R;

import java.util.List;

/**
 * Created by deepu on 17/09/2017.
 */

public class BreedList extends ArrayAdapter<Breed> {


    private Activity context;
    private List<Breed> breedList;

    public BreedList(Activity context,List<Breed> breedList){

        super(context, R.layout.newlist_layout,breedList);
        this.context = context;
        this.breedList = breedList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.newlist_layout,null,true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.brNew_Name);
        TextView textViewGrr = (TextView)listViewItem.findViewById(R.id.gr_Info);
        TextView textViewCost = (TextView)listViewItem.findViewById(R.id.food_Cost);
        TextView textViewEnergy = (TextView) listViewItem.findViewById(R.id.err_Level);
       // TextView textViewExer = (TextView) listViewItem.findViewById(R.id.over_AExer);
//        listView = (ListView)listViewItem.findViewById(R.id.breed_List);

       ImageView imgPic = (ImageView)listViewItem.findViewById(R.id.brr_img);
        ImageView brrLevel = (ImageView) listViewItem.findViewById(R.id.Chil_Level);


        Breed breed = breedList.get(position);
//
        textViewName.setText(breed.getBreedName());
        textViewGrr.setText("Grooming:"+ "\t"+ breed.getgrooming_requirements());
        textViewCost.setText("Daily Average Food Cost:"+"\n"+ "\t" +breed.getfood_cost());
        textViewEnergy.setText("Energy Level:"+ "\t"+breed.getEnergy());
     //   textViewExer.setText(breed.getoverall_exercise_requirement());
        if(breedList.get(position).getsuitability_for_children().contains("Lo")){
            brrLevel.setBackgroundResource(R.drawable.mollylow);
        }else
            if(breedList.get(position).getsuitability_for_children().contains("Medium")){
                brrLevel.setBackgroundResource(R.drawable.molymed);
            }else if(breedList.get(position).getsuitability_for_children().contains("High")){
                brrLevel.setBackgroundResource(R.drawable.mollyhigh);
            }
     //   imgPic.setImageResource(breed.getpic_link());
        PicassoClient.downloading(getContext(), "http://www.purina.com.au" + breed.getpic_link(), imgPic);
        return listViewItem;
    }
}
