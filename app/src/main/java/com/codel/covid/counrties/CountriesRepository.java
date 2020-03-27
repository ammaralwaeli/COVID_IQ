package com.codel.covid.counrties;

import androidx.lifecycle.MutableLiveData;

import com.codel.covid.api.ApiResponse;
import com.codel.covid.api.ApiService;
import com.codel.covid.helpers.RetrofitService;
import com.codel.covid.main.MyResponse;
import com.codel.covid.main.TotalModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CountriesRepository {

    private static CountriesRepository instance;
    public static CountriesRepository getInstance(){
        if(instance==null){
            instance= new CountriesRepository();
        }
        return instance;
    }

    private ApiService apiServices;
    private CountriesRepository(){
        apiServices= RetrofitService.cteateService(ApiService.class);
    }

    private MutableLiveData<ApiResponse> data;
    MutableLiveData<ApiResponse> getCountries(String sortBy){

        data = new MutableLiveData<>();

        apiServices.getCountries(sortBy).enqueue(new Callback<List<CountryModel>>() {
            @Override
            public void onResponse(Call<List<CountryModel>> call, Response<List<CountryModel>> response) {
                if(response.isSuccessful()){
                    data.postValue(new ApiResponse(response.body()));
                }
                else{
                    data.postValue(new ApiResponse(response.code() + "" +response.message()));
                }
            }

            @Override
            public void onFailure(Call<List<CountryModel>> call, Throwable t) {
                data.postValue(new ApiResponse(t.getMessage()));
            }
        });
        return data;
    }
}
