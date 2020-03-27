package com.codel.covid.counrties;

public enum SortBy {
    CASES("cases"),
    DEATHS("deaths"),
    RECOVERED("recovered");

    String value;

    SortBy(String val){
        value=val;
    }
}
