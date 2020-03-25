package com.codel.covid.localCountry;

import java.io.Serializable;

public class HistoricalModel implements Serializable {
	private String standardizedCountryName;
	private TimelineModel timeline;

	public String getStandardizedCountryName(){
		return standardizedCountryName;
	}

	public TimelineModel getTimeline(){
		return timeline;
	}

	@Override
 	public String toString(){
		return 
			"HistoricalModel{" +
			"standardizedCountryName = '" + standardizedCountryName + '\'' + 
			",timeline = '" + timeline + '\'' + 
			"}";
		}
}