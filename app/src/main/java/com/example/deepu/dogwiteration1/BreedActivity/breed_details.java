package com.example.deepu.dogwiteration1.BreedActivity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.deepu.dogwiteration1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//import android.widget.SearchView;

//import android.widget.SearchView;

/**
 * A simple {@link Fragment} subclass.
 */
public class breed_details extends Fragment {

  //  RecyclerView re;
    View v;
    ListView listViewBreed;
    DatabaseReference databaseBreed;
    List<Breed> breedList;
    MyAdapter myAdapter;
    private ProgressBar breed_details_progress_bar1;
    public static final String Breed_Name = "BreedName";
   private SearchView searchBrView;


    public breed_details() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseBreed = FirebaseDatabase.getInstance().getReference("breedPic");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View   view =  inflater.inflate(R.layout.fragment_breed_details, container, false);
        listViewBreed = (ListView) view.findViewById(R.id.breed_list);
        breedList = new ArrayList<>();

      //  searchBrView = (SearchView) view.findViewById(R.id.searchBreed);
        listViewBreed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Breed breed =(Breed) listViewBreed.getItemAtPosition(i);
                Bundle arguments = new Bundle();
                arguments.putString("PicLink",breed.getpic_link());
                arguments.putString("BreedName",breed.getBreedName());
                arguments.putString("BreedDesc",breed.getDescription());
                arguments.putString("GroomingReq",breed.getgrooming_requirements());
                arguments.putString("FoodCost",breed.getfood_cost());
                arguments.putString("Energy",breed.getEnergy());
                 arguments.putString("OverAllExercise",breed.getoverall_exercise_requirement());
                arguments.putString("Barking",breed.gettendency_to_bark());
                arguments.putString("ChildrenSuitablity",breed.getsuitability_for_children());

                BreedDesc newMyFrag = new BreedDesc();
                newMyFrag.setArguments(arguments);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
               // fragmentTransaction.replace(R.id.breedData,newMyFrag);
                fragmentTransaction.hide(getFragmentManager().findFragmentById(R.id.hh_screen));
                fragmentTransaction.add(R.id.hh_screen,newMyFrag);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
//
//        breed_details_progress_bar1 = (ProgressBar) view.findViewById(R.id.breed_details_progress_bar);
//        breed_details_progress_bar1.setVisibility(View.VISIBLE);

//        searchBrView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//            //    adapter.getFilter().filter(newText);
//                return false;
//            }
//        });


      return  view;


    }
//    public void filterList(String searchString) {
//        databaseBreed
//    }

    @Override
    public void onStart() {
        super.onStart();
        databaseBreed.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                breedList.clear();
                for(DataSnapshot breedSnapshot : dataSnapshot.getChildren()){
                    Breed breed = breedSnapshot.getValue(Breed.class);
                    breedList.add(breed);
                }

                BreedList adapter = new BreedList(getActivity(),breedList);
                listViewBreed.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
        }else {
            getActivity().setTitle("Breed Details");
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
        getActivity().setTitle("Breed Details");

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }


}
