package com.codel.covid.localCountry;

public class Timeline {
    String date;
    private int cases;
    private int death;

    public Timeline(String date, int cases, int death) {
        this.date = date;
        this.cases = cases;
        this.death = death;
    }


    public String getDate() {
        return date;
    }

    public String getCases() {
        return cases+"";
    }

    public String getDeath() {
        return death+"";
    }

}


