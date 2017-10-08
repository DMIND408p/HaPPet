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
import com.example.deepu.dogwiteration1.PlantRelevant.PlantDetailPage.FragmentWildToxicDetail;
import com.example.deepu.dogwiteration1.PlantRelevant.PlantEntities.WildToxicPlantEntity;
import com.example.deepu.dogwiteration1.R;

/**
 * Created by qingyunhe on 20/08/2017.
 */

public class WildToxicPlantTabFragment extends Fragment {
    private ListView plantListView;
    private FirebaseClient firebaseClient;
    private View wildToxicPlantView;
    private ProgressBar progressBar;
    private final static String DB_URL = "https://databaseplant-8e253.firebaseio.com/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        wildToxicPlantView = inflater.inflate(R.layout.wild_toxic_plant_tab_fragment, container, false);
        plantListView = (ListView) wildToxicPlantView.findViewById(R.id.wildToxicPlantListView);
        plantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO:

            }
        });
        plantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WildToxicPlantEntity wildToxicPlantEntity = (WildToxicPlantEntity) plantListView.getItemAtPosition(position);
                Bundle arguments = new Bundle();
                arguments.putString("PlantName",wildToxicPlantEntity.getPlantName());
                arguments.putString("picLink",wildToxicPlantEntity.getPicLink());
                arguments.putString("addcommonName",wildToxicPlantEntity.getAddcommonName());
                arguments.putString("clinicalSigns",wildToxicPlantEntity.getClinicalSigns());
                arguments.putString("family",wildToxicPlantEntity.getFamily());
                arguments.putString("link",wildToxicPlantEntity.getLink());
                arguments.putString("toxicity",wildToxicPlantEntity.getToxicity());
                arguments.putString("toxicPrinciples",wildToxicPlantEntity.getToxicPrinciples());

                FragmentWildToxicDetail myFragment = new FragmentWildToxicDetail();
                myFragment.setArguments(arguments);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.hide(getFragmentManager().findFragmentById(R.id.plant_main_page));
                ft.add(R.id.plant_main_page,myFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });



        progressBar = (ProgressBar) wildToxicPlantView.findViewById(R.id.wild_toxic_progress_bar);

        firebaseClient = new FirebaseClient(wildToxicPlantView.getContext(), DB_URL, plantListView);
        firebaseClient.refreshWildToxicPlantData();
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
        return wildToxicPlantView;
    }

    public void filterList(String searchString) {
        firebaseClient.filterWildToxicList(searchString.toLowerCase());
    }
}
