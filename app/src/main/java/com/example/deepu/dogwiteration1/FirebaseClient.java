package com.example.deepu.dogwiteration1;

import android.content.Context;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.deepu.dogwiteration1.PlantRelevant.HouseToxicPlantAdaptor;
import com.example.deepu.dogwiteration1.PlantRelevant.NonToxicPlantAdaptor;
import com.example.deepu.dogwiteration1.PlantRelevant.WildToxicPlantAdaptor;
import com.example.deepu.dogwiteration1.PlantRelevant.PlantEntities.HouseToxicPlant;
import com.example.deepu.dogwiteration1.PlantRelevant.PlantEntities.NonToxicPlant;
import com.example.deepu.dogwiteration1.PlantRelevant.PlantEntities.WildToxicPlantEntity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Marx on 18/08/2017.
 */

public class FirebaseClient {
    private Context c;
    private String DB_URL;
    private ListView listView;
    private DatabaseReference ref;
    private ArrayList<WildToxicPlantEntity> wildToxicPlants = new ArrayList<>();
    private ArrayList<WildToxicPlantEntity> filterWildToxicPlants = new ArrayList<>();
    private ArrayList<NonToxicPlant> nonToxicPlants = new ArrayList<>();
    private ArrayList<NonToxicPlant> filterNonToxicPlants = new ArrayList<>();
    private ArrayList<HouseToxicPlant> houseToxicPlants = new ArrayList<>();
    private ArrayList<HouseToxicPlant> filterHouseToxicPlants = new ArrayList<>();
    private WildToxicPlantAdaptor wildToxicPlantAdaptor;
    private NonToxicPlantAdaptor nonToxicPlantAdaptor;
    private HouseToxicPlantAdaptor houseToxicPlantAdaptor;
    private LoadingProcessListener loadingProcessListener;

    public interface LoadingProcessListener{
        void showLoading();
        void hideLoading();
    }

    public void setLoadingProcessListener(LoadingProcessListener loadingProcessListener){
        this.loadingProcessListener = loadingProcessListener;
    }

    public FirebaseClient(Context c, String DB_URL, ListView listView) {
        this.c = c;
        this.DB_URL = DB_URL;
        this.listView = listView;
        ref = FirebaseDatabase.getInstance().getReferenceFromUrl(DB_URL);

    }

    public void refreshWildToxicPlantData() {
        ref.child("plants").child("1").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getWildToxicPlantUpdates(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getWildToxicPlantUpdates(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getWildToxicPlantUpdates(DataSnapshot dataSnapshot) {
        wildToxicPlants.clear();
        loadingProcessListener.showLoading();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            WildToxicPlantEntity p = new WildToxicPlantEntity();
            p.setPlantName(ds.getValue(WildToxicPlantEntity.class).getPlantName());
            p.setPicLink(ds.getValue(WildToxicPlantEntity.class).getPicLink());
            p.setClinicalSigns(ds.getValue(WildToxicPlantEntity.class).getClinicalSigns());
            p.setAddcommonName(ds.getValue(WildToxicPlantEntity.class).getAddcommonName());
            p.setFamily(ds.getValue(WildToxicPlantEntity.class).getFamily());
            p.setToxicPrinciples(ds.getValue(WildToxicPlantEntity.class).getToxicPrinciples());
            p.setLink(ds.getValue(WildToxicPlantEntity.class).getLink());
            p.setToxicity(ds.getValue(WildToxicPlantEntity.class).getToxicity());

            wildToxicPlants.add(p);
        }
        if (wildToxicPlants.size() > 0) {
            wildToxicPlantAdaptor = new WildToxicPlantAdaptor(c, wildToxicPlants);
            listView.setAdapter((ListAdapter) wildToxicPlantAdaptor);
        } else {
            Toast.makeText(c, "No data", Toast.LENGTH_SHORT).show();
        }
        loadingProcessListener.hideLoading();
    }

    public void refreshNonToxicPlantData() {
        ref.child("plants").child("2").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getNonToxicPlantUpdates(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getNonToxicPlantUpdates(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getNonToxicPlantUpdates(DataSnapshot dataSnapshot) {
        nonToxicPlants.clear();
        loadingProcessListener.showLoading();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            NonToxicPlant p = new NonToxicPlant();
            p.setPlantId(ds.getValue(NonToxicPlant.class).getPlantId());
            p.setCommonName(ds.getValue(NonToxicPlant.class).getCommonName());
            p.setBotanicalName(ds.getValue(NonToxicPlant.class).getBotanicalName());
            p.setPlantType(ds.getValue(NonToxicPlant.class).getPlantType());
            p.setFlowerColor(ds.getValue(NonToxicPlant.class).getFlowerColor());
            p.setLightNeeds(ds.getValue(NonToxicPlant.class).getLightNeeds());
            p.setWaterNeeds(ds.getValue(NonToxicPlant.class).getWaterNeeds());
            p.setFertlizier(ds.getValue(NonToxicPlant.class).getFertlizier());
            p.setTemperature(ds.getValue(NonToxicPlant.class).getTemperature());
            p.setBloomTime(ds.getValue(NonToxicPlant.class).getBloomTime());
            p.setDescription(ds.getValue(NonToxicPlant.class).getDescription());
            p.setPicLink(ds.getValue(NonToxicPlant.class).getPicLink());
            nonToxicPlants.add(p);
        }
        if (nonToxicPlants.size() > 0) {
            nonToxicPlantAdaptor = new NonToxicPlantAdaptor(c, nonToxicPlants);
            listView.setAdapter((ListAdapter) nonToxicPlantAdaptor);
        } else {
            Toast.makeText(c, "No data", Toast.LENGTH_SHORT).show();
        }
        loadingProcessListener.hideLoading();
    }

    public void refreshHouseToxicPlantData() {
        ref.child("plants").child("0").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getHouseToxicPlantUpdates(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getHouseToxicPlantUpdates(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getHouseToxicPlantUpdates(DataSnapshot dataSnapshot) {
        houseToxicPlants.clear();
        loadingProcessListener.showLoading();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            HouseToxicPlant p = new HouseToxicPlant();
            p.setPlantName(ds.getValue(HouseToxicPlant.class).getPlantName());
            p.setPicLink(ds.getValue(HouseToxicPlant.class).getPicLink());
            p.setScienName(ds.getValue(HouseToxicPlant.class).getScienName());
            p.setSymptom(ds.getValue(HouseToxicPlant.class).getSymptom());
            p.setToxicLevel(ds.getValue(HouseToxicPlant.class).getToxicLevel());
            p.setTioxicPart(ds.getValue(HouseToxicPlant.class).getTioxicPart());
            p.setType(ds.getValue(HouseToxicPlant.class).getType());

            houseToxicPlants.add(p);
        }
        if (houseToxicPlants.size() > 0) {
            houseToxicPlantAdaptor = new HouseToxicPlantAdaptor(c, houseToxicPlants);
            listView.setAdapter((ListAdapter) houseToxicPlantAdaptor);
        } else {
            Toast.makeText(c, "No data", Toast.LENGTH_SHORT).show();
        }
        loadingProcessListener.hideLoading();
    }

    //simple search without any validation
    public void filterHouseToxicList(String searchString) {
        filterHouseToxicPlants.clear();
        for (HouseToxicPlant eachPlant : houseToxicPlants) {
            if (eachPlant.getPlantName().toLowerCase().contains(searchString) ||
                    eachPlant.getSymptom().toLowerCase().contains(searchString) ||
                    eachPlant.getType().toLowerCase().contains(searchString) ||
                    eachPlant.getToxicLevel().toLowerCase().contains(searchString)) {
                filterHouseToxicPlants.add(eachPlant);
            }
        }
        if (filterHouseToxicPlants.size() > 0) {
            houseToxicPlantAdaptor = new HouseToxicPlantAdaptor(c, filterHouseToxicPlants);
            listView.setAdapter((ListAdapter) houseToxicPlantAdaptor);
            listView.requestLayout();
            houseToxicPlantAdaptor.notifyDataSetChanged();
        } else {
            Toast.makeText(c, "No data", Toast.LENGTH_SHORT).show();
        }
    }


    public void filterNonToxicList(String searchString) {
        filterNonToxicPlants.clear();
        for (NonToxicPlant eachPlant : nonToxicPlants) {
            if (eachPlant.getCommonName().toLowerCase().contains(searchString) ||
                    eachPlant.getBotanicalName().toLowerCase().contains(searchString)) {
                filterNonToxicPlants.add(eachPlant);
            }
        }
        if (filterNonToxicPlants.size() > 0) {
            nonToxicPlantAdaptor = new NonToxicPlantAdaptor(c, filterNonToxicPlants);
            listView.setAdapter(nonToxicPlantAdaptor);
            listView.requestLayout();
            nonToxicPlantAdaptor.notifyDataSetChanged();
        } else {
            Toast.makeText(c, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    public void filterWildToxicList(String searchString) {
        filterWildToxicPlants.clear();
        for (WildToxicPlantEntity eachPlant : wildToxicPlants) {
            if (eachPlant.getPlantName().toLowerCase().contains(searchString) ||
                    eachPlant.getClinicalSigns().toLowerCase().contains(searchString) ||
                    eachPlant.getAddcommonName().toLowerCase().contains(searchString)) {
                filterWildToxicPlants.add(eachPlant);
            }
        }
        if (filterWildToxicPlants.size() > 0) {
            wildToxicPlantAdaptor = new WildToxicPlantAdaptor(c, filterWildToxicPlants);
            listView.setAdapter(wildToxicPlantAdaptor);
            listView.requestLayout();
            wildToxicPlantAdaptor.notifyDataSetChanged();
        } else {
            Toast.makeText(c, "No data", Toast.LENGTH_SHORT).show();
        }
    }
}
