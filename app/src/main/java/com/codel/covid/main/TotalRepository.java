package com.codel.covid.main;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.codel.covid.api.ApiService;
import com.codel.covid.helpers.RetrofitService;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TotalRepository {

    private static TotalRepository instance;
    public static TotalRepository getInstance(){
        if(instance==null){
            instance= new TotalRepository();
        }
        return instance;
    }

    private ApiService apiServices;
    private TotalRepository(){
        apiServices= RetrofitService.cteateService(ApiService.class);
    }

    private MutableLiveData<MyResponse> data;
    MutableLiveData<MyResponse> getTotal(){

        data = new MutableLiveData<>();

        apiServices.getTotalCountries().enqueue(new Callback<TotalModel>() {
            @Override
            public void onResponse(Call<TotalModel> call, Response<TotalModel> response) {
                if(response.isSuccessful()){
                    data.postValue(new MyResponse(response.body()));
                }
                else{
                    data.postValue(new MyResponse(response.code() + "" +response.message()));
                }
            }

            @Override
            public void onFailure(Call<TotalModel> call, Throwable t) {
                data.postValue(new MyResponse(t.getMessage()));
            }
        });
        return data;
    }
}
