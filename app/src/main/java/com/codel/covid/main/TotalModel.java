package com.codel.covid.main;

import android.icu.util.LocaleData;
import android.text.format.DateFormat;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

public class TotalModel implements Serializable {
	private int cases;
	private int deaths;
	private int recovered;
	private long updated;




	public String getCases(){
		return cases+"";
	}

	public String getDeaths(){
		return deaths+"";
	}

	public String getRecovered(){
		return recovered+"";
	}

	public String getUpdated(){

		return DateFormat.format("yyyy/MM/dd HH:mm", new Date(updated)).toString();
	}

	@Override
 	public String toString(){
		return 
			"TotalModel{" + 
			"cases = '" + cases + '\'' + 
			",deaths = '" + deaths + '\'' + 
			",recovered = '" + recovered + '\'' + 
			",updated = '" + updated + '\'' + 
			"}";
		}
}