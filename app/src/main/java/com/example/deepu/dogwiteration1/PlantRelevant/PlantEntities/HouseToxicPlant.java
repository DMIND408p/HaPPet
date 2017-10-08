package com.example.deepu.dogwiteration1.PlantRelevant.PlantEntities;

/**
 * Created by qingyunhe on 20/08/2017.
 */

public class HouseToxicPlant {
    private String plantName;
    private String scienName;
    private String symptom;
    private String tioxicPart;
    private String toxicLevel;
    private String type;
    private String picLink;

    public HouseToxicPlant() {
    }

    public HouseToxicPlant(String plantName, String scienName, String symptom, String tioxicPart, String toxicLevel, String type, String picLink) {
        this.plantName = plantName;
        this.scienName = scienName;
        this.symptom = symptom;
        this.tioxicPart = tioxicPart;
        this.toxicLevel = toxicLevel;
        this.type = type;
        this.picLink = picLink;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getScienName() {
        return scienName;
    }

    public void setScienName(String scienName) {
        this.scienName = scienName;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getTioxicPart() {
        return tioxicPart;
    }

    public void setTioxicPart(String tioxicPart) {
        this.tioxicPart = tioxicPart;
    }

    public String getToxicLevel() {
        return toxicLevel;
    }

    public void setToxicLevel(String toxicLevel) {
        this.toxicLevel = toxicLevel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPicLink() {
        return picLink;
    }

    public void setPicLink(String picLink) {
        this.picLink = picLink;
    }
}
