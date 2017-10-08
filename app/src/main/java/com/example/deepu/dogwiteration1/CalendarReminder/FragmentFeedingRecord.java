package com.example.deepu.dogwiteration1.CalendarReminder;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deepu.dogwiteration1.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import compactcalendarview.domain.Event;

/**
 * Created by qingyunhe on 17/09/2017.
 */

public class FragmentFeedingRecord extends Fragment implements View.OnClickListener{
    View view;
    ImageView imageViewFeeding, imageView1Feeding, imageView2Feeding;
    TextView feedingEditText, feedingDateTextView;
    ImageButton feedingSaveImageButton, feedingCameraImageButton, feedingDeleteImageButton;
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;
    private String userChoosenTask;
    private DbHelper dbHelper;
    private Event currentEvent;
    Long time_in_millis;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feeding_record,container,false);
        getActivity().setTitle("Feeding Record");
        setHasOptionsMenu(true);
        imageViewFeeding = (ImageView) view.findViewById(R.id.imageViewFeeding);
        imageViewFeeding.setOnClickListener(this);
        imageView1Feeding = (ImageView) view.findViewById(R.id.imageView1Feeding);
        imageView1Feeding.setOnClickListener(this);
        imageView2Feeding = (ImageView) view.findViewById(R.id.imageView2Feeding);
        imageView2Feeding.setOnClickListener(this);
        feedingEditText = (TextView) view.findViewById(R.id.feedingEditText);
        feedingDateTextView = (TextView) view.findViewById(R.id.feedingDateTextView);
        feedingSaveImageButton = (ImageButton) view.findViewById(R.id.feedingSaveImageButton);
        feedingSaveImageButton.setOnClickListener(this);
        feedingCameraImageButton = (ImageButton) view.findViewById(R.id.feedingCameraImageButton);
        feedingCameraImageButton.setOnClickListener(this);
        feedingDeleteImageButton = (ImageButton) view.findViewById(R.id.feedingDeleteImageButton);
        feedingDeleteImageButton.setOnClickListener(this);
        //fill textView and imageViews in Diary Fragment
        if(null!=currentEvent){
            if(null!=currentEvent.getFeeding_text()){
                feedingEditText.setText(currentEvent.getFeeding_text());
            }
            if(null!=currentEvent.getFeeding_pic1()){
                imageViewFeeding.setImageBitmap(currentEvent.getFeeding_pic1());
            }
            if(null!=currentEvent.getFeeding_pic2()){
                imageView1Feeding.setImageBitmap(currentEvent.getFeeding_pic2());
            }
            if(null!=currentEvent.getFeeding_pic3()){
                imageView2Feeding.setImageBitmap(currentEvent.getFeeding_pic3());
            }
        }

        Calendar calendar = Calendar.getInstance();
        if(currentEvent!=null){
            calendar.setTimeInMillis(currentEvent.getTime_in_millis());
            feedingDateTextView.setText(dateFormatForDisplaying.format(calendar.getTime()));
        }else{
            feedingDateTextView.setText(dateFormatForDisplaying.format(time_in_millis));
        }



        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            time_in_millis = Long.valueOf(bundle.getString("Time_in_millis"));
            dbHelper = new DbHelper(getContext());
            currentEvent = dbHelper.getEvent(time_in_millis);
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
        }else {
            getActivity().setTitle("Feeding Record");
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
        getActivity().setTitle("Feeding Record");

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            //save event
            case R.id.feedingSaveImageButton:
                saveFeeding();
                break;
            case R.id.feedingCameraImageButton:
                if(!hasNullOrEmptyDrawable(imageView2Feeding)){
                    //show colored background toast but the size is not suitable
                    //Toast toast = Toast.makeText(getActivity(), "3 images maximum, you can delete the picture by clicking it.", Toast.LENGTH_LONG);
                    //View view = toast.getView();
                    //view.setBackgroundResource(R.color.colorPrimary);
                    ///*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
                    //toast.show();
                    Toast.makeText(getContext(),
                            "3 images maximum, you can delete the picture by clicking it.", Toast.LENGTH_LONG).show();
                }else{
                    choosePhoto();
                }
                break;
            case R.id.feedingDeleteImageButton:
                if(!hasNullOrEmptyDrawable(imageViewFeeding) || !hasNullOrEmptyDrawable(imageView1Feeding) ||
                        !hasNullOrEmptyDrawable(imageView2Feeding) || feedingEditText.getText()!=null || feedingEditText.getText()!="") {
                    final CharSequence[] items = { "Delete", "Cancel" };
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Are you sure to delete all the diary?");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (items[item].equals("Delete")) {
                                imageViewFeeding.setImageBitmap(null);
                                imageViewFeeding.setImageDrawable(null);
                                imageView1Feeding.setImageBitmap(null);
                                imageView1Feeding.setImageDrawable(null);
                                imageView2Feeding.setImageBitmap(null);
                                imageView2Feeding.setImageDrawable(null);
                                feedingEditText.setText(null);
                                saveFeeding();
                            } else if (items[item].equals("Cancel")) {
                                dialog.dismiss();
                            }
                        }
                    });
                    builder.show();
                }
                break;
            case R.id.imageViewFeeding:
                if(!hasNullOrEmptyDrawable(imageViewFeeding)){
                    final CharSequence[] items = { "Delete", "Cancel" };
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Are you sure to delete the image?");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (items[item].equals("Delete")) {
                                imageViewFeeding.setImageBitmap(null);
                            } else if (items[item].equals("Cancel")) {
                                dialog.dismiss();
                            }
                        }
                    });
                    builder.show();
                }
                break;
            case R.id.imageView1Feeding:
                if(!hasNullOrEmptyDrawable(imageView1Feeding)){
                    final CharSequence[] items1 = { "Delete", "Cancel" };
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setTitle("Are you sure to delete the image?");
                    builder1.setItems(items1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (items1[item].equals("Delete")) {
                                imageView1Feeding.setImageBitmap(null);
                            } else if (items1[item].equals("Cancel")) {
                                dialog.dismiss();
                            }
                        }
                    });
                    builder1.show();
                }
                break;
            case R.id.imageView2Feeding:
                if(!hasNullOrEmptyDrawable(imageView2Feeding)){
                    final CharSequence[] items2 = { "Delete", "Cancel" };
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                    builder2.setTitle("Are you sure to delete the image?");
                    builder2.setItems(items2, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (items2[item].equals("Delete")) {
                                imageView2Feeding.setImageBitmap(null);
                            } else if (items2[item].equals("Cancel")) {
                                dialog.dismiss();
                            }
                        }
                    });
                    builder2.show();
                }
                break;
            default:
                break;
        }
    }

    private void choosePhoto(){
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(getActivity());
                if (items[item].equals("Take Photo")) {
                    userChoosenTask="Take Photo";
                    if(result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask="Choose from Library";
                    if(result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }
    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setImage(bm);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setImage(thumbnail);
    }

    private void setImage(Bitmap bitmap){
        if(hasNullOrEmptyDrawable(imageViewFeeding)){
            imageViewFeeding.setImageBitmap(bitmap);
        }else if(hasNullOrEmptyDrawable(imageView1Feeding)){
            imageView1Feeding.setImageBitmap(bitmap);
        }else {
            imageView2Feeding.setImageBitmap(bitmap);
        }
    }


    private void saveFeeding(){
        if(currentEvent==null){
            //use time_in_miles to insert a new event
            Event event = new Event();
            if(feedingEditText.getText()!=null|| feedingEditText.getText()!=""){
                event.setFeeding_text(feedingEditText.getText().toString());
            }
            if (imageViewFeeding.getDrawable()!=null){
                event.setFeeding_pic1(((BitmapDrawable)(imageViewFeeding.getDrawable())).getBitmap());
            }
            if (imageView1Feeding.getDrawable()!=null){
                event.setFeeding_pic2(((BitmapDrawable)(imageView1Feeding.getDrawable())).getBitmap());
            }
            if (imageView2Feeding.getDrawable()!=null){
                event.setFeeding_pic3(((BitmapDrawable)(imageView2Feeding.getDrawable())).getBitmap());
            }
            event.setTime_in_millis(time_in_millis);
            event.setColor(Color.argb(255, 169, 68, 65));
            dbHelper.addEvent(event);
        }else{
            //update the currentEvent
            currentEvent.setFeeding_text(feedingEditText.getText().toString());

            if (imageViewFeeding.getDrawable()!=null){
                currentEvent.setFeeding_pic1(((BitmapDrawable)(imageViewFeeding.getDrawable())).getBitmap());
            }else {
                currentEvent.setFeeding_pic1(null);
            }

            if (imageView1Feeding.getDrawable()!=null){
                currentEvent.setFeeding_pic2(((BitmapDrawable)(imageView1Feeding.getDrawable())).getBitmap());
            }else {
                currentEvent.setFeeding_pic2(null);
            }

            if (imageView2Feeding.getDrawable()!=null){
                currentEvent.setFeeding_pic3(((BitmapDrawable)(imageView2Feeding.getDrawable())).getBitmap());
            }else{
                currentEvent.setFeeding_pic3(null);
            }
            dbHelper.updateEvent(currentEvent);
            Toast.makeText(getContext(),
                    "Save Success", Toast.LENGTH_LONG).show();
        }
    }

    public static boolean hasNullOrEmptyDrawable(ImageView iv)
    {
        Drawable drawable = iv.getDrawable();
        BitmapDrawable bitmapDrawable = drawable instanceof BitmapDrawable ? (BitmapDrawable)drawable : null;

        return bitmapDrawable == null || bitmapDrawable.getBitmap() == null;
    }
}
