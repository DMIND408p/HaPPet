package com.example.deepu.dogwiteration1.CalendarReminder.Entity;

import java.io.Serializable;

/**
 * Created by qingyunhe on 17/09/2017.
 */

public class DailyRecord implements Serializable{
    private boolean walkDog;
    private boolean shower;
    private boolean grooming;
    private boolean vaccination;
    private Diary diary;
    private Feeding feeding;

    public DailyRecord() {
    }

    public boolean isWalkDog() {
        return walkDog;
    }

    public void setWalkDog(boolean walkDog) {
        this.walkDog = walkDog;
    }

    public boolean isShower() {
        return shower;
    }

    public void setShower(boolean shower) {
        this.shower = shower;
    }

    public boolean isGrooming() {
        return grooming;
    }

    public void setGrooming(boolean grooming) {
        this.grooming = grooming;
    }

    public boolean isVaccination() {
        return vaccination;
    }

    public void setVaccination(boolean vaccination) {
        this.vaccination = vaccination;
    }

    public Diary getDiary() {
        return diary;
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
    }

    public Feeding getFeeding() {
        return feeding;
    }

    public void setFeeding(Feeding feeding) {
        this.feeding = feeding;
    }
}
