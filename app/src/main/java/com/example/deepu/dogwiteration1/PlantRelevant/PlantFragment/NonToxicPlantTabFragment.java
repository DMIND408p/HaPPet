package com.example.deepu.dogwiteration1.PlantRelevant.PlantFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.deepu.dogwiteration1.FirebaseClient;
import com.example.deepu.dogwiteration1.PlantRelevant.PlantDetailPage.FragmentNonToxicDetail;
import com.example.deepu.dogwiteration1.PlantRelevant.PlantEntities.NonToxicPlant;
import com.example.deepu.dogwiteration1.R;

/**
 * Created by qingyunhe on 20/08/2017.
 */

public class NonToxicPlantTabFragment extends Fragment {
    private ListView plantListView;
    private FirebaseClient firebaseClient;
    private View nonToxicPlantView;
    private ProgressBar progressBar;
    private final static String DB_URL = "https://databaseplant-8e253.firebaseio.com/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        nonToxicPlantView = inflater.inflate(R.layout.non_toxic_plant_tab_fragment, container, false);
        plantListView = (ListView) nonToxicPlantView.findViewById(R.id.nonToxicPlantListView);
        plantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NonToxicPlant nonToxicPlant = (NonToxicPlant) plantListView.getItemAtPosition(position);
                Bundle arguments = new Bundle();
                arguments.putString("Description",nonToxicPlant.getDescription());
                arguments.putString("BloomTime",nonToxicPlant.getBloomTime());
                arguments.putString("PicLink",nonToxicPlant.getPicLink());
                arguments.putString("Temperature",nonToxicPlant.getTemperature());
                arguments.putString("Fertlizier",nonToxicPlant.getFertlizier());
                arguments.putString("botanicalName",nonToxicPlant.getBotanicalName());
                arguments.putString("CommonName",nonToxicPlant.getCommonName());
                arguments.putString("FlowerColor",nonToxicPlant.getFlowerColor());
                arguments.putString("LightNeeds",nonToxicPlant.getLightNeeds());
                arguments.putString("WaterNeeds",nonToxicPlant.getWaterNeeds());
                arguments.putString("PlantType",nonToxicPlant.getPlantType());
                arguments.putInt("PlantId",nonToxicPlant.getPlantId());

                FragmentNonToxicDetail myFragment = new FragmentNonToxicDetail();
                myFragment.setArguments(arguments);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.hide(getFragmentManager().findFragmentById(R.id.plant_main_page));
                ft.add(R.id.plant_main_page,myFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        progressBar = (ProgressBar) nonToxicPlantView.findViewById(R.id.non_toxic_progress_bar);
        firebaseClient = new FirebaseClient(nonToxicPlantView.getContext(), DB_URL, plantListView);
        firebaseClient.refreshNonToxicPlantData();
        firebaseClient.setLoadingProcessListener(new FirebaseClient.LoadingProcessListener() {
            @Override
            public void showLoading() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void hideLoading() {
                progressBar.setVisibility(View.GONE);
            }
        });
        return nonToxicPlantView;
    }

    public void filterList(String searchString) {
        firebaseClient.filterNonToxicList(searchString.toLowerCase());
    }
}
