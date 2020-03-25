package com.codel.covid.api;

import com.codel.covid.counrties.CountryModel;
import com.codel.covid.localCountry.HistoricalModel;
import com.codel.covid.main.TotalModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("all")
    Call<TotalModel> getTotalCountries();

    @GET("countries")
    Call<List<CountryModel>> getCountries();

    @GET("historical/{country}")
    Call<HistoricalModel> getHistorical(@Path("country")String country);
}
