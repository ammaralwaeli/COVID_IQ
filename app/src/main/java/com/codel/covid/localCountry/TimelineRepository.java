package com.codel.covid.localCountry;

import androidx.lifecycle.MutableLiveData;

import com.codel.covid.api.ApiResponse;
import com.codel.covid.api.ApiService;
import com.codel.covid.counrties.CountryModel;
import com.codel.covid.helpers.RetrofitService;
import com.codel.covid.main.MyResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TimelineRepository {

    private static TimelineRepository instance;
    public static TimelineRepository getInstance(){
        if(instance==null){
            instance= new TimelineRepository();
        }
        return instance;
    }

    private ApiService apiServices;
    private TimelineRepository(){
        apiServices= RetrofitService.cteateService(ApiService.class);
    }

    private MutableLiveData<MyResponse> data;
    MutableLiveData<MyResponse> getTimeline(String country){

        data = new MutableLiveData<>();

        apiServices.getHistorical(country).enqueue(new Callback<HistoricalModel>() {
            @Override
            public void onResponse(Call<HistoricalModel> call, Response<HistoricalModel> response) {
                if(response.isSuccessful()){
                    data.postValue(new MyResponse(response.body()));
                }
                else{
                    data.postValue(new MyResponse(response.code() + "" +response.message()));
                }
            }

            @Override
            public void onFailure(Call<HistoricalModel> call, Throwable t) {
                data.postValue(new MyResponse(t.getMessage()));
            }
        });
        return data;
    }
}
