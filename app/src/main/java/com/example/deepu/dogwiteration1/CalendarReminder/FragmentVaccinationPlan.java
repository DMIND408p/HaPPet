package com.example.deepu.dogwiteration1.CalendarReminder;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.deepu.dogwiteration1.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Marx on 18/09/2017.
 */

public class FragmentVaccinationPlan extends Fragment {
    View view;
    Calendar myCalendar = Calendar.getInstance();
    EditText edittext;
    TextView secondView_startDate, secondView_endDate, thirdView_startDate, thirdView_endDate,
            fourthView_startDate, fourthView_endDate, fifthView_startDate, fifthView_endDate,
            sixthView_startDate, sixthView_endDate;
    final int[] days = {42,56,70,84,84,168,98,112,84,112};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_vaccination_plan, container, false);
        secondView_startDate = (TextView) view.findViewById(R.id.secondView_startDate);
        secondView_endDate = (TextView) view.findViewById(R.id.secondView_endDate);
        thirdView_startDate = (TextView) view.findViewById(R.id.thirdView_startDate);
        thirdView_endDate = (TextView) view.findViewById(R.id.thirdView_endDate);
        fourthView_startDate = (TextView) view.findViewById(R.id.fourthView_startDate);
        fourthView_endDate = (TextView) view.findViewById(R.id.fourthView_endDate);
        fifthView_startDate = (TextView) view.findViewById(R.id.fifthView_startDate);
        fifthView_endDate = (TextView) view.findViewById(R.id.fifthView_endDate);
        sixthView_startDate = (TextView) view.findViewById(R.id.sixthView_startDate);
        sixthView_endDate = (TextView) view.findViewById(R.id.sixthView_endDate);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                saveBirthdayIntoSharedPreference(myCalendar.getTime());
                updateLabel();
            }
        };
        edittext = (EditText) view.findViewById(R.id.Birthday);
        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(getActivity(), date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });
        //if there is birthday record in sharedPreference, show it and update table
        try {
            if(null!=getBirthdayFromSharedPreference()){
                Date birthday = getBirthdayFromSharedPreference();
                myCalendar.setTime(birthday);
                updateLabel();
                updateVaccinationSchedule();
            }else {
                emptyDateTextView();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;
    }


    private void updateLabel() {
        String myFormat = "dd MMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        edittext.setText(sdf.format(myCalendar.getTime()));
        updateVaccinationSchedule();
    }

    private void updateVaccinationSchedule() {
        setStartDate(secondView_startDate,days[0]);
        setEndDate(secondView_endDate,days[1]);
        setStartDate(thirdView_startDate,days[2]);
        setEndDate(thirdView_endDate,days[3]);
        setStartDate(fourthView_startDate,days[4]);
        setEndDate(fourthView_endDate,days[5]);
        setStartDate(fifthView_startDate,days[6]);
        setEndDate(fifthView_endDate,days[7]);
        setStartDate(sixthView_startDate,days[8]);
        setEndDate(sixthView_endDate,days[9]);
    }

    private void saveBirthdayIntoSharedPreference(Date time) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        String s = sdf.format(time);
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("dog_birthday", MODE_PRIVATE).edit();
        editor.putString("birthday", s);
        editor.apply();
    }

    private Date getBirthdayFromSharedPreference() throws ParseException {
        SharedPreferences prefs = getActivity().getSharedPreferences("dog_birthday", MODE_PRIVATE);
        String restoredText = prefs.getString("birthday", null);
        if (restoredText != null) {
            String s = prefs.getString("birthday", "No birthday defined");//"No name defined" is the default value.
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
            Date birthday = sdf.parse(s);
            return birthday;
        }else {
            return null;
        }
    }

    private void setStartDate(TextView textView, int days){
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        String s = sdf.format(addDaysToDate(days))+" -";
        textView.setText(s);
    }

    private void setEndDate(TextView textView, int days){
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        textView.setText(sdf.format(addDaysToDate(days)));
    }

    private Date addDaysToDate(int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(myCalendar.getTime());
        calendar.add(Calendar.DATE, days);
        Date resultDate = new Date(calendar.getTimeInMillis());
        return resultDate;
    }

    private void emptyDateTextView(){
        secondView_startDate.setText("(Please set birthday)");
        secondView_endDate.setText("");
        thirdView_startDate.setText("(Please set birthday)");
        thirdView_endDate.setText("");
        fourthView_startDate.setText("(Please set birthday)");
        fourthView_endDate.setText("");
        fifthView_startDate.setText("(Please set birthday)");
        fifthView_endDate.setText("");
        sixthView_startDate.setText("(Please set birthday)");
        sixthView_endDate.setText("");
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
        }else {
            getActivity().setTitle("Vaccination Schedule");
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
        getActivity().setTitle("Vaccination Schedule");

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }
}
