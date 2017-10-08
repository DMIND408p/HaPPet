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
import com.example.deepu.dogwiteration1.PlantRelevant.PlantDetailPage.FragmentHouseToxicDetail;
import com.example.deepu.dogwiteration1.PlantRelevant.PlantEntities.HouseToxicPlant;
import com.example.deepu.dogwiteration1.R;

/**
 * Created by qingyunhe on 20/08/2017.
 */

public class HouseToxicTabFragment extends Fragment {
    private ListView plantListView;
    private FirebaseClient firebaseClient;
    private View houseToxicPlantView;
    private ProgressBar progressBar;
    final static String DB_URL = "https://databaseplant-8e253.firebaseio.com/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        houseToxicPlantView = inflater.inflate(R.layout.house_toxic_tab_fragment, container, false);
        plantListView = (ListView) houseToxicPlantView.findViewById(R.id.houseToxicPlantListView);
        plantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO:
                HouseToxicPlant houseToxicPlant = (HouseToxicPlant) plantListView.getItemAtPosition(position);
                Bundle arguments = new Bundle();
                arguments.putString("PicLink", houseToxicPlant.getPicLink());
                arguments.putString("PlantName", houseToxicPlant.getPlantName());
                arguments.putString("ScienName", houseToxicPlant.getScienName());
                arguments.putString("Symptom", houseToxicPlant.getSymptom());
                arguments.putString("ToxicPart", houseToxicPlant.getTioxicPart());
                arguments.putString("ToxicLevel", houseToxicPlant.getToxicLevel());
                arguments.putString("Type", houseToxicPlant.getType());

                FragmentHouseToxicDetail myFragment = new FragmentHouseToxicDetail();
                myFragment.setArguments(arguments);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.hide(getFragmentManager().findFragmentById(R.id.plant_main_page));
                ft.add(R.id.plant_main_page, myFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        progressBar = (ProgressBar) houseToxicPlantView.findViewById(R.id.house_toxic_progress_bar);

        firebaseClient = new FirebaseClient(houseToxicPlantView.getContext(), DB_URL, plantListView);
        firebaseClient.setLoadingProcessListener(new FirebaseClient.LoadingProcessListener() {
            @Override
            public void showLoading() {
                //show loading spinner before retrieve data from FireBase
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void hideLoading() {
                //hide loading spinner when data retrieve has finished
                progressBar.setVisibility(View.GONE);
            }
        });
        firebaseClient.refreshHouseToxicPlantData();
        return houseToxicPlantView;
    }

    public void filterList(String searchString) {
        firebaseClient.filterHouseToxicList(searchString.toLowerCase());
    }
}
