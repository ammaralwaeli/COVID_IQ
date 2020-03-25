package com.codel.covid.counrties;

import java.io.Serializable;

public class CountryInfoModel implements Serializable {
	private String iso2;
	private String iso3;
	private int id;
	private String flag;

	public String getIso2(){
		return iso2;
	}

	public String getIso3(){
		return iso3;
	}

	public int getId(){
		return id;
	}

	public String getFlag(){
		return flag;
	}

	@Override
 	public String toString(){
		return 
			"CountryInfoModel{" + 
			"iso2 = '" + iso2 + '\'' + 
			",iso3 = '" + iso3 + '\'' + 
			",_id = '" + id + '\'' +
			",flag = '" + flag + '\'' + 
			"}";
		}
}