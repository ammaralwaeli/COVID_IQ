package com.codel.covid.api;

import com.codel.covid.counrties.CountryModel;
import com.codel.covid.localCountry.HistoricalModel;
import com.codel.covid.main.TotalModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("v2/all")
    Call<TotalModel> getTotalCountries();

    @GET("v2/countries")
    Call<List<CountryModel>> getCountries(@Query("sort")String sort);

    @GET("v2/historical/{country}?lastdays=all")
    Call<HistoricalModel> getHistorical(@Path("country")String country);
}
