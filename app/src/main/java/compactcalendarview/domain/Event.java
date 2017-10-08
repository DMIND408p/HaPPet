package compactcalendarview.domain;

import android.graphics.Bitmap;

public class Event{

    private int color;
    private long time_in_millis;
    private int walk_dog;
    private int grooming;
    private String diary_text,feeding_text;
    private Bitmap diary_pic1, diary_pic2, diary_pic3, feeding_pic1, feeding_pic2, feeding_pic3;

    public Event() {
    }

    public Event(long time_in_millis, int color, int walk_dog, int grooming, String diary_text, String feeding_text, Bitmap diary_pic1, Bitmap diary_pic2, Bitmap diary_pic3, Bitmap feeding_pic1, Bitmap feeding_pic2, Bitmap feeding_pic3) {
        this.time_in_millis = time_in_millis;
        this.color = color;
        this.walk_dog = walk_dog;
        this.grooming = grooming;
        this.diary_text = diary_text;
        this.feeding_text = feeding_text;
        this.diary_pic1 = diary_pic1;
        this.diary_pic2 = diary_pic2;
        this.diary_pic3 = diary_pic3;
        this.feeding_pic1 = feeding_pic1;
        this.feeding_pic2 = feeding_pic2;
        this.feeding_pic3 = feeding_pic3;
    }

    public String getFeeding_text() {
        return feeding_text;
    }

    public void setFeeding_text(String feeding_text) {
        this.feeding_text = feeding_text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public long getTime_in_millis() {
        return time_in_millis;
    }

    public void setTime_in_millis(long time_in_millis) {
        this.time_in_millis = time_in_millis;
    }

    public int getWalk_dog() {
        return walk_dog;
    }

    public void setWalk_dog(int walk_dog) {
        this.walk_dog = walk_dog;
    }


    public int getGrooming() {
        return grooming;
    }

    public void setGrooming(int grooming) {
        this.grooming = grooming;
    }

    public String getDiary_text() {
        return diary_text;
    }

    public void setDiary_text(String diary_text) {
        this.diary_text = diary_text;
    }

    public Bitmap getDiary_pic1() {
        return diary_pic1;
    }

    public void setDiary_pic1(Bitmap diary_pic1) {
        this.diary_pic1 = diary_pic1;
    }

    public Bitmap getDiary_pic2() {
        return diary_pic2;
    }

    public void setDiary_pic2(Bitmap diary_pic2) {
        this.diary_pic2 = diary_pic2;
    }

    public Bitmap getDiary_pic3() {
        return diary_pic3;
    }

    public void setDiary_pic3(Bitmap diary_pic3) {
        this.diary_pic3 = diary_pic3;
    }

    public Bitmap getFeeding_pic1() {
        return feeding_pic1;
    }

    public void setFeeding_pic1(Bitmap feeding_pic1) {
        this.feeding_pic1 = feeding_pic1;
    }

    public Bitmap getFeeding_pic2() {
        return feeding_pic2;
    }

    public void setFeeding_pic2(Bitmap feeding_pic2) {
        this.feeding_pic2 = feeding_pic2;
    }

    public Bitmap getFeeding_pic3() {
        return feeding_pic3;
    }

    public void setFeeding_pic3(Bitmap feeding_pic3) {
        this.feeding_pic3 = feeding_pic3;
    }
}
