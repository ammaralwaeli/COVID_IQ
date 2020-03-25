package com.codel.covid.counrties;

import java.io.Serializable;

public class CountryModel implements Serializable {
	private String country;
	private int cases;
	private int todayCases;
	private int deaths;
	private int todayDeaths;
	private int recovered;
	private int active;
	private int critical;
	private int casesPerOneMillion;
	private CountryInfoModel countryInfo;

	public String getCountry(){
		return country;
	}

	public String getCases(){
		return cases+"";
	}

	public String getTodayCases(){
		return todayCases+"";
	}

	public String getDeaths(){
		return deaths+"";
	}

	public String getTodayDeaths(){
		return todayDeaths+"";
	}

	public String getRecovered(){
		return recovered+"";
	}

	public String getActive(){
		return active+"";
	}

	public String getCritical(){
		return critical+"";
	}

	public int getCasesPerOneMillion(){
		return casesPerOneMillion;
	}

	public CountryInfoModel getCountryInfo() {
		return countryInfo;
	}

	@Override
	public String toString() {
		return "CountryModel{" +
				"country='" + country + '\'' +
				", cases=" + cases +
				", todayCases=" + todayCases +
				", deaths=" + deaths +
				", todayDeaths=" + todayDeaths +
				", recovered=" + recovered +
				", active=" + active +
				", critical=" + critical +
				", casesPerOneMillion=" + casesPerOneMillion +
				", countryInfo=" + countryInfo.toString() +
				'}';
	}
}