package com.example.deepu.dogwiteration1.BreedActivity;

/**
 * Created by deepu on 17/09/2017.
 */

public class Breed {

    String breedName;
    String description;
    String energy;
    String food_cost;
    String grooming_requirements;
    String overall_exercise_requirement;
    String suitability_for_children;
    String tendency_to_bark;
    String link;
    String pic_link;

    public Breed(){}

    public Breed(String breedName, String description, String energy, String food_cost, String grooming_requirements, String overall_exercise_requirement, String suitability_for_children, String tendency_to_bark,String link,String pic_link) {
        this.breedName = breedName;
        this.description = description;
        this.energy = energy;
        this.food_cost = food_cost;
        this.grooming_requirements = grooming_requirements;
        this.overall_exercise_requirement = overall_exercise_requirement;
        this.suitability_for_children = suitability_for_children;
        this.tendency_to_bark = tendency_to_bark;
        this.link = link;
        this.pic_link = pic_link;
    }

    public String getBreedName() {
        return breedName;
    }

    public String getDescription() {
        return description;
    }

    public String getEnergy() {
        return energy;
    }

    public String getfood_cost() {
        return food_cost;
    }

    public String getgrooming_requirements() {
        return grooming_requirements;
    }

    public String getoverall_exercise_requirement() {
        return overall_exercise_requirement;
    }

    public String getsuitability_for_children() {
        return suitability_for_children;
    }

    public String gettendency_to_bark() {
        return tendency_to_bark;
    }

    public String getlink() {
        return link;
    }

    public String getpic_link(){
        return pic_link;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnergy(String energy) {
        this.energy = energy;
    }

    public void setFood_cost(String food_cost) {
        this.food_cost = food_cost;
    }

    public void setGrooming_requirements(String grooming_requirements) {
        this.grooming_requirements = grooming_requirements;
    }

    public void setOverall_exercise_requirement(String overall_exercise_requirement) {
        this.overall_exercise_requirement = overall_exercise_requirement;
    }

    public void setSuitability_for_children(String suitability_for_children) {
        this.suitability_for_children = suitability_for_children;
    }

    public void setTendency_to_bark(String tendency_to_bark) {
        this.tendency_to_bark = tendency_to_bark;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPic_link(String pic_link) {
        this.pic_link = pic_link;
    }


}
