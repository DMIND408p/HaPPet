package com.example.deepu.dogwiteration1.PlantRelevant.PlantEntities;

/**
 * Created by Marx on 17/08/2017.
 */

public class WildToxicPlantEntity {
    private String toxicPrinciples;
    private String family;
    private String clinicalSigns;
    private String PlantName;
    private String addcommonName;
    private String picLink;
    private String toxicity;
    private String link;

    public WildToxicPlantEntity() {
    }

    public WildToxicPlantEntity(String toxicPrinciples, String family, String clinicalSigns, String plantName, String addcommonName, String picLink, String toxicity, String link) {
        this.toxicPrinciples = toxicPrinciples;
        this.family = family;
        this.clinicalSigns = clinicalSigns;
        PlantName = plantName;
        this.addcommonName = addcommonName;
        this.picLink = picLink;
        this.toxicity = toxicity;
        this.link = link;
    }

    public String getToxicPrinciples() {
        return toxicPrinciples;
    }

    public void setToxicPrinciples(String toxicPrinciples) {
        this.toxicPrinciples = toxicPrinciples;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getClinicalSigns() {
        return clinicalSigns;
    }

    public void setClinicalSigns(String clinicalSigns) {
        this.clinicalSigns = clinicalSigns;
    }

    public String getPlantName() {
        return PlantName;
    }

    public void setPlantName(String plantName) {
        PlantName = plantName;
    }

    public String getAddcommonName() {
        return addcommonName;
    }

    public void setAddcommonName(String addcommonName) {
        this.addcommonName = addcommonName;
    }

    public String getPicLink() {
        return picLink;
    }

    public void setPicLink(String picLink) {
        this.picLink = picLink;
    }

    public String getToxicity() {
        return toxicity;
    }

    public void setToxicity(String toxicity) {
        this.toxicity = toxicity;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}