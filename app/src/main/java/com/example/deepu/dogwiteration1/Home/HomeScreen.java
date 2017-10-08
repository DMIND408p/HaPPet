package com.example.deepu.dogwiteration1.Home;


import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.deepu.dogwiteration1.MainActivity;
import com.example.deepu.dogwiteration1.R;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeScreen extends Fragment {
    ImageView captureImg;
    ImageButton captureBtn;
    Integer REQUEST_CAMERA = 1,SELECT_FILE=0;
    Button bodyLang,plantFind,breedBtn,calendarbtn,dogParkBtn,vetCareBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_home_screen, container, false);
        captureBtn = (ImageButton) v.findViewById(R.id.img_cap);
        captureImg = (ImageView) v.findViewById(R.id.cust_img);
        bodyLang = (Button)v.findViewById(R.id.btnBody_lang);
        plantFind = (Button) v.findViewById(R.id.btnPlant);
        breedBtn = (Button) v.findViewById(R.id.btn_breed_home);
        calendarbtn = (Button) v.findViewById(R.id.btn_calendar_home);
        dogParkBtn = (Button) v.findViewById(R.id.btn_dogpark_home);
        vetCareBtn = (Button) v.findViewById(R.id.btn_vercare_home);

        captureImg = (ImageView) v.findViewById(R.id.cust_img);

        breedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showBreedFragment();
            }
        });

        calendarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showCalendarFragment();
            }
        });

        vetCareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showVetCareFragment();
            }
        });

        dogParkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showDogParkFragment();
            }
        });

        bodyLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).showUpnfFragment();
            }
        });

        plantFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).showPlantFragment();
            }
        });

        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });



        return v;

    }

//    private File getFile(){
//
//        // File folder  = new File("sdcard/Upet");
//        File folder  = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//
//        if(!folder.exists()){
//            folder.mkdir();
//        }
//
//        File image_file = new File(folder,"cam_image.jpeg");
//        return image_file;
//    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
        }else {
            getActivity().setTitle("Home");
            getActivity().invalidateOptionsMenu();

        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode== Activity.RESULT_OK){

            if(requestCode== REQUEST_CAMERA){
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor edit=shre.edit();
                edit.putString("image_data",encodedImage);
                edit.commit();

                SharedPreferences shr = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String previouslyEncodedImage = shr.getString("image_data", "");

                if( !previouslyEncodedImage.equalsIgnoreCase("") ) {
                    byte[] b1 = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
                    Bitmap bitmap1 = BitmapFactory.decodeByteArray(b1, 0, b1.length);
                    captureImg.setImageBitmap(bitmap1);


//                    bitmap = Bitmap.createScaledBitmap(bitmap, 350, 350, true);
//                    captureImg.setImageBitmap(bitmap);
                }

            }else if(requestCode == SELECT_FILE){

                Uri selectedImageUri = data.getData();
                captureImg.setImageURI(selectedImageUri);

            }
        }

    }
    public void SelectImage(){

        final CharSequence[] items = {"Camera","Gallery","Cancel"};

        AlertDialog.Builder builder1 = new  AlertDialog.Builder(getActivity());
        builder1.setTitle("Customize HomePage");
        builder1.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                if(items[i].equals("Camera")){

                    String[] permissos = {"android.permission.CAMERA"};

                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(),permissos,REQUEST_CAMERA);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,REQUEST_CAMERA);
                    }else{

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,REQUEST_CAMERA);}

                }else if (items[i].equals("Gallery")){
                    Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent,"Select File"),SELECT_FILE);


                }else if(items[i].equals("Cancel")){
                    dialogInterface.dismiss();

                }
            }
        });
        builder1.show();


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
        getActivity().setTitle("HaPPet");
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }

}
