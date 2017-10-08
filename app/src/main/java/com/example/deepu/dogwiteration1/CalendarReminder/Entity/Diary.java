package com.example.deepu.dogwiteration1.CalendarReminder.Entity;

import java.io.Serializable;

/**
 * Created by qingyunhe on 17/09/2017.
 */

public class Diary implements Serializable {
    private String diaryText;
    private byte[] pic1,pic2,pic3;

    public Diary() {
    }

    public String getDiaryText() {
        return diaryText;
    }

    public void setDiaryText(String diaryText) {
        this.diaryText = diaryText;
    }

    public byte[] getPic1() {
        return pic1;
    }

    public void setPic1(byte[] pic1) {
        this.pic1 = pic1;
    }

    public byte[] getPic2() {
        return pic2;
    }

    public void setPic2(byte[] pic2) {
        this.pic2 = pic2;
    }

    public byte[] getPic3() {
        return pic3;
    }

    public void setPic3(byte[] pic3) {
        this.pic3 = pic3;
    }
}
