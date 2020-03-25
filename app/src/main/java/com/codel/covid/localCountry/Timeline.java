package com.codel.covid.localCountry;

public class Timeline {
    String date;
    private int cases;
    private int death;
    private int recovered;

    public Timeline(String date, int cases, int death, int recovered) {
        this.date = date;
        this.cases = cases;
        this.death = death;
        this.recovered = recovered;
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

    public String getRecovered() {
        return recovered+"";
    }
}


