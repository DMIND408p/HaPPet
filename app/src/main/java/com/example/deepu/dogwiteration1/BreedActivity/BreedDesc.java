package com.example.deepu.dogwiteration1.BreedActivity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deepu.dogwiteration1.PicassoClient;
import com.example.deepu.dogwiteration1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BreedDesc extends Fragment {

    View vi;
    TextView breedName, foodCost, eLevel, gInfo, cLevel, bDesc;
    Breed brr;
    ImageView imv;

    public BreedDesc() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vi=  inflater.inflate(R.layout.fragment_breed__descp, container, false);
        breedName = (TextView) vi.findViewById(R.id.breeding_name);
        breedName.setText(brr.getBreedName());
        foodCost  = (TextView) vi.findViewById(R.id.fooding_cost);
        foodCost.setText(brr.getfood_cost());
        eLevel = (TextView)vi.findViewById(R.id.ereging_level);
        eLevel.setText(brr.getEnergy());
        gInfo = (TextView) vi.findViewById(R.id.grooming_info);
        gInfo.setText(brr.getgrooming_requirements());
        cLevel = (TextView) vi.findViewById(R.id.children_level);
        cLevel.setText(brr.getsuitability_for_children());
        bDesc = (TextView) vi.findViewById(R.id.breed_descpt);
        bDesc.setText(brr.getDescription());
        imv=(ImageView) vi.findViewById(R.id.breeding_img);
        PicassoClient.downloading(getContext(),"http://www.purina.com.au" + brr.getpic_link(),imv);
        return vi;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bbundle = this.getArguments();
        setHasOptionsMenu(true);
        if (bbundle != null) {
        brr = new Breed();
          brr.setPic_link(bbundle.getString("PicLink"));
            brr.setBreedName(bbundle.getString("BreedName"));
            brr.setFood_cost(bbundle.getString("FoodCost"));
            brr.setEnergy(bbundle.getString("Energy"));
            brr.setGrooming_requirements(bbundle.getString("GroomingReq"));
            brr.setSuitability_for_children(bbundle.getString("ChildrenSuitablity"));
            brr.setDescription(bbundle.getString("BreedDesc"));

        }
        }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
        }else {
            getActivity().setTitle("Breed Description");
            getActivity().invalidateOptionsMenu();

        }
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_plant_fragment, menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
        getActivity().setTitle("Breed Description");

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }

//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        super.onPrepareOptionsMenu(menu);
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//
//    }
}

