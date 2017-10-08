package com.example.deepu.dogwiteration1.CalendarReminder;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.deepu.dogwiteration1.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import compactcalendarview.CompactCalendarView;
import compactcalendarview.domain.Event;


/**
 * Created by Marx on 15/09/2017.
 */

public class CompactCalendarTab extends Fragment implements View.OnClickListener{
    private static final String TAG = "CompactCalendarTab";
    private View view;
    private CompactCalendarView compactCalendarView;
    private ImageButton previousButton, nextButton,diaryImageButton,feedingImageButton;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());
    private TextView monthTextView, groomSwitchText,walkDogSwitchText,vaccinationPlantTextView;
    private Switch walkDogSwitch, groomSwitch;
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private DbHelper db;
    private Event currentEvent;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.calendar_tab, container, false);
        db = new DbHelper(getContext());
        setHasOptionsMenu(true);
        compactCalendarView = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);
        previousButton = (ImageButton) view.findViewById(R.id.previousButton);
        previousButton.setOnClickListener(this);
        nextButton = (ImageButton) view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);
        monthTextView = (TextView) view.findViewById(R.id.monthTextView);
        diaryImageButton = (ImageButton) view.findViewById(R.id.diaryImageButton);
        diaryImageButton.setOnClickListener(this);
        feedingImageButton = (ImageButton) view.findViewById(R.id.feedingImageButton);
        feedingImageButton.setOnClickListener(this);
        groomSwitchText = (TextView) view.findViewById(R.id.groomSwithText);
        walkDogSwitchText = (TextView) view.findViewById(R.id.walkDogSwitchText);
        vaccinationPlantTextView = (TextView) view.findViewById(R.id.vaccinationPlanTextView);
        vaccinationPlantTextView.setOnClickListener(this);
        walkDogSwitch = (Switch) view.findViewById(R.id.walkDogSwitch);
        groomSwitch = (Switch) view.findViewById(R.id.groomSwith);

        walkDogSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchChanged(walkDogSwitchText,walkDogSwitch,walkDogSwitch.isChecked());
                if(walkDogSwitch.isChecked()){
                    currentEvent.setWalk_dog(1);
                }else {
                    currentEvent.setWalk_dog(0);
                }
                //update Event if the event has been saved already
                if(db.getEvent(currentEvent.getTime_in_millis())!=null){
                    db.updateEvent(currentEvent);
                    compactCalendarView.getEvents(currentEvent.getTime_in_millis()).get(0).setWalk_dog(currentEvent.getWalk_dog());
                }else{//if not existed, insert a new one
                    db.addEvent(currentEvent);
                    compactCalendarView.addEvent(currentEvent);
                }
                refreshScreen();
            }
        });

        groomSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchChanged(groomSwitchText,groomSwitch,groomSwitch.isChecked());
                if(groomSwitch.isChecked()){
                    currentEvent.setGrooming(1);
                }else {
                    currentEvent.setGrooming(0);
                }
                //update Event if the event has been saved already
                if(db.getEvent(currentEvent.getTime_in_millis())!=null){
                    db.updateEvent(currentEvent);
                    compactCalendarView.getEvents(currentEvent.getTime_in_millis()).get(0).setGrooming(currentEvent.getGrooming());
                }else{//if not existed, insert a new one
                    db.addEvent(currentEvent);
                    compactCalendarView.addEvent(currentEvent);
                }
                refreshScreen();
            }
        });


        //addEvent();
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.shouldDrawIndicatorsBelowSelectedDays(true);
        compactCalendarView.removeAllEvents();
        compactCalendarView.addEvents(db.getAllEvents());

        //set today as the default selected date
        compactCalendarView.setCurrentDate(new Date());
        //if there is event today then refresh bottom screen and set current day event
        if(compactCalendarView.getEvents(new Date()).size()!=0){
            currentEvent = compactCalendarView.getEvents(new Date()).get(0);
            refreshScreen();
        }else{
            currentCalender.setTime(new Date());
            setToMidnight(currentCalender);
            long timeInMillis = currentCalender.getTimeInMillis();

            currentEvent = new Event();
            currentEvent.setColor(Color.argb(255, 169, 68, 65));
            currentEvent.setTime_in_millis(timeInMillis);
            refreshScreen();
        }

        monthTextView.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                monthTextView.setText(dateFormatForMonth.format(dateClicked));
                Log.d(TAG, "inside onclick " + dateFormatForDisplaying.format(dateClicked));
                if(compactCalendarView.getEvents(dateClicked).size()!=0){
                    //if there is a event on day clicked, set current event as that day event
                    currentEvent = compactCalendarView.getEvents(dateClicked).get(0);
                    refreshScreen();
                }else{
                    currentCalender.setTime(dateClicked);
                    setToMidnight(currentCalender);
                    long timeInMillis = currentCalender.getTimeInMillis();

                    currentEvent = new Event();
                    currentEvent.setColor(Color.argb(255, 169, 68, 65));
                    currentEvent.setTime_in_millis(timeInMillis);
                    refreshScreen();
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                monthTextView.setText(dateFormatForMonth.format(firstDayOfNewMonth));
                compactCalendarView.setCurrentDate(firstDayOfNewMonth);
                if(compactCalendarView.getEvents(firstDayOfNewMonth).size()!=0){
                    currentEvent = compactCalendarView.getEvents(firstDayOfNewMonth).get(0);
                    refreshScreen();
                }else{
                    currentCalender.setTime(firstDayOfNewMonth);
                    setToMidnight(currentCalender);
                    long timeInMillis = currentCalender.getTimeInMillis();

                    currentEvent = new Event();
                    currentEvent.setColor(Color.argb(255, 169, 68, 65));
                    currentEvent.setTime_in_millis(timeInMillis);
                    refreshScreen();
                }
            }
        });
        getActivity().setTitle("Dog Calendar");

        refreshScreen();

        return view;
    }

    private void addEvent(){

        currentCalender.setTime(new Date());
        setToMidnight(currentCalender);
        long timeInMillis = currentCalender.getTimeInMillis();
        if(null==db.getEvent(timeInMillis)){
            Event event = new Event();
            event.setTime_in_millis(timeInMillis);
            event.setColor(Color.argb(255, 169, 68, 65));
            event.setGrooming(1);
            event.setWalk_dog(0);
            db.addEvent(event);
        }
    }

    private void updateEvent(Event event){

    }

    private void deleteEvent(Event event){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.previousButton:
                compactCalendarView.showPreviousMonth();
                break;
            case R.id.nextButton:
                compactCalendarView.showNextMonth();
                break;
            case R.id.diaryImageButton:
                Bundle arguments = new Bundle();
                arguments.putString("Time_in_millis", String.valueOf(currentEvent.getTime_in_millis()));
                FragmentDiary myFragment = new FragmentDiary();
                myFragment.setArguments(arguments);
                FragmentManager fragmentManager= getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.hide(fragmentManager.findFragmentById(R.id.calendar_reminder_main_page));
                ft.add(R.id.calendar_reminder_main_page, myFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case R.id.feedingImageButton:
                Bundle arguments1 = new Bundle();
                arguments1.putString("Time_in_millis", String.valueOf(currentEvent.getTime_in_millis()));
                FragmentFeedingRecord myFragment1 = new FragmentFeedingRecord();
                myFragment1.setArguments(arguments1);
                FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                FragmentTransaction ft1 = fragmentManager1.beginTransaction();
                ft1.hide(fragmentManager1.findFragmentById(R.id.calendar_reminder_main_page));
                ft1.add(R.id.calendar_reminder_main_page, myFragment1);
                ft1.addToBackStack(null);
                ft1.commit();
                break;
            case R.id.vaccinationPlanTextView:
                FragmentVaccinationPlan fragmentVaccinationPlan = new FragmentVaccinationPlan();
                FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                FragmentTransaction ft2 = fragmentManager2.beginTransaction();
                ft2.hide(fragmentManager2.findFragmentById(R.id.calendar_reminder_main_page));
                ft2.add(R.id.calendar_reminder_main_page, fragmentVaccinationPlan);
                ft2.addToBackStack(null);
                ft2.commit();
            default:
                break;
        }
    }


    private void switchChanged(TextView textView,Switch mSwitch,boolean bool){
        if(bool){
            mSwitch.setChecked(true);
            textView.setText("Yes");
        }else{
            mSwitch.setChecked(false);
            textView.setText("No");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshScreen();
    }

    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private void refreshScreen(){
        if(currentEvent==null){
            groomSwitch.setChecked(false);
            groomSwitchText.setText("No");
            walkDogSwitch.setChecked(false);
            walkDogSwitchText.setText("No");
            diaryImageButton.setColorFilter(Color.argb(255, 0, 0, 0));
            feedingImageButton.setColorFilter(Color.argb(255, 0, 0, 0));
        }else {
            if (1==currentEvent.getGrooming()){
                groomSwitch.setChecked(true);
                groomSwitchText.setText("Yes");
            }else{
                groomSwitch.setChecked(false);
                groomSwitchText.setText("No");
            }
            if (1==currentEvent.getWalk_dog()){
                walkDogSwitch.setChecked(true);
                walkDogSwitchText.setText("Yes");
            }else{
                walkDogSwitch.setChecked(false);
                walkDogSwitchText.setText("No");
            }


            if(currentEvent.getDiary_pic1()==null && currentEvent.getDiary_pic2()==null && currentEvent.getDiary_pic3()==null &&
                    (Objects.equals(currentEvent.getDiary_text(), "") || currentEvent.getDiary_text()==null)){
                diaryImageButton.setColorFilter(Color.argb(255, 0, 0, 0));
            } else{
                diaryImageButton.setColorFilter(Color.argb(255, 66, 179, 244)); // blue Tint
            }


            if(currentEvent.getFeeding_pic1()==null && currentEvent.getFeeding_pic2()==null && currentEvent.getFeeding_pic3()==null &&
                    (Objects.equals(currentEvent.getFeeding_text(), "") || currentEvent.getFeeding_text()==null)){
                feedingImageButton.setColorFilter(Color.argb(255, 0, 0, 0));
            } else{
                feedingImageButton.setColorFilter(Color.argb(255, 66, 179, 244)); // blue Tint
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
        }else {
            getActivity().setTitle("Dog Calendar");
            getActivity().invalidateOptionsMenu();
            compactCalendarView.removeAllEvents();
            compactCalendarView.addEvents(db.getAllEvents());
            currentEvent = db.getEvent(currentEvent.getTime_in_millis());
            refreshScreen();
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
        compactCalendarView.removeAllEvents();
        compactCalendarView.addEvents(db.getAllEvents());
        refreshScreen();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshScreen();
    }
}
